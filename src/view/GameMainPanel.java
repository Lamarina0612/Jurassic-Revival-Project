package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import listener.MouseClickListener;

/**
 * Main panel for this game.
 * 
 */
public class GameMainPanel extends JScrollPane {
 
  private static final long serialVersionUID = 4387596394608351523L;
  private Map<String, JPanel> spacePanels;
  private final int scale = 20;
  private final int padding = 18;
  private final int offset = 100;
  private JPanel mainPanel;
  private JScrollPane scrollLog;
  private JPanel logPanel;
  
  
  /**
   * Object implementation for the main panel in this game.
   */
  public GameMainPanel() {
    spacePanels = new HashMap<>();
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    
    this.logPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    this.scrollLog = new JScrollPane(this.logPanel);
    this.scrollLog.setPreferredSize(new Dimension(400, 100));
    mainPanel.add(scrollLog, BorderLayout.NORTH);
    this.setViewportView(mainPanel);
    
  }
  
  /**
   * Log Screen implementation for this game.
   * 
   * @param message log message info of the game.
   */
  public void logScreen(String message) {
    logPanel.removeAll();
    JLabel logLabel = new JLabel(message);
    logPanel.add(logLabel);
    logPanel.revalidate();
    logPanel.repaint();
  }
  
  /**
   * Display target icon on the game board.
   * 
   * @param targetLocation target's location
   * @param petLocation pet's location
   * @param listener Listener to execute the actions
   */
  public void displayTarget(String targetLocation, String petLocation,
      MouseClickListener listener) {
    if (!targetLocation.equals(petLocation)) {
      JLabel targetLabel = new JLabel();
      ImageIcon targetIcon = new ImageIcon("res/images/target.png");
      targetLabel.setIcon(targetIcon);
      targetLabel.addMouseListener(listener);
      spacePanels.get(targetLocation).add(targetLabel);    
    }
  }
  
  /**
   * Display players in the game on the game board. 
   * Players here are displayed as icons.
   * 
   * @param players list of player
   * @param playerColors icon color for that player
   * @param listener Listener to execute the actions
   */
  public void displayPlayers(Map<String, String> players, Map<String, Color> playerColors,
      MouseClickListener listener) {
    for (Map.Entry<String, String> player : players.entrySet()) {
      JLabel playerLabel = new JLabel();
      Color playerColor = playerColors.get(player.getKey());
      GameIcon img = new GameIcon(new File("res/images/player.png"));
      ImageIcon playerIcon = img.setColor(playerColor);
      playerLabel.setIcon(playerIcon);
      playerLabel.setName(player.getKey());
      playerLabel.addMouseListener(listener);
      spacePanels.get(player.getValue()).add(playerLabel);
      
    }
  }
  
  /**
   * Display spaces in the game, and one JPanel represents a single space.
   * 
   * @param spaces Map of space names with their location.
   * @param petLocation pet's location
   * @param message Result of previous action.
   * @param listener Listener for performing actions.
   */
  public void displaySpaces(Map<String, int[]> spaces, String petLocation, String message,
      MouseClickListener listener) {
    JPanel spaceContainerPanel = new JPanel();
    spaceContainerPanel.setLayout(null);
    
    for (Map.Entry<String, int[]> space : spaces.entrySet()) {
      JPanel spacePanel = new JPanel();
      int[] location = space.getValue();
      int height = (location[2] - location[0] + 1) * scale;
      int width = (location[3] - location[1] + 1) * scale;
      spacePanel.setName(space.getKey());
      spacePanel.addMouseListener(listener);
      spacePanel.setSize(new Dimension(width, height));
      spacePanel.setLocation(location[1] * scale + padding, location[0] * scale + padding + offset);
      spacePanel.setBorder(BorderFactory.createCompoundBorder(
           BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
      spacePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      
      if (space.getKey().equals(petLocation)) {
        spacePanel.setBackground(Color.LIGHT_GRAY);
      }
      JLabel spaceInfo = new JLabel(space.getKey());
      spacePanel.add(spaceInfo);
      spacePanels.put(space.getKey(), spacePanel);
      spaceContainerPanel.add(spacePanel);
    }
    
    mainPanel.removeAll();
    mainPanel.add(scrollLog, BorderLayout.NORTH);
    mainPanel.add(spaceContainerPanel, BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
  }
}
