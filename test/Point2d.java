import pack.DeepClass;
import pack2.BClass;

public class Point2d {
  /* The X and Y coordinates of the point--instance variables */
  private double x;
  private double y;
  
  public Point2d (double px, double py) { // Constructor
    x = px;
    y = py;
  }

  public void setX(double px) {
    x = px;
  }
  
  public double getX() {
    return x;
  }
  
  public void setY(final double py)  {
    y = py;
  }
  
  public double getY() {
    return y;
  }
  
  public void setXY(double px, double py) {
    setX(px);
    setY(py);
  }
  
  public String toStringForXY() {
    String str = "(" + x + ", " + y;
    return str;
  }
  
  public String toString() {
    String str = toStringForXY() + ")";
    return str;
  }
  
  public static void main(String[] args) {
    Point2d p = new Point2d(5.0,7.0);
    System.out.println(p);
    boolean x = false;
    System.out.println(x);
  }
}
