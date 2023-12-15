package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import listener.ButtonListener;
import listener.KeyboardListener;
import listener.MouseClickListener;
import utils.Instructions;

/**
 * GUI view implementation for the game.
 * 
 */
public class WorldGuiView extends JFrame implements WorldView {

  private static final long serialVersionUID = -7703211473500041741L;
  private GameMenu menu;
  private SidePanel sidePanel;
  private GameMainPanel mainPanel;
  private JPanel welcomePanel;
  private JPanel addPlayerPanel;
  private JPanel newGamePanel;
  private JButton startButton;
  private JSplitPane splitPane;
  private Map<String, Color> playersColor;
  private JTextField name;
  private JComboBox<String> location;
  private JTextField capacity;
  private JRadioButton computer;
  private JRadioButton human;
  private JButton playGame;
  private Color chosenColor;
  private String filePath;
  private JTextField maxPlayer;
  private JTextField numberOfTurns;
  
  /**
   * Constructor of the view implementation. 
   * 
   * @param head name of the view.
   */
  public WorldGuiView(String head) {
    super(head);
    ImageIcon icon = new ImageIcon("res/images/logo.png");
    this.setIconImage(icon.getImage());
    this.setLayout(new BorderLayout());
    this.setSize(1000, 1000);
    this.setLocation(10, 10);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.playersColor = new HashMap<>();
    this.filePath = null;
    this.playGame = new JButton();
    
    // welcome panel
    welcomePanel = new JPanel();
    startButton = new JButton("Start the game");
    
    // add player panel
    addPlayerPanel = new JPanel();
    newGamePanel = new JPanel();
    
    this.menu = new GameMenu();
    this.setJMenuBar(this.menu);
    
    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setResizeWeight(0.75);
    splitPane.setEnabled(false);
    splitPane.setDividerSize(0);
    
    this.mainPanel = new GameMainPanel();
    this.mainPanel.setPreferredSize(new Dimension(1000, 1500));
    splitPane.add(new JScrollPane(this.mainPanel));
    
    this.sidePanel = new SidePanel();
    splitPane.add(new JScrollPane(this.sidePanel));
    this.add(splitPane);
   
  }
  
  private void refresh() {
    this.revalidate();
    this.repaint();
    this.setVisible(true);
  }
 
  @Override
  public String displayItems(Map<String, Integer> items) {
    if (items.isEmpty()) {
      this.displayInfo("There is no items to pick up.");
      return null;
    }
    String[] choices = new String[items.size()];
    int index = 0;
    for (String key : items.keySet()) {
      Integer value = items.get(key);
      choices[index] = String.format("%s: Damage %s", key, value.toString());
      ++index;
    }
    String chosenItem = JOptionPane.showInputDialog(null, "Select one item to pick up.",
            "PICK UP", JOptionPane.QUESTION_MESSAGE, null, choices, null).toString();
    return Objects.nonNull(chosenItem) ? chosenItem.split(":")[0] : chosenItem;
  }
  
  @Override
  public void displayInfo(String message) {
    ImageIcon icon = new ImageIcon("res/images/logo.png");
    JOptionPane.showMessageDialog(this, message, "Information", 
           JOptionPane.INFORMATION_MESSAGE, icon);
  }
  
