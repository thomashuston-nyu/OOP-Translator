package demo;

import demo.deeper.Deep;

public class Sub extends Super {

  public static int y = 99;

  // Constructor
  public Sub(int x1) {
    // Set x
    x = x1;
    
    // Create a new Deep object using the imported class
    Deep d = new Deep();
    
    // Call d.toString(), which simply uses Object's toString method
    System.out.println(d);
  }

  // Override getX
  public int getX() {
    return x;
  }

  // Override setX
  public void setX(int x1) {
    setXplusTen(x1);
  }
  
  // Add a new method
  public void setXplusTen(int x1) {
    x = x1 + 10;
  }
  
  // Override toString
  public String toString() {
    return "This is Sub.";
  }

  public static void main(String[] args) {
    Super s = new Super();
  }

}
