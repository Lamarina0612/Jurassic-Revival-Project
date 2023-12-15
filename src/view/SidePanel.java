package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import utils.Instructions;

/**
 * Side panel for this game.
 * 
 */
public class SidePanel extends JPanel {

  private static final long serialVersionUID = -6418093059524090837L;
  private JPanel playersPanel;
  private JPanel targetPanel;
  private JPanel turnPanel;
  private JScrollPane scrollPlayers;
  private JScrollPane scrollTarget;
  private JScrollPane scrollTurn;
  private GridBagConstraints constraint;
  private JButton newGameButton;
  private JButton restartGameButton;
  private JButton addPlayerButton;
  private JButton quitGameButton;
  
  /**
   *  Side Panel for the game.
   */
  public SidePanel() {
    this.constraint = new GridBagConstraints();
    this.constraint.gridwidth = GridBagConstraints.REMAINDER;
    this.setLayout(new BorderLayout());
    
    this.targetPanel = new JPanel();
    this.targetPanel.setLayout(new GridBagLayout());
    this.scrollTarget = new JScrollPane(this.targetPanel);
    this.scrollTarget.setPreferredSize(new Dimension(100, 100));
    
    this.playersPanel = new JPanel();
    this.playersPanel.setLayout(new GridBagLayout());
    this.scrollPlayers = new JScrollPane(this.playersPanel);
    this.scrollPlayers.setPreferredSize(new Dimension(100, 450));
    
    
    this.turnPanel = new JPanel();
    this.scrollTurn = new JScrollPane(this.turnPanel);
    
    newGameButton = new JButton("New Game");
    newGameButton.setActionCommand(Instructions.NEW_GAME);
    add(newGameButton);

    restartGameButton = new JButton("Restart Game");
    restartGameButton.setActionCommand(Instructions.RESTART_GAME);
    add(restartGameButton);

    addPlayerButton = new JButton("Add Player");
    addPlayerButton.setActionCommand(Instructions.ADD_PLAYER);
    add(addPlayerButton);

    quitGameButton = new JButton("Quit Game");
    quitGameButton.setActionCommand(Instructions.QUIT_GAME);
    add(quitGameButton);
    
    JSplitPane playerSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTurn, scrollPlayers);
    playerSplit.setEnabled(false);
    playerSplit.setDividerSize(0);
    
    JSplitPane targetSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, playerSplit, scrollTarget);
    targetSplit.setEnabled(false);
    targetSplit.setDividerSize(0);

    this.add(targetSplit);
    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
        BorderFactory.createLoweredBevelBorder()));
    
  }
  
  
  /**
   * Display number of turns left in this game.
   * 
   * @param numberOfTurns number of turns left in the game.
   */
  public void displayTurnsLeft(int numberOfTurns) {
    turnPanel.removeAll();
    JLabel turns = new JLabel(numberOfTurns + " turns left in the game");
    turns.setFont(new Font(turns.getFont().getFontName(), Font.BOLD, 18));
    turns.setForeground(Color.RED);
    turnPanel.add(turns);
    turnPanel.revalidate();
    turnPanel.repaint();
  }
  
  /**
   * A side panel that displays list of players in the game.
   * 
   * @param currentPlayer current player in this turn
   * @param playersColor the color of the player
   */
  public void displayListOfPlayers(String currentPlayer, Map<String, Color> playersColor) {
    this.playersPanel.removeAll();
    JLabel head = new JLabel("Players");
    head.setFont(new Font(head.getFont().getFontName(), Font.BOLD, head.getFont().getSize()));
    this.playersPanel.add(head, constraint);
    this.playersPanel.add(Box.createVerticalStrut(50));
    for (String player : playersColor.keySet()) {
      JLabel p = new JLabel(player);
      GameIcon img = new GameIcon(new File("res/images/player.png"));
      ImageIcon playerIcon = img.setColor(playersColor.get(player));
      p.setIcon(playerIcon);
      p.setHorizontalAlignment(JLabel.LEFT);
      p.setVerticalAlignment(JLabel.TOP);
      p.setPreferredSize(new Dimension(200, 35));
      this.playersPanel.add(p, constraint);
      this.playersPanel.add(Box.createVerticalStrut(40));
      if (player.equals(currentPlayer)) {
        p.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
      }
    }
    this.playersPanel.revalidate();
    this.playersPanel.repaint();
  }

  /**
   * Display target details implementation.
   * 
   * @param targetDetails details of the target
   */
  public void displayTargetDetails(String targetDetails) {
    this.targetPanel.removeAll();
    JLabel head = new JLabel("Target Details");
    head.setFont(new Font(head.getFont().getFontName(), Font.BOLD, head.getFont().getSize()));
    ImageIcon img = new ImageIcon("res/images/target.png");
    String target = "<html>"
        + targetDetails.replaceAll("\n", "<br/>")
        .replaceAll("Target Details:", "") + "</html>";
    JLabel targetLabel = new JLabel(target);
    targetLabel.setIcon(img);
    this.targetPanel.add(head, constraint);
    this.targetPanel.add(targetLabel, constraint);
    this.targetPanel.revalidate();
    this.targetPanel.repaint();
  }
  
}
