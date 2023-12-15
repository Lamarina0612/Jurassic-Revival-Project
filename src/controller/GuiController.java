package controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import listener.ButtonListener;
import listener.KeyboardListener;
import listener.MouseClickListener;
import player.Player;
import utils.Action;
import utils.Instructions;
import view.WorldView;
import world.World;
import world.WorldBuilder;
import world.WorldImpl;

/**
 * Gui controller implementation for this game.
 */
public class GuiController extends AbstractGameController {
  private final MouseClickListener mouseListener;
  private Timer timer;
  private List<String> logger;
  private final WorldView view;
  private int numberOfTurns;
  
  /**
   * Object initialization for the GUI controller of this game.
   * 
   * @param model model of the world
   * @param build builder of the world
   * @param viewObject the GUI view of the game.
   * @throws NullPointerException null file for the world builder
   */
  public GuiController(WorldImpl model, WorldBuilder build, WorldView viewObject, int turns)
         throws NullPointerException {
    Objects.requireNonNull(viewObject);
    this.view = viewObject;
    this.world = model;
    this.mouseListener = new MouseClickListener();
    this.logger = new ArrayList<>();
    this.timer = new Timer(Instructions.TIMER_DELAY, e -> {
      this.computerAction();
    });
    this.numberOfTurns = turns;
    configureButtonListener();
    configureKeyboardListener();
    configureMouseClickListener();
  }
  
  private void configureKeyboardListener() {
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    keyPresses.put(KeyEvent.VK_P, () -> {
      if (this.world.isHuman()) {
        this.actionPick();
      }
    });
    
    keyPresses.put(KeyEvent.VK_L, () -> {
      if (this.world.isHuman()) {
        this.actionLook();
      }
    });
    
    keyPresses.put(KeyEvent.VK_K, () -> {
      if (this.world.isHuman()) {
        this.actionAttemptKill(world.getCurrentPlayer().getName());
      }
    });

    KeyboardListener keyListener = new KeyboardListener();
    keyListener.setKeyBoardActionMap(keyPresses);
    this.view.addActionListener(keyListener);
  }

  private void actionLook() {
    String msg = this.world.getCurrentPlayer().getName()
        + " looked around and get the info.";
    this.view.displayInfo(msg);
    this.displayBoard(msg);
  }

  private void actionAttemptKill(String currentPlayer) {
    currentPlayer = world.getCurrentPlayer().getName();
    this.takeAction(Action.ATTEMPT_KILL, currentPlayer);
  }

  private void actionPick() {
    try {
      String itemName = this.view.displayItems(this.world.getItems());
      if (Objects.nonNull(itemName)) {
        this.takeAction(Action.PICKUP, itemName);
      }
    } catch (IllegalStateException | NullPointerException e) {
      this.view.displayErrorMsg(e.getMessage());
    }
  }

  private void configureMouseClickListener() {
    Map<Integer, Consumer<String>> mouseClickedMap = new HashMap<>();
    mouseClickedMap.put(MouseEvent.BUTTON1, (input) -> {
      if (this.world.getSpacesNames().contains(input)) {
        if (this.world.isHuman()) {
          this.takeAction(Action.MOVE, input);
        }
      } else if (this.world.getPlayersNames().contains(input)) {
        this.getDetailOfPlayer(input);
      } else {
        this.getTargetDetails();
      }
    });
    
    mouseClickedMap.put(MouseEvent.BUTTON3, (input) -> {
      if (this.world.getSpacesNames().contains(input)) {
        if (this.world.isHuman()) {
          this.takeAction(Action.MOVEPET, input);
        }
      }
    });
    mouseListener.setMouseClickListenerMap(mouseClickedMap);
  }

