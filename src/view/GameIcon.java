package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Game Icons implementation for image.
 */
public class GameIcon extends ImageIcon {
  private static final long serialVersionUID = -4147192390033287981L;
  private File file;
  
  /**
   * Constructor for game icon.
   * 
   * @param fileObject file that constructs the game icon.
   */
  public GameIcon(File fileObject) {
    this.file = fileObject;
  }
  
  /**
   * Set the icon color.
   * 
   * @param color new color set for the player
   * @return new image icon set in the given color
   */
  public ImageIcon setColor(Color color) {

    BufferedImage image = null;
    try {
      image = ImageIO.read(this.file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),
        BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < image.getHeight(); x++) {
      for (int y = 0; y < image.getWidth(); y++) {
        int pixelColor = image.getRGB(y, x);
        if (pixelColor < 0) {
          int newColor = color.getRGB();
          newImage.setRGB(y, x, newColor);
        }
      }
    }
    
    return new ImageIcon(newImage);
  }
  
}
