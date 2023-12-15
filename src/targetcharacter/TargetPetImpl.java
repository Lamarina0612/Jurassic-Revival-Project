package targetcharacter;

/**
 * Represents a target pet in the game.
 * <p>
 * The TargetPet interface defines the essential methods 
 * that a target pet must implement.
 * </p>
 */
public class TargetPetImpl implements TargetPet {
  private String name;
  private int location;
  
  /**
   * Constructor of the target pet.
   * 
   * @param name The target pet's name.
   * @param location The target pet's location.
   */
  public TargetPetImpl(String name, int location) {
    this.name = name;
    this.location = location;
  }
  
  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getLocation() {
    return location;
  }

  @Override
  public void updateTargetPetLocation(int spaceNumber) {
    this.location = spaceNumber;
  }

}
