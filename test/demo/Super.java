package demo;

public class Super {
  
  int x;
  
  public Super() {
    x = 0;
  }

  public Super(int x1) {
    x = x1;
  }

  public int getX() {
    return x;
  }

  public void setX(int x1) {
    x = x1;
  }

  public void printMessage() {
    Sub sub = new Sub(5);
    System.out.println("Welcome to the demo package.");
  }

  public String toString() {
    return "This is Super.";
  }

}
