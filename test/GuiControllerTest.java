import static org.junit.Assert.assertEquals;

import controller.GuiController;
import java.io.StringWriter;
import org.junit.Before;
import org.junit.Test;
import utils.Action;
import view.WorldGuiView;
import world.WorldBuilder;
import world.WorldImpl;

/**
 * Junit class for GuiController.
 *
 */
public class GuiControllerTest {
  private GuiController controller;
  private Appendable out;

  /**
   * Setup object to test.
   */
  @Before
  public void setUp() {
    out = new StringWriter();
    controller = new GuiController(new WorldImpl(0, 0, null, null, null, null, null), 
                 new WorldBuilder(),
        new MockView(out), 3);
  }

  /**
   * Test when output is null.
   */
  @Test(expected = NullPointerException.class)
  public void testAppendableNull() {
    new GuiController(new WorldImpl(0, 0, null, null, null, null, null), new WorldBuilder(), 
        new WorldGuiView("Game"), 0);
  }

  /**
   * Test when model is null.
   */
  @Test(expected = NullPointerException.class)
  public void testModelNull() {
    new GuiController(null, new WorldBuilder(), new WorldGuiView("Game"), 10);
  }

  /**
   * test when mansion builder is null.
   */
  @Test(expected = NullPointerException.class)
  public void testBuilderNull() {
    new GuiController(new WorldImpl(0, 0, null, null, null, null, null), null, 
         new WorldGuiView("Game"), 10);
  }

  /**
   * test when view is null.
   */
  @Test(expected = NullPointerException.class)
  public void testViewNull() {
    new GuiController(new WorldImpl(0, 0, null, null, null, null, null), 
        new WorldBuilder(), null, 10);
  }

  /**
   * Test play game.
   */
  @Test
  public void testPlayGame() {
    try {
      controller.playGame();
    } catch (NullPointerException | IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    } 
    assertEquals("Listener Added.\n" + "Listener Added.\n" + "Game Started. Waiting for Events.\n",
        out.toString());
  }

  /**
   * Test action pick.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test
  public void testPick() throws 
      NullPointerException, IllegalArgumentException, IllegalAccessException {
    assertEquals("Item Picked Successfully.", controller.execute(Action.PICKUP, "Pen"));
    assertEquals("Listener Added.\n" + "Listener Added.\n" + "Item picked Pen\n", out.toString());
  }

  /**
   * Test action pick null object.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPickNull() throws 
      NullPointerException, IllegalArgumentException, IllegalAccessException {
    controller.execute(Action.PICKUP, "null");
  }

  /**
   * Test action look around.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test
  public void testLookAround() throws 
       NullPointerException, IllegalArgumentException, IllegalAccessException {
    assertEquals("Looked Around.", controller.execute(Action.LOOKAROUND));
    assertEquals("Listener Added.\n" + "Listener Added.\n", out.toString());
  }

  /**
   * Test action move to neighbor room.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test
  public void testMove() throws 
       NullPointerException, IllegalArgumentException, IllegalAccessException {
    assertEquals("Player Moved Successfully.", controller.execute(Action.MOVE, "Library"));
    assertEquals("Listener Added.\n" + "Listener Added.\n" + "Player Moved to Library\n",
        out.toString());
  }

  /**
   * Test action attempt to kill target.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test
  public void testKill() 
         throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    assertEquals("Attempt to kill is Successful.", 
          controller.execute(Action.ATTEMPT_KILL, "knife"));
    assertEquals("Listener Added.\n" + "Listener Added.\n" + "Attempt to kill using knife\n",
        out.toString());
  }


  /**
   * Test display a player's detail.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test
  public void testDisplayPlayerDetails() 
       throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    assertEquals("Player details for Frank", controller.execute(Action.DISPLAY_PLAYER, "Frank"));
    assertEquals("Listener Added.\n" + "Listener Added.\n", out.toString());
  }

  /**
   * Test display a target's detail.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test
  public void testDisplayTargetDetails() 
        throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    assertEquals("Target Details", controller.execute(Action.DISPLAY_TARGET));
    assertEquals("Listener Added.\n" + "Listener Added.\n", out.toString());
  }

  /**
   * Test action add player to the game.
   * 
   * @throws IllegalAccessException illegal access exception.
   * @throws IllegalArgumentException illegal argument exception.
   * @throws NullPointerException  null pointer exception.
   */
  @Test
  public void testAddPlayer() 
       throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    assertEquals("Player Added successfully.",
        controller.execute(Action.ADD_HUMAN_PLAYER, "Frank", "Armory", "12", "true"));
    assertEquals(
        "Listener Added.\n" + "Listener Added.\n"
            + "Player added [name=Frank, location=Armory, capacity=12, isHuman=true]\n",
        out.toString());
  }

  /**
   * Test quit game.
   */
  @Test
  public void testQuit() {
    controller.quitGame();
    assertEquals("Listener Added.\n" + "Listener Added.\n" + "System Terminated.\n",
        out.toString());
  }

  /**
   * Test Restart game.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRestartGame() {
    controller.restartGame();
  }

  /**
   * Test start a new game with new settings.
   */
  @Test
  public void testNewGame() {
    controller.newGame();
    assertEquals("Listener Added.\n" + "Listener Added.\n" + "New Game Created.\n"
        + "Player Added successfully.\n", out.toString());
  }

}

