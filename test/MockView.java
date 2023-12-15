import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import listener.ButtonListener;
import listener.KeyboardListener;
import listener.MouseClickListener;
import view.WorldView;

/**
 * Mock View Implementation.
 *
 */
public class MockView implements WorldView {
  private final Appendable out;

  /**
   * Creates an object for View with fields Appendable.
   * 
   * @param output the target to print to.
   * @throws NullPointerException when output is NULL.
   */
  public MockView(Appendable output) throws NullPointerException {
    Objects.requireNonNull(output);
    this.out = output;
  }

  private void println(String message) throws IllegalStateException {
    try {
      this.out.append(message + "\n");
    } catch (IOException exception) {
      throw new IllegalStateException("Append failed", exception);
    }
  }

  @Override
  public String displayItems(Map<String, Integer> items) {
    println("\nList of Items Available with Damage = " + items.toString());
    return "Pen";
  }

  @Override
  public void displayInfo(String message) {
    println(message);
  }

  @Override
  public void displayErrorMsg(String message) {
    println(message);
  }

  @Override
  public void addPlayerScreen(List<String> roomNames) {
    println("Player Added successfully.");
  }


  @Override
  public void refreshBoard(Map<String, int[]> rooms, Map<String, String> players,
      String petLocation, String targetLocation, String currentPlayer, String targetDetails,
      int numberOfTurns, String message, MouseClickListener listener) {
    throw new UnsupportedOperationException("There is no refresh needed in console view.");
  }

  @Override
  public void welcomeScreen() {
    println("Game Started. Waiting for Events.");
  }

  @Override
  public void quit() {
    println("System Terminated.");
  }

  @Override
  public void addActionListener(ButtonListener buttonListener) {
    println("Listener Added.");
  }

  @Override
  public void addActionListener(KeyboardListener keyboardListener) {
    println("Listener Added.");
  }

  @Override
  public void restartGame(List<String> roomNames) {
    println("Game restarted");
  }


  @Override
  public String[] getPlayerInput() {
    return new String[] {"Input"};
  }

  @Override
  public void setPlayerColor(List<String> roomNames) {
    println("Colors set");
  }

  @Override
  public String[] getInitialInput() {
    return new String[] {"res/myworld.txt", "10", "10"};
  }


  @Override
  public void initialScreen() {
    throw new UnsupportedOperationException(
     "No new world specification can be setup in console view.");
  }

  @Override
  public int confirmation(String message) {
    return 0;
  }

  @Override
  public void endScreen(String result) {
    println("Game is Over");
  }



}