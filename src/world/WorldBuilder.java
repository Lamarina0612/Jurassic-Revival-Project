package world;

import item.Item;
import item.ItemImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import models.Point;
import space.SpaceImpl;
import targetcharacter.TargetCharacter;
import targetcharacter.TargetCharacterImpl;
import targetcharacter.TargetPet;
import targetcharacter.TargetPetImpl;

/**
 * Responsible for constructing and initializing the game world.
 *
 * <p>The WorldBuilder class provides functionality to set up the game world by 
 * adding spaces, items, players, and configuring their initial states and positions.
 * </p>
 *
 */
public class WorldBuilder {
  private int numberOfRows;
  private int numberOfColumns;
  private String worldName;
  private TargetCharacter target;
  private TargetPet pet;
  private List<SpaceImpl> spaces;
  private Map<Integer, Set<Integer>> worldMap; 

  /**
   * Constructs a {@code WorldImpl} object based on 
   * the configuration provided by the {@code readable}.
   *
   * <p>This method reads the configuration from the 
   * provided {@code readable} source, interprets the data,
   * and then initializes and constructs a new 
   * instance of {@code WorldImpl}. The configuration should specify
   * spaces, items, players, and their initial states and positions in the game world.</p>
   *
   * @param readable The source of the configuration data for the world setup.
   * @return A fully initialized {@code WorldImpl} object based on the provided configuration.
   * @see WorldImpl
   * @see Readable
   *
   */

  public WorldImpl buildWorldFromConfig(Readable readable) {
    Scanner in = new Scanner(readable);

    // Reading World description
    this.numberOfRows = in.nextInt();
    this.numberOfColumns = in.nextInt();
    this.worldName = in.nextLine().trim();

    int targetHealth = in.nextInt();
    String targetName = in.nextLine().trim();
    String petName = in.nextLine().trim();
    this.target = new TargetCharacterImpl(targetName, targetHealth, 1); 
    this.pet = new TargetPetImpl(petName, 1);
    
    // Reading spaces or rooms
    int numberOfSpaces = in.nextInt();
    spaces = new ArrayList<>();
    for (int i = 0; i < numberOfSpaces; i++) {
      int upperLeftRow = in.nextInt();
      int upperLeftCol = in.nextInt();
      Point upperLeftCorner = new Point(upperLeftRow, upperLeftCol);
      int lowerRightRow = in.nextInt();
      int lowerRightCol = in.nextInt();
      Point lowerRightCorner = new Point(lowerRightRow, lowerRightCol);
      String spaceName = in.nextLine().trim();
      spaces.add(new SpaceImpl(spaceName, upperLeftCorner, lowerRightCorner)); 
    }

    // Reading items
    int numberOfItems = in.nextInt();
    for (int i = 0; i < numberOfItems; i++) {
      int roomIndex = in.nextInt();
      int damage = in.nextInt();
      String itemName = in.nextLine().trim();
      Item item = new ItemImpl(itemName, damage);
      spaces.get(roomIndex).addItem(item);
    }

    in.close();

    return new WorldImpl(this.numberOfRows, this.numberOfColumns, 
             this.worldName, this.spaces, this.target, this.pet, this.worldMap);
  }
}
