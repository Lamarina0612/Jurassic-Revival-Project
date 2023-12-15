package item;

/**
 * Represents an item in the game.
 * <p>
 * Items can have various functionalities and attributes that can impact gameplay.
 * </p>
 */
public interface Item {

  /**
   * Gets the name of the item.
   * 
   * @return The name of the item.
   */
  public String getName();
  
  /**
   * Uses the item, triggering its damage.
   * 
   * @return damage this item causes.
   */
  public int useItem();
}