  @Override 
  public void displayErrorMsg(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
  
  @Override 
  public void restartGame(List<String> spaceNames) {
    this.getContentPane().removeAll();
    if (addPlayerPanel.getComponents().length == 0) {
      this.addPlayerScreen(spaceNames);
    }
    this.playersColor.clear();
    this.add(addPlayerPanel);
    this.refresh();
  }
  
  @Override
  public void addPlayerScreen(List<String> spaceNames) {
    addPlayerPanel.removeAll();
    
    // layout
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 5, 0, 5);
    GridBagLayout grid = new GridBagLayout();
    addPlayerPanel.setLayout(grid);

    JLabel top = new JLabel("Add a Player to Game", SwingConstants.CENTER);
    top.setFont(new Font(Font.SERIF, Font.PLAIN, 25));
    top.setForeground(Color.MAGENTA);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipadx = 200;
    gbc.ipady = 20;
    addPlayerPanel.add(top, gbc);
    
    JLabel inputType = new JLabel(" Choose type of player:");
    gbc.gridx = 0;
    gbc.gridy = 1;
    addPlayerPanel.add(inputType, gbc);

    computer = new JRadioButton("Computer Player");
    human = new JRadioButton("Human Player");
    human.setSelected(true);
    ButtonGroup group = new ButtonGroup();
    group.add(computer);
    group.add(human);
    
    JButton color = new JButton("Choose Color");
    color.setFocusPainted(false);
    gbc.gridx = 0;
    gbc.gridy = 10;
    gbc.insets = new Insets(0, 0, 15, 0);
    addPlayerPanel.add(color, gbc);
    chosenColor = null;
    color.addActionListener((ActionEvent e) -> {
      chosenColor = JColorChooser.showDialog(null, "Select a color for player", Color.BLACK);
    });

    JLabel inputName = new JLabel("Player Name:");
    gbc.gridx = 0;
    gbc.gridy = 4;
    addPlayerPanel.add(inputName, gbc);

    name = new JTextField();
    gbc.gridx = 0;
    gbc.gridy = 5;
    addPlayerPanel.add(name, gbc);

    JLabel inputLocation = new JLabel("Choose your initial Location:");
    gbc.gridx = 0;
    gbc.gridy = 6;
    addPlayerPanel.add(inputLocation, gbc);

    location = new JComboBox<>(spaceNames.toArray(new String[0]));
    gbc.gridx = 0;
    gbc.gridy = 7;
    addPlayerPanel.add(location, gbc);

    JLabel inputCapacity = new JLabel("Capacity of Player's Bag:");
    gbc.gridx = 0;
    gbc.gridy = 8;
    addPlayerPanel.add(inputCapacity, gbc);

    capacity = new JTextField();
    gbc.gridx = 0;
    gbc.gridy = 9;
    addPlayerPanel.add(capacity, gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 2;
    addPlayerPanel.add(computer, gbc);
    gbc.gridx = 0;
    gbc.gridy = 3;
    addPlayerPanel.add(human, gbc);

    startButton.setText(Instructions.ADD);
    startButton.setActionCommand(Instructions.ADD);
    startButton.setFocusPainted(false);
    startButton.setBackground(null);
    startButton.setForeground(null);
    startButton.setFont(null);
    gbc.gridx = 0;
    gbc.gridy = 11;
    addPlayerPanel.add(startButton, gbc);

    playGame.setText(Instructions.PLAY_GAME);
    playGame.setFocusPainted(false);
    gbc.gridx = 0;
    gbc.gridy = 12;
    addPlayerPanel.add(playGame, gbc);

    addPlayerPanel.setSize(400, 350);
    addPlayerPanel.setVisible(true);
    addPlayerPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    this.getContentPane().removeAll();
    this.add(addPlayerPanel);
    this.refresh();
  }
  
  /**
   * Get player input for color, capacity, name and location.
   * 
   * @return String array of player setting in the game.
   */
  public String[] getPlayerInput() {
    try {
      Integer.parseInt(capacity.getText());
      Objects.requireNonNull(chosenColor, "Please choose a color for the player.");
      if (playersColor.containsValue(chosenColor)) {
        throw new IllegalArgumentException("This color has been chosen by other player.");
      }
      
      return new String[] {name.getText(), location.getSelectedItem().toString(),
             capacity.getText(), String.valueOf(human.isSelected())};
    } catch (NumberFormatException e) {
      this.displayErrorMsg("Capacity has to be a number");
    }
    
    return null;
  }
  
  @Override
  public void setPlayerColor(List<String> spaceNames) {
    this.playersColor.put(name.getText(), chosenColor);
    this.displayInfo("Player added successfully!");
    this.addPlayerScreen(spaceNames);
  }
  
  @Override
  public void initialScreen() {
    this.playersColor.clear();
    newGamePanel.removeAll();
    this.setTitle("New Game");
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 5, 0, 5);
    GridBagLayout grid = new GridBagLayout();
    newGamePanel.setLayout(grid);

    JLabel top = new JLabel("Set Up New Game", SwingConstants.CENTER);
    top.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
    top.setForeground(Color.MAGENTA);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipadx = 200;
    gbc.ipady = 20;
    newGamePanel.add(top, gbc);

    JButton inputFile = new JButton("Choose World File");
    gbc.gridx = 0;
    gbc.gridy = 1;
    inputFile.setFocusPainted(false);
    newGamePanel.add(inputFile, gbc);

    inputFile.addActionListener((ActionEvent e) -> {
      File workingDirectory = new File(System.getProperty("user.dir"));
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(filter);
      fileChooser.setCurrentDirectory(workingDirectory);
      fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      int returnVal = fileChooser.showOpenDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        filePath = file.getPath();
      }
    });

    JLabel inputTurns = new JLabel("Number of Turns:");
    gbc.gridx = 0;
    gbc.gridy = 2;
    newGamePanel.add(inputTurns, gbc);

    numberOfTurns = new JTextField();
    gbc.gridx = 0;
    gbc.gridy = 3;
    newGamePanel.add(numberOfTurns, gbc);

    JLabel inputMaxPlayer = new JLabel("Maximum of Players:");
    gbc.gridx = 0;
    gbc.gridy = 4;
    newGamePanel.add(inputMaxPlayer, gbc);

    maxPlayer = new JTextField();
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.insets = new Insets(0, 0, 15, 0);
    newGamePanel.add(maxPlayer, gbc);

    startButton.setText("Start New Game");
    startButton.setFocusPainted(false);
    startButton.setActionCommand(Instructions.CREATE_NEW_GAME);
    gbc.gridx = 0;
    gbc.gridy = 6;
    newGamePanel.add(startButton, gbc);

