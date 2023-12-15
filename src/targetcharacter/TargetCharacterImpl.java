package targetcharacter;

/**
 * Represents a character in the World game.
 * A character has a name, health, and is located in a space.
 */
public class TargetCharacterImpl implements TargetCharacter {
  private String name;
  private int health;
  private int spaceNumber;
  
  /**
   * Constructs a new Character.
   *
   * @param name The name of the character.
   * @param health The initial health or resilience of the character.
   * @param spaceNumber The location in index of spaces of the target character.
   */
  public TargetCharacterImpl(String name, int health, int spaceNumber) {
    this.name = name;
    this.health = health;
    this.spaceNumber = spaceNumber;
  }
  
  /**
   * Returns the name of the character.
   *
   * @return The character's name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets a new name for the character.
   *
   * @param name The new character name.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Returns the health of the character.
   *
   * @return The character's health.
   */
  public int getHealth() {
    return health;
  }
  
  @Override
  public int getLocation() {
    return spaceNumber;
  }
  
  @Override
  public void updateLocation(int spaceNumber) {
    this.spaceNumber = spaceNumber;
  }
  
  @Override
  public boolean isAlive() {
    return health > 0;
  }
  
  @Override
  public void reduceHealth(int damage) {
    this.health = Math.max(this.health - damage, 0);
  }
  
  /**
   * Returns a string representation of the character.
   *
   * @return A string detailing the name, health, and location of the character.
   */
  @Override
  public String toString() {
    return String.format("TargetCharacterImpl[name=%s, health=%d, location=%s]", 
           name, health, spaceNumber);
  }

}
