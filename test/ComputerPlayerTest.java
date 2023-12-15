import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.Point;
import org.junit.Before;
import org.junit.Test;
import player.ComputerPlayer;
import player.Player;
import space.SpaceImpl;
import targetcharacter.TargetCharacter;
import targetcharacter.TargetPet;
import world.WorldImpl;

/**
 * Unit tests for the {@code ComputerPlayer} class.
 * <p>
 * This test class verifies the behavior and functionality of the methods and properties 
 * associated with computer-controlled players in the game environment. Each test case aims to
 * cover various scenarios, including normal behavior, 
 * boundary conditions, and potential edge cases.
 * </p>
 */
public class ComputerPlayerTest {
   
  private static List<SpaceImpl> spaces;
  private ComputerPlayer computerPlayer;
  private StubWorld stubWorld;
  private TargetCharacter target;
  private TargetPet pet;
  private Map<Integer, Set<Integer>> worldMap;

  @Before
  public void setUp() {
    computerPlayer = new ComputerPlayer("TestPlayer", 1, 10);
    stubWorld = new StubWorld(20, 20, "testWorld", spaces, target, pet, worldMap);
  }
    
  @Test
  public void testChooseActionMove() 
       throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    stubWorld.setNeighboringSpaces(List.of("Neighbor1", "Neighbor2"));
    String result = computerPlayer.chooseAction(stubWorld);
    assertTrue(result.startsWith("Moved to "));
  }
    
  @Test
  public void testChooseActionPickUpItem() 
        throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    Map<String, Integer> items = new HashMap<>();
    items.put("Item1", 1);
    items.put("Item2", 2);
    stubWorld.setItems(items);

    String result = computerPlayer.chooseAction(stubWorld);
    assertTrue(result.startsWith("Picked up "));
  }
    
  @Test
  public void testChooseActionLookAround() 
         throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    stubWorld.setLookAroundResponse("Looking around...");

    String result = computerPlayer.chooseAction(stubWorld);
    assertEquals("Looking around...", result);
  }
    
  @Test
  public void testChooseActionNoNeighboringSpaces() 
         throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    stubWorld.setNeighboringSpaces(new ArrayList<>());

    String result = computerPlayer.chooseAction(stubWorld);
    assertEquals("No neighboring spaces to move to.", result);
  }
    
  @Test
  public void testChooseActionNoItems()
         throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    stubWorld.setItems(new HashMap<>());

    String result = computerPlayer.chooseAction(stubWorld);
    assertEquals("No items to pick up.", result);
  }
    
  @Test
  public void testGetCurrentSpaceName() {
    stubWorld.setCurrentSpace(new SpaceImpl("TestSpace", new Point(0, 0), new Point(5, 5)));
        
    String spaceName = computerPlayer.getCurrentSpaceName(stubWorld);
    assertEquals("TestSpace", spaceName);
  }

  // Stub implementation for WorldImpl
  private class StubWorld extends WorldImpl {
    private List<String> neighboringSpaces;
    private Map<String, Integer> items;
    private String lookAroundResponse;
    private SpaceImpl currentSpace;
    
    public StubWorld(int rows, int cols, String worldName, 
        List<SpaceImpl> spaces, TargetCharacter target, TargetPet pet,
           Map<Integer, Set<Integer>> worldMap) {
      super(rows, cols, worldName, spaces, target, pet, worldMap);
    }

    public void setNeighboringSpaces(List<String> spaces) {
      this.neighboringSpaces = spaces;
    }

    public void setItems(Map<String, Integer> items) {
      this.items = items;
    }

    public void setLookAroundResponse(String response) {
      this.lookAroundResponse = response;
    }

    public void setCurrentSpace(SpaceImpl space) {
      this.currentSpace = space;
    }

    @Override
    public List<String> getNeighboringSpaces(String spaceName) {
      return neighboringSpaces;
    }

    @Override
    public Map<String, Integer> getItems() {
      return items;
    }

    @Override
    public String lookAround() {
      return lookAroundResponse;
    }
  }
}
