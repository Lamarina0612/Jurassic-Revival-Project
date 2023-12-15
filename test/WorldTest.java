import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import item.ItemImpl;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import models.Point;
import org.junit.Before;
import org.junit.Test;
import player.Player;
import space.SpaceImpl;
import targetcharacter.TargetCharacter;
import targetcharacter.TargetCharacterImpl;
import targetcharacter.TargetPet;
import targetcharacter.TargetPetImpl;
import world.WorldImpl;

/**
 * Unit tests for the {@code World} class.
 * <p>
 * This test class is dedicated to verifying the behavior and functionalities 
 * of the World class in the game. Test cases are designed to cover a range 
 * of scenarios, from typical operations to edge conditions, ensuring the 
 * robustness and correctness of the World class.
 * </p>
 */
public class WorldTest {

  private WorldImpl world;
  
  /**
   * Initializes and sets up necessary objects before running each test.
   * <p>
   * This method is executed before each test case, ensuring a fresh environment
   * with mock data for consistent testing. It constructs a mock world environment 
   * with predefined dimensions, a set of mock spaces, a test target character, 
   * and a mock world map to simulate different in-game scenarios.
   * </p>
   *
   * @throws Exception if any initialization or setup process fails.
   */
  @Before
  public void setUp() {
    List<SpaceImpl> mockSpaces = new ArrayList<>();
    Map<Integer, Set<Integer>> mockWorldMap = new HashMap<>();
    TargetCharacter target = new TargetCharacterImpl("TestCharacter", 100, 5);
    TargetPet pet = new TargetPetImpl("Fortune the dog", 1);
    world = new WorldImpl(20, 20, "TestWorld", mockSpaces, target, pet, mockWorldMap);
  }

  @Test
  public void testIsHuman() {
    WorldImpl.addPlayer("HumanPlayer", "SomeSpace", 10, true);
    assertTrue(world.isHuman());
  }

  @Test
  public void testGetSpaceInfo() {
    WorldImpl.addPlayer("HumanPlayer", "SomeSpace", 10, true);
    assertNotNull(world.getSpaceInfo());
  }

  @Test
  public void testGetWorldName() {
    assertEquals("TestWorld", world.getWorldName());
  }

  @Test
  public void testGetNeighboringSpaces() {
    List<String> neighbors = world.getNeighboringSpaces("SomeSpace");
    assertNotNull(neighbors);
  }

  @Test
  public void testLookAround() {
    WorldImpl.addPlayer("HumanPlayer", "SomeSpace", 10, true);
    String lookResult = world.lookAround();
    assertNotNull(lookResult);
  }


