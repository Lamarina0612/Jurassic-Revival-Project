package targetcharacter;

/**
 * Represents a target character in the game.
 * <p>
 * The TargetCharacter interface defines the essential methods 
 * that a target character must implement.
 * </p>
 */
public interface TargetCharacter {

  /**
   * Retrieves the name of the target character.
   *
   * @return The name of the target character.
   */
  String getName();
  
  /**
   * Retrieves the health of the target character.
   *
   * @return The health of the target character.
   */
  int getHealth();
  
  /**
   * Determines if the target character is still alive.
   *
   * @return true if the target character is alive, false otherwise.
   */
  boolean isAlive();
  
  /**
   * Retrieves the location of the target character.
   *
   * @return The location of the target character.
   */
  int getLocation();
  
  /**
   * Updates the location of the target character.
   *
   * @param spaceNumber The new space number for the target character to move to.
   */
  void updateLocation(int spaceNumber);
  
  /**
   * Reduce the health point of the target character.
   *
   * @param damage The damage that the target character receives.
   */
  void reduceHealth(int damage);
}
