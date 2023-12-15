package models;

/**
 * Represents a 2D point in a Cartesian coordinate system.
 * <p>
 * The {@code Point} class is used to define a position (or location) 
 * in a two-dimensional space using x1 and Y coordinates.
 * </p>
 */
public class Point {
  private int x1;
  private int y1;
  
  /**
   * Constructor for the Point class.
   *
   * @param x1 The x-coordinate of the point.
   * @param y1 The y1-coordinate of the point.
   */
  public Point(int x1, int y1) {
    this.x1 = x1;
    this.y1 = y1;
  }
  
  /**
   * Getter for the x1-coordinate.
   *
   * @return The x1-coordinate of the point.
   */
  public int getX() {
    return x1;
  }

  /**
   * Getter for the y1-coordinate.
   *
   * @return The y1-coordinate of the point.
   */
  public int getY() {
    return y1;
  }
  
  @Override
  public String toString() {
    return "(" + x1 + ", " + y1 + ")";
  }
}
