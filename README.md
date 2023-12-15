# Jurassic World Revival Game (Milestone 4 version)

A Java application that allows users to navigate the vast landscapes of a Jurassic-themed world, visualized via image generation. Users can interact with the program to move characters, generate images, describe rooms, and more. This is a course project from Northeastern University CS5010. The game can run in both text and GUI mode.

## Table of Contents
- [Getting Started](#getting-started)
- [Instructions for Using the JAR File](#instructions-for-using-the-jar-file)
- [Assumptions Made](#assumptions-made)
- [Citations](#citations)
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


## Limitations
1. **AI Behavior Predictability**
Description: The computer-controlled players follow a set algorithm, which may become predictable after repeated gameplay. 
2. **Order of the user added**
Description: If user adds the computer player first, the game will start immediately and loop until the game ends.
3. **Error Handling**
Description: If a computer player wants to move to its neighboring spaces, sometimes it may fail. 
