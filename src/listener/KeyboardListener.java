package listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Key Listener Implementation for the GUI.
 */
public class KeyboardListener extends KeyAdapter {
  private Map<Integer, Runnable> map;
  
  /**
   * Keyboard listener constructor.
   */
  public KeyboardListener() {
    this.map = new HashMap<>();
  }
  
  /**
   * Set actions for keyboard.
   * 
   * @param actionMap Map that contains actions for keyboards.
   */
  public void setKeyBoardActionMap(Map<Integer, Runnable> actionMap) {
    this.map = actionMap;
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    if (map.containsKey(e.getKeyCode())) {
      map.get(e.getKeyCode()).run();
    }
  }
}
