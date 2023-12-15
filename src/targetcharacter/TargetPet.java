package targetcharacter;

/**
 * Represents a target pet in the game.
 * <p>
 * The TargetPet interface defines the essential methods 
 * that a target pet must implement.
 * </p>
 */
public interface TargetPet {
  /**
  * Retrieves the name of the target pet.
  *
  * @return The name of the target pet.
  */
  String getName();

  /**
  * Retrieves the location of the target pet.
  *
  * @return The location of the target pet.
  */
  int getLocation();
  
  /**
   * Updates the location of the target pet.
   * 
   * @param spaceNumber The new space number for the target pet to move to.
   */
  void updateTargetPetLocation(int spaceNumber);
}
