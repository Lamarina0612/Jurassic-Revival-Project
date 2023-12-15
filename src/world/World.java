package world;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import player.Player;

/**
 * Represents the game world in which all the game actions and interactions occur.
 *
 * <p>The World interface provides a blueprint for creating different kinds of game worlds. 
 * It might encompass various spaces, items, players, and other elements relevant to a game.
 * </p>
 * 
 * @see PlayerImpl
 * @see Item
 */
public interface World {
  /**
   * Determines if the current player is a human.
   * 
   * @return true if the current player is human, false otherwise.
   */
  boolean isHuman();
  
  /**
   * Gets information about the current space.
   * 
   * @return Details about the current space.
   */
  String getSpaceInfo();
  
  /**
   * Retrieves the name of the world.
   * 
   * @return The world's name.
   */
  String getWorldName();
  
  /**
   * Gets neighboring spaces to the current space.
   * 
   * @param spaceName the space name to find neighboring spaces.
   * @return A list of neighboring spaces.
   */
  List<String> getNeighboringSpaces(String spaceName);
  
  /**
   * Moves the target character in the world.
   */
  void moveTarget();
  
  /**
   * Moves the current player in the world.
   * 
   * @param spaceName space name that the player move to
   * @throws IllegalAccessException when there is no player.
   * @throws IllegalArgumentException when the space does not have a neighbor or does not exist.
   * @throws NullPointerException when the space name is NULL.
   */
  void movePlayer(String spaceName) throws NullPointerException, 
                                           IllegalArgumentException, IllegalAccessException;

  /**
   * Gets details about a player.
   * 
   * @param name The name of the player.
   * @return The details about the specified player.
   */
  String getDetailsOfPlayer(String name);
  
  /**
   * Retrieves the name of the current player.
   * 
   * @return The name of the current player.
   */
  Player getCurrentPlayer();
  
  /**
   * Allows the player to pick up an item.
   * 
   * @param itemName name that picked up by the player
   */
  void pickItem(String itemName);
  
  /**
   * Allows the player to look around their current location.
   * 
   * @return space info that the player is in.
   */
  String lookAround();

  /**
   * Generates an image representation of the world.
   * 
   * @return An image of the world.
   */
  BufferedImage generateImage();
  
  /**
   * Gets a list of all items in the world.
   * 
   * @return A map of all items with their damage.
   */
  Map<String, Integer> getItems();

  /**
   * Retrieves the names of all spaces in the game world.
   *
   * @return A list of strings, each representing the name of a space in the game world.
  */
  List<String> getSpacesNames();
  
  /**
   * Retrieves the names of all players in the game world.
   *
   * @return A list of strings, each representing the name of a player in the game world.
   */
  List<String> getPlayersNames();

  /**
   * Retrieves the location of the target in the game world.
   *
   * @return A String that contains the target location.
   */
  String getTargetLocation();
  
  /**
   * Retrieves the location of the pet in the game world.
   *
   * @return A String that contains the pet location.
   */
  String getPetLocation();
  
  /**
   * Retrieves the locations of all spaces in the game world.
   *
   * @return A map where each key is a space name and the value is an array 
   *         of integers representing the location coordinates of that space.
   */
  Map<String, int[]> getSpaceLocation();
  
  /**
   * Retrieves the locations of all players in the game world.
   *
   * @return A map where each key is a player's name and the value is the name 
   *         of the space where the player is currently located.
   */
  Map<String, String> getPlayerLocations();

  /**
   * Method to kill the target character.
   * 
   * @param name The name of the attacking player.
   * @return kill result.
   */
  String attemptKill(String name);
  
  

}
