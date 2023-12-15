package item;

/**
 * Represents an item in the Jurassic World game.
 * An item has a name, a damage value, and a location (Space).
 */
public class ItemImpl implements Item {
  private String name;
  private int damage;

  /**
   * Constructor for the ItemImpl class.
   * 
   * @param name   The name of the item.
   * @param damage The damage this item causes.
   */
  public ItemImpl(String name, int damage) {
    this.name = name;
    this.damage = damage;
  }
  
  /**
   * Returns the name of the item.
   *
   * @return The item name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets a new name for the item.
   *
   * @param itemName The new item name.
   */
  public void setItemName(String itemName) {
    this.name = itemName;
  }
  
  /**
   * Returns the damage value of the item.
   *
   * @return The damage value.
   */
  public int useItem() {
    return damage;
  }
  
  /**
   * Sets a new damage value for the item.
   *
   * @param damage The new damage value.
   */
  public void setDamage(int damage) {
    this.damage = damage;
  }

  
  /**
   * Returns a string representation of the item.
   *
   * @return A string detailing the name, damage value, and location of the item.
   */
  @Override
  public String toString() {
    return String.format("%s : %s", name, damage);
  }

}
