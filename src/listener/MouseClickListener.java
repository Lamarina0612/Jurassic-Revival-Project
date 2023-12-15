package listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Mouse Click Listener implementation for GUI.
 */
public class MouseClickListener extends MouseAdapter {
  private Map<Integer, Consumer<String>> map;
  
  /**
   * Mouse click constructor.
   */
  public MouseClickListener() {
    this.map = new HashMap<>();
  }
  
  /**
   * Set actions for mouse clicks.
   * 
   * @param actionMap Map that contains actions for mouse clicks.
   */
  public void setMouseClickListenerMap(Map<Integer, Consumer<String>> actionMap) {
    this.map = actionMap;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    map.get(e.getButton()).accept(e.getComponent().getName());
  }
}
