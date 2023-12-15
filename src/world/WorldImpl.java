package world;

import item.Item;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import javax.imageio.ImageIO;
import player.ComputerPlayer;
import player.Player;
import player.PlayerImpl;
import space.Space;
import space.SpaceImpl;
import targetcharacter.TargetCharacter;
import targetcharacter.TargetPet;

/**
 * Represents the game world, including its spaces, items, players, and various game logic.
 * This class implements the {@code World} interface, encapsulating all essential details 
 * and behaviors associated with the game world.
 *
 * <p>{@code WorldImpl} serves as a concrete implementation of the game world, ensuring that all
 * gameplay interactions and mechanics are effectively coordinated. The class manages the state
 * of the world, player actions, and any other essential game mechanics.</p>
 *

 */
public class WorldImpl implements World {
  private static List<SpaceImpl> spaces;
  private static List<Player> players;
  
  private int rows;
  private int cols;
  private String worldName;
  private TargetCharacter target;
  private TargetPet pet;
  private int numberOfSpaces;
  private int currentPlayer;
  private Stack<Integer> petPath = new Stack<>();
  private Set<Integer> visitedSpaces = new HashSet<>();
  private Map<Integer, Set<Integer>> worldMap;
  
  /**
   * Constructs a new instance of {@code WorldImpl} with the specified parameters.
   * This represents the game world grid, complete with spaces and other world-specific details.
   *
   * @param rows      the number of rows in the world grid.
   * @param cols      the number of columns in the world grid.
   * @param worldName the name of the game world.
   * @param spaces    a list of spaces ({@code SpaceImpl}) that are present within this game world.
   * @param target    the target character of this game
   * @param worldMap  the world map of this game
   */
  public WorldImpl(int rows, int cols, String worldName, List<SpaceImpl> spaces, 
         TargetCharacter target, TargetPet pet, Map<Integer, Set<Integer>> worldMap) {
    this.rows = rows;
    this.cols = cols;
    this.worldName = worldName;
    this.target = target;
    this.pet = pet;
    this.spaces = spaces;
    this.numberOfSpaces = spaces.size();
    this.players = new ArrayList<>();
    this.currentPlayer = 0;
    this.worldMap = worldMap; 
  }
  
  /**
   * Get the target character. 
   * 
   * @return The target character.
   */
  public TargetCharacter getTarget() {
    return this.target;
  }
  
  /**
   * Get the target pet. 
   * 
   * @return The target pet.
   */
  public TargetPet getPet() {
    return this.pet;
  }
  
  /**
   * Get the world's map.
   * 
   * @return The world map of this game.
   */
  public Map<Integer, Set<Integer>> getWorldMap() {
    return this.worldMap;
  }
  
  /**
   * Get the spaces of this game.
   * 
   * @return The spaces of this game.
   */
  public List<SpaceImpl> getSpaces() {
    return this.spaces;
  }
  
  @Override
  public List<String> getSpacesNames() {
    List<String> spaceNames = new ArrayList<>();
    for (SpaceImpl space : spaces) {
      spaceNames.add(space.getName());
    }
    return spaceNames;
  }
  
  @Override
  public List<String> getPlayersNames() {
    List<String> playerNames = new ArrayList<>();
    for (Player player : players) {
      playerNames.add(player.getName());
    }
    return playerNames;
  }

  
  @Override
  public boolean isHuman() {
    return players.get(currentPlayer).isHuman();
  }
  
  /**
   * Get the number of players in the game.
   * 
   * @return The number of players in the game.
   */
  public static int getNumberOfPlayers() {
    return players.size();
  }
  
  @Override
  public String getSpaceInfo() {
    return players.get(currentPlayer).toString();
  }
  
  @Override
  public String getWorldName() {
    return worldName;
  }
  
  private SpaceImpl findSpaceByName(String spaceName) {
    for (SpaceImpl space : spaces) {
      if (space.getName().equals(spaceName)) {
        return space;
      }
    }
    return null;
  }
  
