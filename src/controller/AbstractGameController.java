package controller;

import java.util.Map;
import java.util.function.Function;
import utils.Action;
import world.World;
import world.WorldBuilder;
import world.WorldImpl;

/**
 * Represents an abstract implementation of the GameController interface.
 * This class provides a foundation for game controllers, encapsulating the 
 * common functionality and behavior shared by all game controllers. Subclasses 
 * should provide specific implementations for game-specific behavior.
 * 
 * <p>Implementing classes are expected to provide concrete implementations 
 * for the methods defined in the {@code GameController} interface.</p>
 *
 * @see GameController
 * @author Marina Lin
 * @version Milestone 2
 */
public abstract class AbstractGameController implements GameController {

  protected WorldImpl world;
  protected Map<Action, Function<String[], String>> operations;
  

  @Override
  public abstract String execute(Action action, String... args) 
      throws NullPointerException, IllegalArgumentException, IllegalAccessException;
}
