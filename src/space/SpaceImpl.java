package space;

import item.Item;
import java.util.ArrayList;
import java.util.List;
import models.Point;

/**
 * Represents a specific space in the game.
 * The SpaceImpl class implements the properties and behaviors defined in the Space interface.
 */
public class SpaceImpl implements Space {
  private String name;
  private List<Item> items;
  private Point upperLeftCorner;
  private Point lowerRightCorner;

  /**
   * Constructor for the SpaceImpl class.
   * 
   * @param name The name of the space.
   * @param upperLeftCorner The upper left corner of the space as a Point.
   * @param lowerRightCorner The lower right corner of the space as a Point.
   */
  public SpaceImpl(String name, Point upperLeftCorner, Point lowerRightCorner) {
    this.name = name;
    this.items = new ArrayList<>();
    this.upperLeftCorner = upperLeftCorner;
    this.lowerRightCorner = lowerRightCorner;
  }
  
  @Override
  public String getName() {
    return name;
  }

  @Override
  public Item getItems(String itemName) {
    return items.isEmpty() ? null : items.get(0); // return the first item here
  }
  
  @Override
  public List<String> getListOfItems() {
    List<String> itemNames = new ArrayList<>();
    for (Item item : items) {
      itemNames.add(item.getName());
    }
    return itemNames;
  }
  
  @Override
  public void addItem(Item item) {
    items.add(item);
  }
  
  @Override
  public void removeItem(String itemName) {
    items.remove(itemName);
  }
  
  /**
 * Retrieves the bottom-right coordinates of a rectangular space or area.
 *
 * @return An integer array of size 2, where the first element represents the x-coordinate
 *         and the second element represents the y-coordinate of the bottom-right location.
 */
  public int[] getBottomRightLocation() {
    int[] coordinates = new int[2];
    coordinates[0] = lowerRightCorner.getX();
    coordinates[1] = lowerRightCorner.getY();
    return coordinates;
  }

  @Override
  public int[] getLocation() {
    int[] topLeft = new int[2];
    topLeft[0] = upperLeftCorner.getX();
    topLeft[1] = upperLeftCorner.getY(); 
    int[] bottomRight = getBottomRightLocation();
    return new int[] {topLeft[0], topLeft[1], bottomRight[0], bottomRight[1]};
  }
  
  /**
   * Returns a string representation of the space.
   *
   * @return A string detailing the name and location of the space.
   */
  @Override
  public String toString() {
    return String.format("Space: %s from %s to %s with items: %s", name, 
           upperLeftCorner.toString(), lowerRightCorner.toString(), getListOfItems());
  }
  
  /**
   * Retrieves an item from the game world based on its name.
   *
   * @param name The name of the item to be retrieved. Must not be {@code null}.
   * @return The item with the specified name or {@code null} if the item does not exist.
   * 
   */
  public Item getItemByName(String name) {
    for (Item item : this.items) {
      if (item.getName().equals(name)) {
        return item;
      }
    }
    return null;
  }
}
