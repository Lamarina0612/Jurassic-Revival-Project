package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Button Listener Implementation for the GUI.
 */
public class ButtonListener implements ActionListener {
  private Map<String, Runnable> map;
  
  /**
   * Constructor for the button listener.
   */
  public ButtonListener() {
    this.map = new HashMap<>();
  }
  
  /**
   * Set actions for buttons.
   * 
   * @param actionMap Map that contains the actions for buttons.
   */
  public void setButtonClickedActionMap(Map<String, Runnable> actionMap) {
    this.map = actionMap;
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    if (map.containsKey(e.getActionCommand())) {
      map.get(e.getActionCommand()).run();
    }
  }
  
}
