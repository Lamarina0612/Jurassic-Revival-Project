package controller;

import java.util.Random;
import java.util.Scanner;
import player.ComputerPlayer;
import player.Player;
import space.SpaceImpl;
import utils.Action;
import world.WorldImpl;

/**
 * Represents a game controller that operates through the console.
 * 
 * <p>The {@code ConsoleController} class provides an implementation of the game
 * controller that interacts with the user through the command-line console.
 * It inherits the common functionalities from {@code AbstractGameController} and 
 * implements the specific behaviors required for console-based interaction.</p>
 * 
 * <p>Players can input commands, receive feedback, and view game progress directly
 * in the console interface. This class is responsible for parsing console input, 
 * interpreting game commands, and displaying appropriate responses to the user.</p>
 * 
 * @author Marina Lin
 * @version Milestone 2
 * @see AbstractGameController
 */
public class ConsoleController extends AbstractGameController {
  private Scanner scanner;
  private Appendable output;
  private int numberOfTurns;
  
  /**
   * Initializes a new instance of the {@code ConsoleController} with 
   * the specified input, output, game model, and number of turns.
   * 
   * <p>This constructor sets up a console-based game controller by 
   * defining the sources of input and output, 
   * linking it to the game world model, and setting the 
   * number of turns for gameplay. It ensures that the game 
   * controller can read player commands from a keyboard input, relay feedback to a 
   * designated output (e.g., console), and interact with the game model accordingly.</p>
   * 
   * @param in    the source from which to read user input, typically the console input
   * @param append the destination to which game feedback and responses
   *        will be written, typically the console output
   * @param model the game world model that represents the current state and logic of the game
   * @param turns the total number of turns allocated for gameplay
   * 
   * @throws IllegalArgumentException if any of the provided arguments are null
   * @see WorldImpl
   * @see AbstractGameController
   */
  public ConsoleController(Readable in, Appendable append, WorldImpl model, int turns) {
    this.scanner = new Scanner(in);
    this.output = append;
    this.world = model;
    this.numberOfTurns = turns;

        
  }
  
  @Override
  public String execute(Action action, String... args) 
      throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    String result = "";
    
