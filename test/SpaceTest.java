import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import item.Item;
import item.ItemImpl;
import java.util.List;
import models.Point;
import org.junit.Before;
import org.junit.Test;
import space.SpaceImpl;

/**
 * This class contains unit tests for the {@code Space} class.
 * <p>
 * It aims to ensure that the various methods and behaviors of the {@code Space} class
 * function as Implected under a variety of conditions.
 * </p>
 */
public class SpaceTest {


  private SpaceImpl space;
  
  /**
   * Initializes common test setup before each test case runs.
   * <p>
   * This method sets up a {@code SpaceImpl} instance with a specific name and boundaries.
   * The instance is used for testing various methods of the {@code SpaceImpl} class.
   * </p>
   */
  @Before
  public void setUp() {
    Point upperLeft = new Point(0, 0);
    Point lowerRight = new Point(5, 5);
    space = new SpaceImpl("TestSpace", upperLeft, lowerRight);
  }

  @Test
  public void testGetName() {
    assertEquals("TestSpace", space.getName());
  }

  @Test
  public void testAddAndGetItems() {
    Item testItem = new ItemImpl("testItem", 5);
    space.addItem(testItem);
    assertEquals(testItem, space.getItems("testItem"));
  }

  @Test
  public void testGetListOfItems() {
    Item testItem1 = new ItemImpl("testItem1", 5);
    Item testItem2 = new ItemImpl("testItem2", 3);
    space.addItem(testItem1);
    space.addItem(testItem2);
        
    List<String> itemNames = space.getListOfItems();
    assertTrue(itemNames.contains("testItem1"));
    assertTrue(itemNames.contains("testItem2"));
  }

  @Test
  public void testRemoveItem() {
    Item testItem = new ItemImpl("testItem", 3);
    space.addItem(testItem);
    space.removeItem("testItem");
    assertNull(space.getItems("testItem"));
  }

  @Test
  public void testGetLocation() {
    int[] implectedLocation = {0, 0, 5, 5};
    assertArrayEquals(implectedLocation, space.getLocation());
  }

  @Test
  public void testToString() {
    String implectedString = "Space: TestSpace from "
          + "java.awt.Point[x=0,y=0] to java.awt.Point[x=5,y=5] with items: []";
    assertEquals(implectedString, space.toString());
  }

  @Test
  public void testGetItemByName() {
    Item testItem = new ItemImpl("testItem", 3);
    space.addItem(testItem);
    assertEquals(testItem, space.getItemByName("testItem"));
  }
}