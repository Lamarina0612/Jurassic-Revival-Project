import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import item.Item;
import item.ItemImpl;
import org.junit.Before;
import org.junit.Test;
import player.PlayerImpl;


/**
* Unit tests for the {@code Player} class.
* <p>
* This test class aims to verify the behavior and functionality of the 
* methods and attributes associated with players in the game. Test cases encompass 
* various scenarios, from common operations to edge cases and possible error conditions.
* </p>
*/
public class PlayerTest {

  private PlayerImpl player;

  @Before
  public void setUp() {
    player = new PlayerImpl("TestPlayer", 1, 2, true);
  }

  @Test
  public void testGetName() {
    assertEquals("TestPlayer", player.getName());
  }

  @Test
  public void testGetHealth() {
    assertEquals(0, player.getHealth());
  }

  @Test
  public void testGetLocation() {
    assertEquals(1, player.getLocation());
  }

  @Test
  public void testIsHuman() {
    assertTrue(player.isHuman());
  }

  @Test
  public void testUpdateLocation() {
    player.updateLocation(2);
    assertEquals(2, player.getLocation());
  }

  @Test
  public void testToString() {
    String implectedString = "PlayerImpl[name=TestPlayer, health=0, location=1, isHuman=true]";
    assertEquals(implectedString, player.toString());
  }

  @Test
  public void testAddItemWithinCapacity() {
    Item testItem = new ItemImpl("TestItem", 10);
    player.addItem(testItem);
    // Check if the item was added
    String implectedString = "PlayerImpl[name=TestPlayer, "
           + "health=0, location=1, isHuman=true, items=[TestItem]]"; 
    assertEquals(implectedString, player.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testAddItemExceedingCapacity() {
    Item testItem1 = new ItemImpl("TestItem1", 10);
    Item testItem2 = new ItemImpl("TestItem2", 20);
    Item testItem3 = new ItemImpl("TestItem3", 30);
    player.addItem(testItem1);
    player.addItem(testItem2);
    player.addItem(testItem3); // This should throw an exception
  }
}
