import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import targetcharacter.TargetPetImpl;

/**
 * Unit tests for the {@code TargetPet} class.
 * <p>
 * This test class aims to validate the behavior and functionality of the 
 * methods and attributes associated with target pet in the game. Test cases 
 * encompass various scenarios, from common operations to edge cases and possible 
 * error conditions.
 * </p>
 */
public class TargetPetTest {

  private TargetPetImpl targetPet;

  @Before
  public void setUp() {
    targetPet = new TargetPetImpl("Fortune", 5);
  }

  @Test
  public void testGetName() {
    assertEquals("Fortune", targetPet.getName());
  }

  @Test
  public void testGetLocation() {
    assertEquals(5, targetPet.getLocation());
  }

  @Test
  public void testUpdateTargetPetLocation() {
    targetPet.updateTargetPetLocation(10);
    assertEquals(10, targetPet.getLocation());
    targetPet.updateTargetPetLocation(-1);
    assertEquals(-1, targetPet.getLocation());
  }

}
