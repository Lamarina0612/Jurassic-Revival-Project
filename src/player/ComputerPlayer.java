package player;

import java.util.List;
import java.util.Map;
import java.util.Random;
import space.SpaceImpl;
import utils.Action;
import world.World;
import world.WorldImpl;

/**
 * Represents a computer-controlled player in the game.
 *
 * <p>This class models the behavior and attributes specific to a computer-controlled player,
 * distinguishing it from human players.
 * It inherits core player functionalities
 * from the {@link PlayerImpl} class but contain additional methods or overrides to simulate
 * computer decision-making or behavior.</p>
 *
 * @see PlayerImpl
 * 
 */
public class ComputerPlayer extends PlayerImpl {
  private static Random rand;
  
  /**
   *  Constructor for Computer Player.
   *
   */
  public ComputerPlayer(String name, int location, int capacity) {
    super(name, location, capacity, false); 
    this.rand = new Random();
  }
  
  /**
   * Chooses an action for the computer player based on the current state of the world.
   *
   * <p>This method determines the next move or action for the computer player based on its
   * internal logic or algorithm and the provided world state. It can analyze the {@link WorldImpl}
   * instance to make informed decisions.</p>
   *
   * @param world The current state of the game world, used to decide the next action.
   * @return A string representation of the chosen action, which may correspond to an action
   *         enum value or any other specific command in the game.
   * @throws IllegalAccessException if the access does not exist.
   * @throws IllegalArgumentException if the argument is not legal.
   * @throws NullPointerException 
   * 
   * @see WorldImpl
   * 
   */
  public String chooseAction(WorldImpl world) 
        throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    int action = rand.nextInt(4); // 3 actions: move, pickup item, look around
    String actionResult = "No action chosen.";
    switch (action) {
      case 0: // Move
        List<String> neighboringSpaces = world.getNeighboringSpaces(getCurrentSpaceName(world));
        if (neighboringSpaces.size() > 0) {
          String moveTo = neighboringSpaces.get(rand.nextInt(neighboringSpaces.size()));
          world.movePlayer(moveTo);
          actionResult = "Computer Player moved to " + moveTo;
        } else {
          actionResult = "No neighboring spaces to move to.";
        }
        break;
              
      case 1: // Pickup Item
        Map<String, Integer> items = world.getItems();
        if (items.size() > 0) {
          String randomItem = (String) items.keySet().toArray()[rand.nextInt(items.size())];
          world.pickItem(randomItem);
          actionResult = "Picked up " + randomItem;
        } else {
          actionResult = "No items to pick up.";
        }
        break;

      case 2: // Look Around
        actionResult = world.lookAround();
        break;
      
      case 3: // Attempt kill
        actionResult = world.attemptKill(this.getName());
        break;
        
      default:
        return actionResult;
    }
    
    return actionResult;  
    
  }

  /**
   * Method for the computer player to choose action for GUI implementation.
   * 
   * @return the action chosen by computer player.
   */
  public static Action chooseActionGui() {
    int action = rand.nextInt(4); 
    switch (action) {
      case 0: 
        return Action.MOVE;
      case 1: // Pickup Item
        return Action.PICKUP;

      case 2: // Look Around
        return Action.LOOKAROUND;

      case 3: // Attempt kill
        return Action.ATTEMPT_KILL;
      default:
        return null;
    }

  }
  
  /**
   * Retrieves the name of the current space where the computer player is positioned
   * in the given game world.
   *
   * @param world The current state of the game world from which the space's name is derived.
   * @return A string representing the name of the current space or location in the game world.
   * 
   * @see WorldImpl
   * 
   */
  public String getCurrentSpaceName(WorldImpl world) {
    int location = this.getLocation();
    SpaceImpl currentSpace = world.getSpaceByIndex(location);
    return currentSpace.getName();
  }
}