  private SpaceImpl getSpaceAt(int row, int col) {
    for (SpaceImpl space : spaces) {
      int[] location = space.getLocation();
      int topLeftX = location[0];
      int topLeftY = location[1];
      int bottomRightX = location[2];
      int bottomRightY = location[3];
      
      if (row >= topLeftY && row <= bottomRightY && col >= topLeftX && col <= bottomRightX) {
        return space;
      }
    }
    return null;
  }
  
  @Override
  public List<String> getNeighboringSpaces(String spaceName) {
    List<String> neighbors = new ArrayList<>();
    
    // Offsets for the neighboring spaces (up, down, left, right)
    int[][] offsets = {
        {-1, 0},  // above
        {1, 0},   // below
        {0, -1},  // left
        {0, 1}    // right
    };
    
    SpaceImpl targetSpace = findSpaceByName(spaceName);
    if (targetSpace == null) {
      return neighbors;
    }
    
    int[] targetLocation = targetSpace.getLocation();
    int targetTopLeftX = targetLocation[0];
    int targetTopLeftY = targetLocation[1];
    int targetBottomRightX = targetLocation[2];
    int targetBottomRightY = targetLocation[3];
    
    for (int row = targetTopLeftY; row <= targetBottomRightY; row++) {
      for (int col = targetTopLeftX; col <= targetBottomRightX; col++) {
        for (int[] offset : offsets) {
          int newRow = row + offset[0];
          int newCol = col + offset[1];
          
          SpaceImpl neighborSpace = getSpaceAt(newRow, newCol);
          if (neighborSpace != null && !targetSpace.equals(neighborSpace)) {
            String neighborName = neighborSpace.getName();
            if (!neighbors.contains(neighborName)) {
              neighbors.add(neighborName);
            }
          }
        }
      }
    }
    return neighbors;
  }
  
  @Override
  public void moveTarget() {
    List<String> neighboringSpaces = 
          getNeighboringSpaces(getSpaceByIndex(target.getLocation()).getName());
    if (!neighboringSpaces.isEmpty()) {
      Random rand = new Random();
      String moveTo = neighboringSpaces.get(rand.nextInt(neighboringSpaces.size()));
      int newLocationIndex = findSpaceIndexByName(moveTo);
      target.updateLocation(newLocationIndex);
    }
  }
  
  @Override
  public String lookAround() {
    Player current = players.get(currentPlayer);
    int playerSpaceIndex = current.getLocation();  
    if (playerSpaceIndex >= 0 && playerSpaceIndex < spaces.size()) {
      SpaceImpl currentSpace = spaces.get(playerSpaceIndex);
      List<String> visibleSpaces = new ArrayList<>();
      StringBuilder info = new StringBuilder();
      
      // Players in the same space
      List<String> playersInSpace = new ArrayList<>();
      for (Player player : players) {
        if (player.getLocation() == playerSpaceIndex && !player.equals(current)) {
          playersInSpace.add(player.getName());
        }
      }
      info.append("You are at ").append(currentSpace.getName())
          .append(". Items here: ").append(String.join(", ", currentSpace.getListOfItems()));
      
      
      if (!playersInSpace.isEmpty()) {
        info.append(". Players here: ").append(String.join(", ", playersInSpace));
      }
      
      // information about neighboring spaces
      for (String spaceName : getNeighboringSpaces(currentSpace.getName())) {
        int neighborIndex = findSpaceIndexByName(spaceName);
        if (!isPetInSpace(neighborIndex)) {
          SpaceImpl neighborSpace = spaces.get(neighborIndex);
          List<String> playersInNeighbor = new ArrayList<>();
          for (Player player : players) {
            if (player.getLocation() == neighborIndex) {
              playersInNeighbor.add(player.getName());
            }
          }
          visibleSpaces.add(spaceName + " (Items: " + String.join(", ", 
                        neighborSpace.getListOfItems()) 
                        + ", Players: " + String.join(", ", playersInNeighbor) + ")");
        }
      }
      
      info.append(". Visible spaces: ").append(String.join(",", visibleSpaces));
      endOfTurn(); // Update the turn after looking around
      return info.toString();
    }
    endOfTurn();
    return "You are in an unknown location.";
  }
  
