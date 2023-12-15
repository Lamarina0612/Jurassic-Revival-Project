<<<<<<< HEAD
# Kill Doctor Lucky (Milestone 4 version)

A Java application that allows users to navigate the vast landscapes of a Jurassic-themed world, visualized via image generation. Users can interact with the program to move characters, generate images, describe rooms, and more.
## Table of Contents
- [Getting Started](#getting-started)
- [Instructions for Using the JAR File](#instructions-for-using-the-jar-file)
- [Assumptions Made](#assumptions-made)
- [Citations](#citations)
- [Changes from Milestone 1 Design](#changes-from-milestone-1-design)
- [Changes from Milestone 2 Design](#changes-from-milestone-2-design)
- [Changes from Milestone 3 Design](#changes-from-milestone-3-design)
- [Limitations](#limitations)
  
## Getting Started
Before you dive in, ensure you have the following prerequisites:
- Java Development Kit (JDK) 8 or higher
- JUnit 4 library for running the test cases
- Your favorite Java IDE (Eclipse, IntelliJ IDEA, etc.)
- Navigate to the project folder and open it with your IDE.

## Instructions for Using the JAR File
1. Navigate to the /res directory containing the JAR file.
2. Open your terminal or command prompt.
3. For running the text-based game, please use the command below:
```
java -jar milestone4runnable.jar myworld.txt 5 TEXT
```
4. For running the GUI-based game, please use the command below:
```
java -jar milestone4runnable.jar myworld.txt 5 GUI
```
GUI and TEXT represent the game mode, you can change the turns of game by replacing the number '5' to any number you like.

## Assumptions Made (Milestone 4 version)
1. **Game Structure**: The game consists of multiple players and spaces. Each space can contain multiple items. The world has one target character.
2. **Player Interaction**: Players can move between neighboring spaces, pick up items, and interact with the target character.
3. **Target Movement**: The target character can move to any space in the game.
5. **Space Structure**: Spaces are rectangular and can be neighboring each other either horizontally or vertically, but not diagonally.
6. **Item Usage**: When a player picks up an item, it's immediately removed from the that space.
7. **Player Actions and Visibility:** We assumed that a player's visibility is limited to their current space and neighboring spaces. This reflects real-world limitations on a person's field of view.
8. **Pet Movement**: The pet’s movement was assumed to follow a depth-first traversal algorithm. This decision was to add unpredictability and complexity to the game, enhancing the strategic aspect.
9. **Computer-Controlled Behavior**: It was assumed that computer-controlled players should make decisions automatically, without user input. This includes moving, picking up items, and attempting to kill the target.
10. **Game End Conditions**: We assumed that the game ends either when the target character is killed or the maximum number of turns is reached, with no possibility of a draw or tie.
11. **Item Usage and Removal**: When items are used in an attempt on the target character's life, they are removed from play. This assumption was made to add a strategic layer to item management.
12. **Limited Player Information**: Players receive limited information at the start of their turn to make gameplay more challenging and realistic.

## Citations
1. Get neighboring spaces: https://stackoverflow.com/questions/3775905/programming-logic-how-to-check-for-neighbors-in-a-grid
2. Color Scheme: https://www.rapidtables.com/web/color/color-scheme.html
3. Orable JSwing: https://docs.oracle.com/javase/tutorial/uiswing/components/colorchooser.html
4. Stub Test: https://semaphoreci.com/community/tutorials/stubbing-and-mocking-with-mockito-2-and-junit#basic-stubbing-with-mockito
   
## Example Usage

1. **Navigating the World**:
    - When prompted, provide the path to the world file.
    - Follow on-screen instructions to move characters, display world details, and more.

2. **Generating Images**:
    - Select the "Output image" option.
    - Images are typically saved to `/res/outputImage.png` (or the specified path).


## Changes from Milestone 1 Design
1. **Player Class Refactoring**: The `Player` class was refactored to include more details like `capacity` and `isHuman`.
2. **Improved Graphics**: Introduced a new method `generateImage()` to provide a graphical representation of the game world.
3. **Item Interaction**: Enhanced item interaction methods in spaces to improve the user experience.
4. **Error Handling**: Improved error handling across the game for better resilience and user feedback.
5. **New Assumptions**: Based on feedback and gameplay requirements, added a few more assumptions (as listed above) to make the game more realistic and engaging.

## Changes from Milestone 2 Design
1. **Enhanced 'Look Around' Command**: Updated to provide detailed information about the current space, including other players and visible items, as well as insight into neighboring spaces.
2. **Target Character's Pet**: Introduced the pet as a new game element, affecting visibility in spaces and adding a new layer of strategy.
3. **Automated Pet Movement**: Implemented a DFS algorithm for the pet's autonomous movement, adding complexity to the game dynamics.
4. **Attempt Kill Functionality**: Included the ability for players to attempt to kill the target character, with success dependent on whether the attack is seen by others.
5. **End Game Logic**: Developed a comprehensive end-game scenario, encompassing conditions like successful assassination, escape of the target due to turn limit, and declaring the winner.
6. **Computer-Controlled Player Upgrade**: Enhanced AI behavior for computer-controlled players, including making kill attempts and strategic item usage.
7. **Player Visibility Logic**: Added the functionality to determine if one player can see another, based on their relative locations and neighboring spaces.
8. **Turn-Based Mechanics**: Refined turn-based mechanics to include pet movement and turn-end triggers, ensuring a balanced and fair gameplay experience.

## Changes from the Milestone 3 Design
1. Added view component, and successfully finished the project using MVC.
2. Enhanced controller by adding GUI controller. Now the game can be executed in both text-based and Gui.
3. Welcome Screen and End Screen: Enhanced user experience with welcome screen and end screen.
4. Log screen: User can see what is going on in the game by looking at the game log, which is at the top of the graphical map.
5. Up to 10 players in the game, each player is represented by different colors implemented by JColorChooser.

## Limitations
1. **AI Behavior Predictability**
Description: The computer-controlled players follow a set algorithm, which may become predictable after repeated gameplay. 
2. **Order of the user added**
Description: If user adds the computer player first, the game will start immediately and loop until the game ends.
3. **Error Handling**
Description: If a computer player wants to move to its neighboring spaces, sometimes it may fail. 

=======
# Jurassic-Revival-Project
>>>>>>> origin/main
