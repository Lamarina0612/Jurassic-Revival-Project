package player;

import item.Item;
import java.util.List;


/**
 * Represents a player in the game, providing methods to get the player's 
 * name, health, location, and determine if the player is a human.
 * 
 * <p>The Player interface provides a unified representation for a player,
 * regardless of the specific implementation or type of player (e.g., human, computer).</p>
 * 
 * @author Marina Lin
 */
public interface Player {
  /**
   * Retrieves the name of the player.
   * 
   * @return The name of the player.
   */
  String getName();
  
  /**
   * Retrieves the current health of the player.
   * 
   * @return The health value of the player.
   */
  int getHealth();
  
  /**
   * Retrieves the current location of the player in the game.
   * 
   * @return The location of the player, represented as an int of the space index.
   */
  int getLocation();
  
  /**
   * Determines if the player is human.
   * 
   * @return True if the player is human, false otherwise.
   */
  boolean isHuman();
  
  /**
   * Updates the location of the player to a new specified location.
   * 
   * @param newLocation The new location to which the player should move.
   */
  void updateLocation(int newLocation);

  /**
   * Add item to the player.
   * 
   * @param items items that the player is carrying.
   */
  void addItem(Item items);
  
  /**
   * Show the items.
   */
  List<Item> getItems();
  
  /**
  * Remove existing items.
  */
  void removeItem(String itemName);
  
  
}