  /**
   * Check if the pet is in a specific space.
   * 
   * @param spaceIndex The index of the space
   * @return boolean value if the pet location equals the input index.
   */
  public boolean isPetInSpace(int spaceIndex) {
    return pet.getLocation() == spaceIndex;
  }
  
  /**
   * Finds the index of a space by its name in the game world.
   * This method iterates over the list of spaces in the game world, comparing
   * the name of each space with the provided space name. If a matching name is found,
   * the index of that space in the list is returned.
   *
   * @param spaceName The name of the space to find.
   * @return The index of the space if found; returns -1 if no space with the specified
   *         name exists in the game world.
   */
  public int findSpaceIndexByName(String spaceName) {
    for (int i = 0; i < spaces.size(); i++) {
      if (spaces.get(i).getName().equals(spaceName)) {
        return i;
      }
    }
    return -1; // Indicates not found
  }
  
  private boolean isNeighborSpace(int spaceIndexA, int spaceIndexB) {
    SpaceImpl spaceA = spaces.get(spaceIndexA);
    List<String> neighborSpaces = getNeighboringSpaces(spaceA.getName());
    SpaceImpl spaceB = spaces.get(spaceIndexB);
    return neighborSpaces.contains(spaceB.getName());  
  }
  
  /**
   * Searches for a player in the game world by their name.
   * This method iterates through the list of players in the game world,
   * comparing each player's name with the provided name argument. If a player
   * with the matching name is found, that player object is returned.
   *
   * @param playerName The name of the player to be searched for.
   * @return The Player object corresponding to the given name if found; 
   *         returns null if no player with the specified name exists in the game world.
   */
  public Player findPlayerByName(String playerName) {
    for (Player player : players) {
      if (player.getName().equals(playerName)) {
        return player;
      }
    }
    return null;
  }
  
  /**
   * Determines if one player can see another player based on their locations.
   * This method checks if two players, identified by their names, are in a position
   * where they can see each other. A player can see another player if they are either 
   * in the same space or in neighboring spaces. The method uses the players' locations 
   * to determine this visibility.
   *
   * @param playerNameA The name of the first player.
   * @param playerNameB The name of the second player.
   * @return True if player A can see player B, otherwise returns false. 
   *         Also returns false if either player does not exist.
   */
  public boolean canSeePlayer(String playerNameA, String playerNameB) {
    Player playerA = findPlayerByName(playerNameA);
    Player playerB = findPlayerByName(playerNameB);
    
    if (playerA == null || playerB == null) {
      return false;
    }
    
    int playerAspaceIndex = playerA.getLocation();
    int playerBspaceIndex = playerB.getLocation();
    
    if (playerAspaceIndex == playerBspaceIndex) {
      return true;
    }
    
    return isNeighborSpace(playerAspaceIndex, playerBspaceIndex);
    
  }
  
  @Override
  public void pickItem(String itemName) {
    Player current = players.get(currentPlayer);
    int playerSpaceIndex = current.getLocation();
    SpaceImpl currentSpace = spaces.get(playerSpaceIndex);

    if (currentSpace.getListOfItems().contains(itemName)) {
      current.addItem(currentSpace.getItems(itemName)); 
      currentSpace.removeItem(itemName);              
      endOfTurn();                               
      return;
    }
    
    endOfTurn();
  }
  
  @Override
  public void movePlayer(String spaceName) 
       throws NullPointerException, IllegalArgumentException, IllegalAccessException {
    if (players.size() == 0) {
      throw new IllegalAccessException("No Players in Game.");
    }
      
    Player player = players.get(currentPlayer);
    SpaceImpl currentSpace = spaces.get(player.getLocation());

    // If the desired space is neighboring, move the player there
    if (getNeighboringSpaces(currentSpace.getName()).contains(spaceName)) {
      for (int i = 0; i < spaces.size(); i++) {
        if (spaces.get(i).getName().equals(spaceName)) {
          player.updateLocation(i); 
          endOfTurn();
          return;
        }
      }
    } else {
      throw new IllegalArgumentException("Cannot move to " + spaceName);
    }
    
    endOfTurn();
  }