  @Test
  public void testAddPlayer() {
    WorldImpl.addPlayer("NewPlayer", "SomeSpace", 10, true);
    Player currentPlayer = world.getCurrentPlayer();
    assertEquals("NewPlayer", currentPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerToInvalidSpace() {
    WorldImpl.addPlayer("NewPlayer", "InvalidSpace", 10, true);
  }

  @Test
  public void testGetDetailsOfPlayer() {
    WorldImpl.addPlayer("NewPlayer", "SomeSpace", 10, true);
    String details = world.getDetailsOfPlayer("NewPlayer");
    assertNotNull(details);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetDetailsOfInvalidPlayer() {
    world.getDetailsOfPlayer("InvalidPlayer");
  }

  @Test
  public void testGenerateImage() {
    BufferedImage image = world.generateImage();
    assertNotNull(image);
  }

  @Test
  public void testGetItems() {
    Map<String, Integer> items = world.getItems();
    assertNotNull(items);
  }
  
  @Test
  public void testSuccessfulUnseenAttack() {
    // Setup
    WorldImpl.addPlayer("Attacker", "IsolatedSpace", 10, true);
    Player attacker = world.findPlayerByName("Attacker");
    ItemImpl powerfulItem = new ItemImpl("PowerfulItem", 20);
    attacker.addItem(powerfulItem);
    int initialHealth = world.getTarget().getHealth();

    // Perform attack
    String result = world.attemptKill("Attacker");
    assertEquals(initialHealth - 20, world.getTarget().getHealth());
    assertFalse(attacker.getItems().contains(powerfulItem));
    assertTrue(result.contains("successfully killed the target using PowerfulItem"));
  }
  
  @Test
  public void testFailedAttackDueToBeingSeen() {
    // Setup
    WorldImpl.addPlayer("Attacker", "VisibleSpace", 10, true);
    WorldImpl.addPlayer("Observer", "NeighboringSpace", 10, true);
    ItemImpl visibleItem = new ItemImpl("VisibleItem", 15);
    world.findPlayerByName("Attacker").addItem(visibleItem);

    // Perform attack
    String result = world.attemptKill("Attacker");

    // Assertions
    assertTrue(world.getTarget().isAlive());
    assertTrue(world.findPlayerByName("Attacker").getItems().contains(visibleItem));
    assertTrue(result.contains("was seen by Observer. No damage done."));
  }
  
  @Test
  public void testAttackWithNoItems() {
    WorldImpl.addPlayer("UnarmedAttacker", "IsolatedSpace", 10, true);
    int initialHealth = world.getTarget().getHealth();

    // Perform attack
    String result = world.attemptKill("UnarmedAttacker");
    assertEquals(initialHealth - 1, world.getTarget().getHealth());
    assertTrue(result.contains("poked in the eye"));
  }
  
  @Test
  public void testAttackOnDeadTarget() {
    // Setup
    WorldImpl.addPlayer("LateAttacker", "IsolatedSpace", 10, true);
    world.getTarget().reduceHealth(100); 
    ItemImpl item = new ItemImpl("LateAttackItem", 10);
    world.findPlayerByName("LateAttacker").addItem(item);

    // Perform attack
    String result = world.attemptKill("LateAttacker");
    assertEquals(0, world.getTarget().getHealth());
    assertTrue(result.contains("target is already dead") || result.contains("no effect"));
  }
  
  @Test
  public void testTargetAndPetMovementOnTurn() {
    // Setup initial locations
    int initialTargetLocation = world.getTarget().getLocation();
    int initialPetLocation = world.getPet().getLocation();

    // Perform one turn
    world.endOfTurn();

    // Verify movement
    int newTargetLocation = world.getTarget().getLocation();
    int newPetLocation = world.getPet().getLocation();

    assertNotEquals(initialTargetLocation, newTargetLocation);
    assertNotEquals(initialPetLocation, newPetLocation);
  }
  
  @Test
  public void testPetMakesSpaceInvisible() {
    WorldImpl.addPlayer("Player1", "SpaceNearPet", 10, true);
    int petLocation = world.getPet().getLocation();
    SpaceImpl petSpace = WorldImpl.getSpaceByIndex(petLocation);
    SpaceImpl neighboringSpace = new SpaceImpl("SpaceNearPet", new Point(0, 0), new Point(0, 0));
    world.getSpaces().add(neighboringSpace);
    world.getWorldMap().put(petLocation, 
          new HashSet<>(Arrays.asList(world.findSpaceIndexByName("SpaceNearPet"))));

    // Check visibility
    String lookAroundResult = world.lookAround();
    assertFalse(lookAroundResult.contains("SpaceNearPet"));
  }
  
  @Test
  public void testTurnsUpdateAndNextPlayerSet() {
    // Setup
    WorldImpl.addPlayer("Player1", "SomeSpace", 10, true);
    WorldImpl.addPlayer("Player2", "SomeSpace", 10, true);

    Player initialPlayerIndex = world.getCurrentPlayer();
    world.endOfTurn();
    Player nextPlayerIndex = world.getCurrentPlayer();

    assertNotEquals(initialPlayerIndex, nextPlayerIndex);
    assertEquals("Player2", world.getCurrentPlayer().toString());
  }

  @Test
  public void testSuccessfulKillEndsGame() {
    // Setup
    WorldImpl.addPlayer("Player1", "SpaceWithTarget", 10, true);
    Player player = world.getCurrentPlayer();
    player.addItem(new ItemImpl("PowerfulWeapon", 100)); 
    world.getTarget().updateLocation(player.getLocation());
    String result = world.attemptKill(player.getName());

    assertTrue(result.contains("wins"));
    assertFalse(world.getTarget().isAlive());
  }

  @Test
  public void testGameEndsWhenMaxTurnsReached() {
    // Setup
    WorldImpl.addPlayer("Player1", "SomeSpace", 10, true);
    int maxTurns = 5;
    for (int i = 0; i < maxTurns; i++) {
      world.endOfTurn();
    }

    assertEquals(maxTurns, 0);
  }



}
