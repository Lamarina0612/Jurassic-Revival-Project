package controller;

import utils.Action;

/**
 * Represents the main control interface for game operations.
 * 
 * <p>The {@code GameController} interface defines the essential methods that any game controller 
 * implementation should provide. It acts as a contract for controllers to manage and interact 
 * with the game world, handle player commands, and oversee the flow of the game.</p>
 * 
 * @see AbstractGameController
 */
public interface GameController {
  /**
   * Initiates and manages the game play.
   * This method is responsible for initiating the game loop, processing player actions,
   * and managing the flow of the game. It handles the turns of each player, ensures the
   * game rules are followed, and monitors the condition for the game's conclusion. 
   * Exceptions are thrown to handle any illegal actions or unexpected game states.
   *
   * @throws NullPointerException if an operation or method is attempted on a null object reference.
   * @throws IllegalArgumentException if an argument passed to a method is inappropriate.
   * @throws IllegalAccessException if an attempt is made to access a field 
   *         in a way that is not allowed.
   */
  void playGame() throws NullPointerException, IllegalArgumentException, IllegalAccessException;
  
  /**
   * Executes a specified action in the game.
   * 
   * @param action The action to be executed within the game, as defined by the Action enum.
   * @param varargs Optional arguments needed for certain actions, varying based on the action type.
   * @return A string representing the outcome or result of the executed action.
   * @throws NullPointerException if an operation or method is attempted on a null object reference.
   * @throws IllegalArgumentException if an argument passed to a method is inappropriate.
   * @throws IllegalAccessException if an attempt is made to access a 
   *         field in a way that is not allowed.
   */
  String execute(Action action, String... varargs) 
         throws NullPointerException, IllegalArgumentException, IllegalAccessException;
  
}