  private void movePet() {
    if (petPath.isEmpty()) {
      petPath.push(pet.getLocation());
    }


    while (!petPath.isEmpty()) {
      int currentSpaceIndex = petPath.peek();
      List<String> neighbors = getNeighboringSpaces(spaces.get(currentSpaceIndex).getName());
      boolean moved = false;
      
      for (String neighborName : neighbors) {
        int neighborIndex = findSpaceIndexByName(neighborName);
        if (!visitedSpaces.contains(neighborIndex)) {
          petPath.push(neighborIndex);
          visitedSpaces.add(neighborIndex);
          pet.updateTargetPetLocation(neighborIndex);
          moved = true;
          break; // Break after moving to the first unvisited neighbor
        }
      }
      
      if (!moved) {
        // Backtrack if no unvisited neighbors are found.
        petPath.pop();
      }
      
      break; // Only one step per turn.
    }
  }

  /**
   * Adds a new player to the game, placing them in the specified space.
   * 
   * <p>The method first searches for the space by its name, and once found,
   * a new player is created and added to the player's list with the attributes 
   * provided. If the specified space does not exist, an exception is thrown.</p>
   *
   * @param name      the name of the new player.
   * @param spaceName the name of the space where the player will be placed.
   * @param capacity  the carrying capacity of the player.
   * @param isHuman   a boolean flag indicating whether the player 
   *                  is human (true) or a computer (false).
   *
   * @throws IllegalArgumentException if the specified space does not exist.
   */
  public static void addPlayer(String name, String spaceName, int capacity, boolean isHuman) {
    int locationIndex = -1;  // to store the index of the location where the player should be added.
    for (int i = 0; i < spaces.size(); i++) {
      if (spaces.get(i).getName().equals(spaceName)) {
        locationIndex = i;
        break;
      }
    }

    if (locationIndex != -1) {
      Player newPlayer;
      if (isHuman) {
        newPlayer = new PlayerImpl(name, locationIndex, capacity, true);
      } else {
        newPlayer = new ComputerPlayer(name, locationIndex, capacity);
      }
      players.add(newPlayer);
    } else {
      throw new IllegalArgumentException("The specified space does not exist.");
    } 
  }
  
