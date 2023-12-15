package utils;

/**
* Enumerates the possible actions that can be performed within the game.
* 
* <p>Each action corresponds to a specific operation or command that
* can be invoked by players or the game system. These actions help in 
* managing the game flow and determining the interaction between the player 
* and the game world.</p>
* 
* <ul>
*     <li>{@code MOVE}: Represents a command to move a player to a different location.</li>
*     <li>{@code PICKUP}: Represents a command for a player to pick up an item.</li>
*     <li>{@code LOOKAROUND}: Represents a command for a player to look around 
*     their current location.</li>
*     <li>{@code DISPLAY_SPACE}: Represents a command to display 
*     details of a specific space or location.</li>
*     <li>{@code GENERATE_IMAGE}: Represents a command to generate an image of the game world.</li>
*     <li>{@code ADD_HUMAN_PLAYER}: Represents a command to add a human player to the game.</li>
*     <li>{@code ADD_COMPUTER_PLAYER}: Represents a command to add 
*     a computer-controlled player to the game.</li>
*     <li>{@code DISPLAY_PLAYER}: Represents a command to display details of a specific player.</li>
* </ul>
*
* @author Marina Lin
* @version Milestone 2
*/
public enum Action {
  MOVE,
  PICKUP,
  LOOKAROUND,
  DISPLAY_SPACE,
  GENERATE_IMAGE,
  ADD_HUMAN_PLAYER,
  ADD_COMPUTER_PLAYER,
  DISPLAY_PLAYER,
  ATTEMPT_KILL, 
  MOVEPET, 
  DISPLAY_TARGET;
}
