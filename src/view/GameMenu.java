package view;

import java.util.Objects;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import listener.ButtonListener;
import utils.Instructions;

/**
 * Game menu implementation GUI version.
 */
public final class GameMenu extends JMenuBar {
  
  private static final long serialVersionUID = -7152019393830266770L;
  private JMenu menu;
  private JMenuItem newGame;
  private JMenuItem restartGame;
  private JMenuItem addPlayer;
  private JMenuItem quitGame;

  /**
   * Creates objects for the game menu.
   */
  public GameMenu() {
    this.menu = new JMenu("Game");
    this.newGame = new JMenuItem(Instructions.NEW_GAME);
    this.newGame.setActionCommand(Instructions.NEW_GAME);
    
    this.restartGame = new JMenuItem(Instructions.RESTART_GAME);
    this.restartGame.setActionCommand(Instructions.RESTART_GAME);
    
    this.quitGame = new JMenuItem(Instructions.QUIT_GAME);
    this.quitGame.setActionCommand(Instructions.QUIT_GAME);
    
    this.addPlayer = new JMenuItem(Instructions.ADD_PLAYER);
    this.addPlayer.setActionCommand(Instructions.ADD_PLAYER);
    
    this.menu.add(this.newGame);
    this.menu.add(this.restartGame);
    this.menu.add(this.addPlayer);
    this.menu.add(this.quitGame);
    
    this.add(this.menu);

  }
  
  /**
   * Add button action listener to the menu.
   * 
   * @param buttonListener button listener
   */
  public void addActionListener(ButtonListener buttonListener) {
    Objects.requireNonNull(buttonListener);
    this.newGame.addActionListener(buttonListener);
    this.restartGame.addActionListener(buttonListener);
    this.addPlayer.addActionListener(buttonListener);
    this.quitGame.addActionListener(buttonListener);
  }
}
