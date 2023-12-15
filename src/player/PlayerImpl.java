package player;

import item.Item;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a player in the game with attributes like name, health, location, and human status.
 * 
 * <p>This class provides a concrete implementation of the Player interface.</p>
 * 
 * @author Marina Lin
 * @version Milestone 2
 */
public class PlayerImpl implements Player { 
  
  private final String name;
  private int health;
  private int capacity;
  private int location;
  private boolean isHuman;
  private List<Item> itemList;
  
  /**
   * Constructor for the PlayerImpl class.
   * 
   * @param name The name of the player.
   * @param capacity the number of items that this play can pick.
   * @param location The initial location of the player in the game.
   * @param isHuman A boolean indicating if the player is human or not.
   */
  public PlayerImpl(String name, int location, int capacity, boolean isHuman) {
    this.name = name;
    this.capacity = capacity;
    this.location = location;
    this.isHuman = isHuman;
    this.itemList = new ArrayList<>();
  }
  
  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public int getLocation() {
    return location;
  }

  @Override
  public boolean isHuman() {
    return isHuman;
  }

  @Override
  public void updateLocation(int newLocation) {
    this.location = newLocation;
  }
  
  
  @Override
  public String toString() {
    return String.format("PlayerImpl[name=%s, health=%d, location=%s, isHuman=%b]", 
           name, health, location, isHuman);
  }

  @Override
  public void addItem(Item items) {
    if (this.capacity <= 0) {
      throw new IllegalStateException("No capacity to add this item for " + this.getName());
    }
    this.itemList.add(items);
  }

  @Override
  public List<Item> getItems() {
    return new ArrayList<>(itemList);
  }

  @Override
  public void removeItem(String itemName) {
    itemList.removeIf(item -> item.getName().equals(itemName));
  }
  
}
