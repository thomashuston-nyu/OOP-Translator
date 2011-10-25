package demo;

public class Sub extends Super {

  int x;

  public Sub(int x1) {
    x = x1;
  }

  public int getX() {
    return x;
  }

  public void setX(int x1) {
    x = x1 + 10;
  }

  public String toString() {
    return "This is Sub.";
  }

}