  private void getTargetDetails() {
    try {
      this.view.displayInfo(this.execute(Action.DISPLAY_TARGET));
    } catch (NullPointerException | IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  private void getDetailOfPlayer(String input) {
    try {
      this.view.displayInfo(this.execute(Action.DISPLAY_PLAYER, input));
    } catch (NullPointerException | IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    } 
  }

  private void takeAction(Action action, String input) {
    try {
      String message = this.world.getCurrentPlayer().getName() 
          + " tried to " + action.toString() + " " + input + ".";
      String result = this.execute(action, input);
      this.displayBoard(message + " " + result);
    } catch (IllegalArgumentException | IllegalAccessException | NullPointerException exception) {
      this.view.displayErrorMsg(exception.getMessage());
    }
  }

  private void configureButtonListener() {
    Map<String, Runnable> buttonMap = new HashMap<>();
    
    buttonMap.put(Instructions.NEW_GAME, () -> {
      this.createNewGame();
    });
    
    buttonMap.put(Instructions.QUIT_GAME, () -> {
      this.quitGame();
    });

    buttonMap.put(Instructions.RESTART_GAME, () -> {
      this.restartGame();
    });

    buttonMap.put(Instructions.CREATE_NEW_GAME, () -> {
      this.newGame();
    });

    buttonMap.put(Instructions.ADD_PLAYER, () -> {
      this.addPlayerScreen();
    });

    buttonMap.put(Instructions.ADD, () -> {
      this.addPlayer();
    });

    buttonMap.put(Instructions.PLAY_GAME, () -> {
      timer.start();
      this.displayBoard("Play Game");
    });
   
    ButtonListener buttonListener = new ButtonListener();
    buttonListener.setButtonClickedActionMap(buttonMap);
    this.view.addActionListener(buttonListener);
    
  }

  private void displayBoard(String message) {
    logger.add(message);
    if (isGameOver()) {
      timer.stop();
      if (numberOfTurns < 1) {
        logger.add("Game Over. Target Escaped. Nobody wins.");
        this.view.endScreen("Game Over. Target Escaped. Nobody wins.");
      } else {
        Player lastPlayer = world.getCurrentPlayer();
        logger.add(lastPlayer + "killed the target and won the game!");
        this.view.endScreen(lastPlayer + "killed the target and won the game!");
      }
    } else {
      StringBuilder buildMessage = new StringBuilder();
      buildMessage.append("<html>");
      int start = this.logger.size() - 200 < 0 ? 0 : this.logger.size() - 200;
      for (int i = start; i < this.logger.size(); i++) {
        buildMessage.append(this.logger.get(i) + "<br>");
      }
      buildMessage.append("</html>");

      try {
        this.view.refreshBoard(this.world.getSpaceLocation(), this.world.getPlayerLocations(),
             this.world.getPetLocation(), this.world.getTargetLocation(),
             this.world.getCurrentPlayer().getName(), this.execute(Action.DISPLAY_TARGET),
             numberOfTurns, buildMessage.toString(), mouseListener);
      } catch (NullPointerException | IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    
  }

  private void addPlayerScreen() {
    timer.stop();
    if (!isGameOver()) {
      this.view.addPlayerScreen(this.world.getSpacesNames());
    } else {
      this.view.displayErrorMsg("Game Over.");
    }
  }
  
  private void addPlayer() {
    String[] userInputs = this.view.getPlayerInput();
    if (Objects.nonNull(userInputs)) {
      try {
        this.execute(Action.ADD_HUMAN_PLAYER, userInputs);
        this.view.setPlayerColor(this.world.getSpacesNames());
      } catch (NullPointerException | IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Action to quit the game.
   */
  public void quitGame() {
    int choice = this.view.confirmation("Are you sure to quit the game?");
    if (choice == JOptionPane.YES_OPTION) {
      this.view.quit();
    }
  }

  /**
   * Action to restart the game.
   */
  public void restartGame() {
    int input = this.view.confirmation(
        "Do you want to restart the game with the same settings?");
    if (input == JOptionPane.YES_OPTION) {
      timer.stop();
      WorldBuilder builder = new WorldBuilder();
      String filePath = "/Users/marinalin/Desktop/myworld.txt";
      try {
        this.world = builder.buildWorldFromConfig(new FileReader(filePath));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      this.logger = new ArrayList<>();
      this.view.restartGame(this.world.getSpacesNames());  
    }
  }

  private void createNewGame() {
    int input = this.view.confirmation("Are you sure you want to start a new game?");
    if (input == JOptionPane.YES_OPTION) {
      timer.stop();
      this.view.initialScreen();
    }
  }

  /**
   * Action to start a new game.
   */
  public void newGame() {
    String[] inputs = this.view.getInitialInput();
    if (Objects.nonNull(inputs)) {
      try {
        WorldBuilder builder = new WorldBuilder();
        this.world = builder.buildWorldFromConfig(new FileReader(inputs[0]));
        this.logger = new ArrayList<>();
        this.view.displayInfo("New Game Created.");
        this.view.addPlayerScreen(this.world.getSpacesNames());
      } catch (FileNotFoundException e) {
        this.view.displayErrorMsg("File is not present.");
      } catch (NumberFormatException e) {
        this.view.displayErrorMsg("Invalid File.");
      } catch (IllegalArgumentException | NullPointerException exception) {
        this.view.displayErrorMsg(exception.getMessage());
      }
    }
  }

  private void computerAction() {
    if (!this.world.isHuman()) {
      Action chosenAction = player.ComputerPlayer.chooseActionGui();
      String message = this.world.getCurrentPlayer().getName() + "tried to "
                + chosenAction.toString();
      switch (chosenAction) {
        case MOVE:
          this.displayBoard(message);
          break;
        case PICKUP:
          this.displayBoard(message);
          break;
        case ATTEMPT_KILL:
          this.displayBoard(message);
          break;
        case LOOKAROUND:
          this.displayBoard(message);
          break;
        default:
          break;
      }
    }

  }

  @Override
  public void playGame() throws 
        NullPointerException, IllegalArgumentException, IllegalAccessException {
    this.view.welcomeScreen();
  }

  @Override
  public String execute(Action action, String... args)
        throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    String result = "";

    switch (action) {
      case MOVE:
        String spaceToMove = args[0];
        world.movePlayer(spaceToMove);
        result = "Moved to " + spaceToMove;
        numberOfTurns--;
        break;
      case PICKUP:
        String itemName = args[0];
        world.pickItem(itemName);
        result = "Picked up " + itemName;
        numberOfTurns--;
        break;
      case ADD_HUMAN_PLAYER:
        WorldImpl.addPlayer(args[0], args[1], Integer.parseInt(args[2]), true);
        break;
      case LOOKAROUND:
        result = world.lookAround();
        numberOfTurns--;
        break;
      case DISPLAY_TARGET:
        result = world.getTargetLocation();
        break;
      case DISPLAY_PLAYER:
        result = world.getDetailsOfPlayer(args[0]);
        break;
      case ATTEMPT_KILL:
        result = world.attemptKill(args[0]);
        numberOfTurns--;
        break;
      default:
        break;
    }
    return result;
  }
  
  /**
   * Determines the end state of the game and returns an appropriate message.
   * This method checks the current state of the game to determine if it has ended.
   * There are two conditions for the game to end:
   * 1. The target character is killed: In this case, it identifies the last player 
   *    (human or computer) who killed the target and declares them as the winner.
   * 2. The maximum number of turns is reached without killing the target: The game ends 
   *    without a winner, and it's declared that the target character has escaped.
   * If neither of these conditions is met, the game is considered to be still ongoing.
   *
   * @return A string message indicating the end of the game with the outcome. 
   *         It could be a winning message, a message about the target escaping, 
   *         or a message indicating that the game is still ongoing.
   */
  public boolean isGameOver() {
    if (!this.world.getTarget().isAlive()) {
      return true;
    } else if (numberOfTurns <= 0) {
      return true;
    }
    return false;
  }


}
