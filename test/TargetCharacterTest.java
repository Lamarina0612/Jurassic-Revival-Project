import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import targetcharacter.TargetCharacterImpl;

/**
 * Unit tests for the {@code TargetCharacter} class.
 * <p>
 * This test class aims to validate the behavior and functionality of the 
 * methods and attributes associated with target characters in the game. Test cases 
 * encompass various scenarios, from common operations to edge cases and possible 
 * error conditions.
 * </p>
 */
public class TargetCharacterTest {

  private TargetCharacterImpl character;

  @Before
  public void setUp() {
    character = new TargetCharacterImpl("TestCharacter", 100, 5);
  }

  @Test
  public void testGetName() {
    assertEquals("TestCharacter", character.getName());
  }
  
  @Test
  public void testSetName() {
    character.setName("UpdatedCharacter");
    assertEquals("UpdatedCharacter", character.getName());
  }

  @Test
  public void testGetHealth() {
    assertEquals(100, character.getHealth());
  }
  
  @Test
  public void testGetLocation() {
    assertEquals(5, character.getLocation());
  }

  @Test
  public void testUpdateLocation() {
    character.updateLocation(7);
    assertEquals(7, character.getLocation());
  }

  @Test
  public void testIsAlive() {
    assertTrue(character.isAlive());
  }

  @Test
  public void testIsAliveWhenHealthIsZero() {
    TargetCharacterImpl deadCharacter = new TargetCharacterImpl("DeadChar", 0, 5);
    assertFalse(deadCharacter.isAlive());
  }

  @Test
  public void testToString() {
    String implectedString = "TargetCharacterImpl[name=TestCharacter, health=100, location=5]";
    assertEquals(implectedString, character.toString());
  }
}
