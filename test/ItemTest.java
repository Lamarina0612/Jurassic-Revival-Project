import static org.junit.Assert.assertEquals;

import item.ItemImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@code Item} class.
 * <p>
 * This test class focuses on ensuring the correct behavior and functionality of the 
 * methods and attributes associated with items in the game. Each test case is designed to
 * validate various scenarios, from regular operations to edge cases and potential error conditions.
 * </p>
 * 
 */
public class ItemTest {

  private ItemImpl item;

  @Before
  public void setUp() {
    item = new ItemImpl("TestItem", 10);
  }

  @Test
  public void testGetName() {
    assertEquals("TestItem", item.getName());
  }

  @Test
  public void testSetName() {
    item.setItemName("NewTestItem");
    assertEquals("NewTestItem", item.getName());
  }

  @Test
  public void testUseItem() {
    assertEquals(10, item.useItem());
  }

  @Test
  public void testSetDamage() {
    item.setDamage(20);
    assertEquals(20, item.useItem());
  }

  @Test
  public void testToString() {
    String implectedString = "TestItem : 10";
    assertEquals(implectedString, item.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeDamage() {
    item.setDamage(-5);
  }
}
