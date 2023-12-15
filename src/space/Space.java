package space;

import item.Item;
import java.util.List;

/**
 * Represents a space in the game.
 * <p>
 * Each space can have various attributes such as items, characters, or specific events.
 * </p>
 */
public interface Space {

  /**
   * Gets the name of the space.
   * 
   * @return The name of the space.
   */
  String getName();
  
  /**
   * Gets a specific item from the space.
   *
   * @param itemName the name of the item.
   * @return An item from the space.
   */
  Item getItems(String itemName);
  
  /**
   * Gets a list of item names in the space.
   *
   * @return A list of item names.
   */
  List<String> getListOfItems();
  
  /**
   * Adds an item to the space.
   *
   * @param item The item to add.
   */
  void addItem(Item item);
  
  /**
   * Removes an item from the space.
   *
   * @param item The item to remove.
   */
  void removeItem(String item);
  
  /**
   * Gets the location of the space.
   *
   * @return The location of the space as coordinates.
   */
  int[] getLocation();
}
