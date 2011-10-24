public class Sample {
  int x;
  int y;
  
  public Sample(int x1, int y1) {
    x = x1;
    y = y1;
  }
  
  public int getX() {
    return x;
  }

  public String toString() {
    return "ok";
  }

  public static void main(String[] args) {
    Sample sample = new Sample(3, 8);
    int a = sample.getX();
    if (a < 2)
      System.out.println("a < 2");
    else
      while (a > 2)
        a--;
    // System.out.println(x);
    // System.out.println(a);
  }
}