    newGamePanel.setSize(500, 450);
    newGamePanel.setVisible(true);
    newGamePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    this.getContentPane().removeAll();
    this.add(newGamePanel);
    this.refresh();   
  }
  
  @Override
  public String[] getInitialInput() {
    try {
      Objects.requireNonNull(filePath, "Please choose a file");
      Integer.parseInt(numberOfTurns.getText());
      Integer.parseInt(maxPlayer.getText());
      return new String[] {filePath, numberOfTurns.getText(), maxPlayer.getText()}; 
    } catch (NumberFormatException e) {
      this.displayErrorMsg("You should input number for turns and maximum number of players.");
    }
    
    return null;
  }
  
  @Override
  public void welcomeScreen() {
    welcomePanel.setBackground(new Color(219, 228, 255));

    // logo and style
    JLabel logo = new JLabel();
    logo.setVisible(true);
    ImageIcon icon = new ImageIcon("res/images/start_logo.png");
    logo.setIcon(icon);
    logo.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    // button and style
    startButton.setBackground(new Color(209, 228, 255));
    startButton.setForeground(new Color(0, 68, 128));
    startButton.setFocusPainted(false);
    startButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
    startButton.setActionCommand(Instructions.ADD_PLAYER);

    // Welcome message
    JLabel welcomeMsg = new JLabel(
        "<html>Welcome to Jurassic World Revival!"
            + "<br>An epic journey through a land of prehistoric wonders!",
        SwingConstants.CENTER);
    welcomeMsg.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
    welcomeMsg.setForeground(new Color(2, 125, 212));
    welcomeMsg.setVerticalTextPosition(JLabel.TOP);
    welcomeMsg.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    
    // authors and style
    JLabel authors = new JLabel(
        "<html>Created by Marina Lin"
           + "<br> Northeastern University",
        SwingConstants.CENTER);
    authors.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
    authors.setForeground(new Color(128, 157, 255));
    authors.setVerticalTextPosition(JLabel.BOTTOM);
    authors.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    // layout
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout grid = new GridBagLayout();
    welcomePanel.setLayout(grid);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    welcomePanel.add(logo, gbc);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 2;
    welcomePanel.add(startButton, gbc);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = 100;
    gbc.gridx = 0;
    gbc.gridy = 3;
    welcomePanel.add(authors, gbc);
    
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 1;
    welcomePanel.add(welcomeMsg, gbc);

    welcomePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    welcomePanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

    this.add(welcomePanel);
    this.setVisible(true);
  }
  
  @Override
  public void quit() {
    System.exit(0);
  }
  
  @Override
  public void addActionListener(ButtonListener buttonListener) {
    this.menu.addActionListener(buttonListener);
    startButton.addActionListener(buttonListener);
    playGame.addActionListener(buttonListener);
  }
  
  @Override
  public void addActionListener(KeyboardListener keyboardListener) {
    this.addKeyListener(keyboardListener);
  }
  
  @Override
  public int confirmation(String message) {
    return JOptionPane.showConfirmDialog(this, message, "Choose an option",
           JOptionPane.YES_NO_CANCEL_OPTION);
  }
  
  @Override
  public void refreshBoard(Map<String, int[]> spaces, Map<String, String> players,
              String petLocation, String targetLocation, String currentPlayer, String targetDetails,
              int turns, String message, MouseClickListener listener) {
    this.getContentPane().removeAll();
    this.add(splitPane);
    this.sidePanel.displayListOfPlayers(currentPlayer, playersColor);
    this.sidePanel.displayTargetDetails(targetDetails);
    this.sidePanel.displayTurnsLeft(turns);
    this.mainPanel.logScreen(message);
    this.mainPanel.displaySpaces(spaces, petLocation, message, listener);
    this.mainPanel.displayTarget(targetLocation, petLocation, listener);
    this.mainPanel.displayPlayers(players, playersColor, listener);
    this.requestFocus();
    this.refresh();
  }
  
  @Override
  public void endScreen(String result) {
    this.remove(splitPane);
    this.welcomePanel.removeAll();
    welcomePanel.setBackground(new Color(255, 255, 255));
    
    JLabel logo = new JLabel();
    logo.setVisible(true);
    ImageIcon icon = new ImageIcon("res/images/start_logo.png");
    logo.setIcon(icon);
    logo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    JLabel resultLabel = new JLabel(result, SwingConstants.CENTER);
    resultLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
    resultLabel.setForeground(new Color(0, 68, 128));
    resultLabel.setVerticalTextPosition(JLabel.BOTTOM);
    resultLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout grid = new GridBagLayout();
    welcomePanel.setLayout(grid);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    welcomePanel.add(logo, gbc);

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = 100;
    gbc.gridx = 0;
    gbc.gridy = 1;
    welcomePanel.add(resultLabel, gbc);

    welcomePanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    welcomePanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

    this.add(welcomePanel);
    this.revalidate();
    this.repaint();
    this.setVisible(true);
  }

}
