package driver;

import controller.ConsoleController;
import controller.GuiController;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import utils.Instructions;
import view.WorldGuiView;
import world.WorldBuilder;
import world.WorldImpl;

/**
 * The main driver class responsible for initializing and starting the game.
 * 
 * <p>This class contains the {@code main} method and acts as the primary entry point 
 * for the game application. The {@code Driver} initializes necessary game components, 
 * sets up the game environment, and triggers the game loop or main flow.</p>
 * 
 * @author Marina Lin
 */
public class Driver {

  /**
   <p>This method initializes and sets up the game environment, triggers necessary game components,
  * and starts the game loop or main flow. It processes command-line arguments (if any) provided 
  * when starting the game.</p>
  * 
  * @param args An array of command-line arguments provided during the execution of the game.
  * @throws IllegalAccessException  if the access does not exist.
  */ 
  public static void main(String[] args) throws IllegalAccessException {
    // Check for sufficient arguments
    if (args.length != 3) {
      System.out.println("Please provide the path to "
          + "the configuration file and the number of turns.");
      return;
    }

    // Extract arguments
    String filePath = args[0];
    int maxTurns = Integer.parseInt(args[1]);
    String gameMode = args[2];
    WorldImpl world = null;

    try {
      if (maxTurns <= 0) {
        throw new NumberFormatException("Number of turns must be positive.");
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid max turns value: " + e.getMessage());
      return;
    }

    // Process the file and create the world
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      WorldBuilder wb = new WorldBuilder();
      world = wb.buildWorldFromConfig(br);
      System.out.println("World read successfully!");
      System.out.println("World name: " + world.getWorldName());
      System.out.println("Target name: " + world.getTarget().getName()); 
      System.out.println("Target's Pet name: " + world.getPet().getName());

      if (Instructions.GUI.equalsIgnoreCase(gameMode)) {
        new GuiController(world, wb, new WorldGuiView("Jurassic World"), maxTurns).playGame();
      } else if (Instructions.TEXT.equalsIgnoreCase(gameMode)) {
        ConsoleController controller = 
            new ConsoleController(new InputStreamReader(System.in), System.out, world, maxTurns);
        controller.playGame();
      }
      
      

    } catch (IOException e) {
      System.out.println("Error reading the file: " + e.getMessage());
    } catch (IllegalArgumentException | NullPointerException e) {
      System.out.println("Error processing the configuration file: " + e.getMessage());
    }
  }
}