    switch (action) {
      case MOVE:
        System.out.println("Enter space name: ");
        String spaceToMove = scanner.nextLine();
        args = new String[]{spaceToMove};
        world.movePlayer(args[0]);
        result = "Moved to " + args[0];
        System.out.println(result);
        System.out.println(result);
        break;
      case PICKUP:
        System.out.println("Enter item name: ");
        String itemName = scanner.nextLine();
        args = new String[]{itemName};
        world.pickItem(args[0]);
        result = "Picked up " + args[0];
        System.out.println(result);
        break;
      case LOOKAROUND:
        result = world.lookAround();
        System.out.println(result);
        break;
      case DISPLAY_SPACE:
        result = world.getSpaceInfo();
        System.out.println(result);
        break;
      case GENERATE_IMAGE:
        result = "Generated image completed"; 
        world.generateImage();
        System.out.println(result);
        break;
      case ADD_HUMAN_PLAYER:
        System.out.println("Enter player name: ");
        String playerName = scanner.nextLine();
        System.out.println("Enter space name: ");
        String spaceName = scanner.nextLine();
        System.out.println("Enter player capacity: ");
        String playerCapacity = scanner.nextLine();
        args = new String[]{playerName, spaceName, playerCapacity};
        ((WorldImpl) world).addPlayer(args[0], args[1], Integer.parseInt(args[2]), true);
        result = "Added human player " + args[0];
        System.out.println(result);
        break;
      case ADD_COMPUTER_PLAYER:
        Random rand = new Random();
        int randomSpaceIndex = rand.nextInt(WorldImpl.getNumberOfSpaces());
        SpaceImpl startingSpace = WorldImpl.getSpaceByIndex(randomSpaceIndex);
        String startingSpaceName = startingSpace.getName();
        String computerPlayerName = "ComputerPlayer" + (WorldImpl.getNumberOfPlayers() + 1);

        int computerPlayerCapacity = 5;

        WorldImpl.addPlayer(computerPlayerName, startingSpaceName, computerPlayerCapacity, false);

        result = "Added a computer player named " 
                 + computerPlayerName + " at " + startingSpaceName + ".";
        System.out.println(result);
        break;
      case DISPLAY_PLAYER:
        System.out.println("Enter player name: ");
        String name = scanner.nextLine();
        args = new String[]{name};
        result = world.getDetailsOfPlayer(args[0]);
        System.out.println(result);
        break; 
      case ATTEMPT_KILL:
        result = ((WorldImpl) world).attemptKill(world.getCurrentPlayer().getName());
        System.out.println(result);
        break;
      default:
        result = "Unknown action!";
        break;
    }
    return result;
  }
   
  private void printMenu() {
    System.out.println("Available Actions:");
    System.out.println("1. MOVE - Move to a different space.");
    System.out.println("2. PICKUP - Pick up an item.");
    System.out.println("3. LOOKAROUND - Look around the current space.");
    System.out.println("4. DISPLAY_SPACE - Display information about the current space.");
    System.out.println("5. GENERATE_IMAGE - Generate an image representation of the world.");
    System.out.println("6. ADD_HUMAN_PLAYER - Add a human player.");
    System.out.println("7. ADD_COMPUTER_PLAYER - Add a computer player.");
    System.out.println("8. DISPLAY_PLAYER - Display information about a specific player.");
    System.out.println("9. ATTEMPT KILL - Make an attempt on the target character's life.");
    System.out.println("Enter your action:");
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
  public String endGame() {
    if (!((WorldImpl) world).getTarget().isAlive()) {
      Player lastPlayer = world.getCurrentPlayer();
      String winnerType = lastPlayer.isHuman() ? "Human player" : "Computer Player";
      return "Target is killed by " + lastPlayer.getName() + ". " + winnerType + " wins!";
    } else if (numberOfTurns <= 0) {
      int targetHealth = ((WorldImpl) world).getTarget().getHealth();
      return "The target escaped with " + targetHealth + " health points. No one wins.";
    }
    
    return "Game is still ongoing.";
  }
    
  @Override
  public void playGame() 
       throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    while (WorldImpl.getNumberOfPlayers() == 0) {
      System.out.println("No players in the game. Please add players to start.");
      printMenu();
      System.out.println("Enter action to add players (ADD_HUMAN_PLAYER or ADD_COMPUTER_PLAYER): ");
      int actionChoice = Integer.parseInt(scanner.nextLine());
      Action action = mapNumberToAction(actionChoice);
      execute(action);
    }
    
    while (numberOfTurns > 0 && ((WorldImpl) world).getTarget().isAlive()) {
      Player currentPlayer = world.getCurrentPlayer();
      System.out.println("Current Player: " + currentPlayer.getName());
      
      if (currentPlayer.isHuman()) {
        printMenu();
        int actionChoice = Integer.parseInt(scanner.nextLine());
        Action action = mapNumberToAction(actionChoice);
        
        if (action == Action.MOVE || action == Action.PICKUP || action == Action.MOVE) {
          execute(action);
          numberOfTurns--;
        } else {
          execute(action);
        }
      } else {
        String result = handleComputerPlayerAction(currentPlayer);
        System.out.println(result);
        ((WorldImpl) world).endOfTurn();
        numberOfTurns--;
      }
    }
    String gameOutcome = endGame();
    System.out.println(gameOutcome);
  }
  
  private String handleComputerPlayerAction(Player player)
        throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    if (player instanceof ComputerPlayer) {
      return ((ComputerPlayer) player).chooseAction((WorldImpl) world);
    } else {
      return "Non-human player action not implemented.";
    }
  }
  
  private Action mapNumberToAction(int number) {
    switch (number) {
      case 1: return Action.MOVE;
      case 2: return Action.PICKUP;
      case 3: return Action.LOOKAROUND;
      case 4: return Action.DISPLAY_SPACE;
      case 5: return Action.GENERATE_IMAGE;
      case 6: return Action.ADD_HUMAN_PLAYER;
      case 7: return Action.ADD_COMPUTER_PLAYER;
      case 8: return Action.DISPLAY_PLAYER;
      case 9: return Action.ATTEMPT_KILL;
      default: return null; // Handle invalid numbers
    }
  }
  

}
