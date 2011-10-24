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

  public void printX() {
    System.out.println(x);
  }

  public String toString() {
    return "This is Super.";
  }

}