  @Override
  public String getDetailsOfPlayer(String playerName) {
    int playerIndex = -1; // to store the index of the requested player
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).getName().equals(playerName)) {
        playerIndex = i;
        break;
      }
    }
    if (playerIndex != -1) {
      Player player = players.get(playerIndex);
      StringBuilder playerDetails = new StringBuilder(player.toString());
      playerDetails.append("\nCurrent Location = ");
      playerDetails.append(spaces.get(player.getLocation()).getName());
      return playerDetails.toString();
    } else {
      throw new IllegalArgumentException("This player does not exist.");
    }
  }
 
  @Override
  public Player getCurrentPlayer() {
    if (currentPlayer >= 0 && currentPlayer < players.size()) {
      return players.get(currentPlayer);
    }
    throw new IllegalStateException("No current player set.");
  }

  @Override
  public BufferedImage generateImage() {
    int scale = 30;
    int padding = 20;
    BufferedImage bufferedImage = new BufferedImage((this.cols + 2) * scale,
        (this.rows + 2) * scale, BufferedImage.TYPE_INT_RGB);
    Graphics graphics = bufferedImage.createGraphics();
    for (Space space : spaces) {
      int[] location = space.getLocation();
      int y = location[0] * scale + padding;
      int x = location[1] * scale + padding;
      int height = (location[2] - location[0] + 1) * scale;
      int width = (location[3] - location[1] + 1) * scale;
      graphics.drawString(space.getName(), x + scale, y + scale);
      graphics.drawRect(x, y, width, height);
    }
    graphics.dispose();
    try {
      File outputfile = new File("res/output.png");
      ImageIO.write(bufferedImage, "png", outputfile);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bufferedImage;
  }

  @Override
  public Map<String, Integer> getItems() {
    Player player = players.get(currentPlayer);
    SpaceImpl currentSpace = spaces.get(player.getLocation());
    currentSpace.getListOfItems();
    Map<String, Integer> itemsMap = new HashMap<>();
    
    for (String itemName : currentSpace.getListOfItems()) {
      Item currentItem = currentSpace.getItemByName(itemName);
      itemsMap.put(itemName, currentItem.useItem());
    }
    
    return itemsMap;
  }
  
  @Override
  public String attemptKill(String playerName) {
    Player attacker = findPlayerByName(playerName);
    if (attacker == null) {
      return "Player " + playerName + " does not exist in the game.";
    }
    
    for (Player otherPlayer : players) {
      if (!otherPlayer.equals(attacker) && canSeePlayer(otherPlayer.getName(), playerName)) {
        endOfTurn();
        return "Attack by " + playerName + " was seen by "
               + otherPlayer.getName() + ". No damage done.";
      }
    }
    
    int damage = 1;
    Item bestItem = getBestItemForAttack(attacker);
    if (bestItem != null) {
      damage = bestItem.useItem();
      attacker.removeItem(bestItem.getName());
    }
    
    target.reduceHealth(damage);
    
    if (!target.isAlive()) {
      return playerName + " successfully killed the target using " 
             + (bestItem != null ? bestItem.getName() : "a poke in the eye") 
             + ". " + playerName + " wins!";
    }
    
    endOfTurn();
    return playerName + " attacked the target with " 
           + (bestItem != null ? bestItem.getName() : " a poke in the eye") 
           + " causing " + damage + " damage.";
  }
  
  private Item getBestItemForAttack(Player player) {
    Item bestItem = null;
    int maxDamage = 0;
    for (Item item : player.getItems()) {
      if (item.useItem() > maxDamage) {
        maxDamage = item.useItem();
        bestItem = item;
      }
    }
    return bestItem;
  }

  /**
   * Retrieves a space based on its location (index) in the spaces list.
   * 
   * <p>If the provided index is valid (within the bounds of the spaces list), 
   * the corresponding space is returned. Otherwise, an exception is thrown indicating 
   * that the location is out of bounds.</p>
   *
   * @param location the index of the space to be retrieved.
   * 
   * @return the space located at the specified index.
   *
   * @throws IndexOutOfBoundsException if the provided location 
   *         is out of the bounds of the spaces list.
   */
  public static SpaceImpl getSpaceByIndex(int location) {
    if (location >= 0 && location < spaces.size()) {
      return spaces.get(location);
    } else {
      throw new IndexOutOfBoundsException("The provided location is out of bounds.");
    }
  }
  
  @Override
  public Map<String, int[]> getSpaceLocation() {
    Map<String, int[]> spaceLocations = new HashMap<>();
    for (SpaceImpl space : spaces) {
      spaceLocations.put(space.getName(), space.getLocation());
    }
    return spaceLocations;
  }
  
  @Override
  public Map<String, String> getPlayerLocations() {
    Map<String, String> playerLocations = new HashMap<>();
    for (Player player : players) {
      int locationIndex = player.getLocation();
      String locationName = spaces.get(locationIndex).getName();
      playerLocations.put(player.getName(), locationName);
    }
    return playerLocations;
  }

  /**
   * Get the number of spaces in the game.
   * 
   * @return the number of spaces in the game.
   */
  public static int getNumberOfSpaces() {
    return spaces.size();
  }
  
  @Override
  public String getTargetLocation() {
    SpaceImpl currentSpace = getSpaceByIndex(target.getLocation());
    System.out.println("Target is now at: " + currentSpace.getName());
    return currentSpace.getName();
  }
  
  /**
   * Demonstrates if the turn is end for the current player.
   * Move the pet DFS.
   * Move the target to another place. 
   * And turn the current player to the next one in the player list.
   */
  public void endOfTurn() {
    moveTarget();
    movePet();
    getTargetLocation();
    getPetLocation();
    currentPlayer = (currentPlayer + 1) % players.size();
  }
  
  @Override
  public String getPetLocation() {
    int petLocationIndex = pet.getLocation();
    SpaceImpl petSpace = spaces.get(petLocationIndex);
    System.out.println("The pet, " + pet.getName() + ", is now move to " + petSpace.getName());
    return petSpace.getName();
  }
  
  

}
