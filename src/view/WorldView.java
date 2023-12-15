package view;

import java.util.List;
import java.util.Map;
import listener.ButtonListener;
import listener.KeyboardListener;
import listener.MouseClickListener;

/**
 * Interface of the view implementation for the world in this game.
 */
public interface WorldView {
  
  /**
   * Display list of items in the game and their corresponding damage values. 
   * 
   * @param items list of items and their damage values.
   * @return the selected item name.
   */
  String displayItems(Map<String, Integer> items);
  
  /**
   * Display the info to user.
   * 
   * @param message info message
   */
  void displayInfo(String message);
  
  /**
   * Display the error message to user.
   * 
   * @param error message
   */
  void displayErrorMsg(String error);
  
  /**
   * Set up the initial screen of the game.
   */
  void initialScreen();
  
  /**
   * Welcome screen of the game.
   */
  void welcomeScreen();
  
  /**
   * The screen to add player to the game.
   * 
   * @param spaceNames list of space names.
   */
  void addPlayerScreen(List<String> spaceNames);
  
  /**
   * Set the color to a player.
   * 
   * @param spaceNames list of space names.
   */
  void setPlayerColor(List<String> spaceNames);

  /**
   * Ask confirmations from the user.
   * 
   * @param message the message to ask for confirmation
   * @return integer value of the selected option
   */
  int confirmation(String message);
  
  /**
   * End screen of the game.
   * 
   * @param result result of the game.
   */
  void endScreen(String result);
  
  /**
   * Option to quit the game.
   */
  void quit();
  
  /**
   * Option to restart the game. 
   * 
   * @param spaceNames list of spaces in the game.
   */
  void restartGame(List<String> spaceNames);
  
  /**
   * Option to refresh the board and display the current game status.
   * 
   * @param spaces Map of the space names with their locations. 
   * @param players Map of players with their locations.
   * @param petLocation pet's location
   * @param targetLocation target's location
   * @param currentPlayer current player of the game.
   * @param targetDetails details of the target
   * @param numberOfTurns number of turns left in the game.
   * @param message result of the previous action
   * @param listener Listener to perform actions.
   */
  void refreshBoard(Map<String, int[]> spaces, Map<String, String> players, String petLocation, 
       String targetLocation, String currentPlayer, String targetDetails, 
       int numberOfTurns, String message, MouseClickListener listener);
  
  /**
   * Add button listener to the game components. 
   * 
   * @param buttonListener button listener
   */
  void addActionListener(ButtonListener buttonListener);
  
  /**
   * Add keyboard listener to game components.
   * 
   * @param keyboardListener keyboard listener
   */
  void addActionListener(KeyboardListener keyboardListener);
  
  /**
   * Get the setup input to start the new game.
   * 
   * @return String array that consists of file path for the new config file, 
   *         number of turns, and maximum number of players for the new game. 
   */
  String[] getInitialInput();
  
  /**
   * Get the input from user to create players. 
   * 
   * @return String array that consists of the name of the player, starting location, 
   *         capacity to pick items, and player type(human/computer).
   */
  String[] getPlayerInput();
  
}
