public class Sample {
  public void test() {
    int x = 2;
    int y = 3;
    int z = 0;
    boolean b = true;
    String s;
    if (x == y)
      z = x + y;
    else if (x + 1 == y)
      z = x * y;
    else
      s = "asdf";
    while (z > x)
      z--;
    if (b)
      z = x / y;
    if (b == true)
      z = 1;
    if (x >= 5)
      x = 6;
    if (x != 6)
      x = 7;
    while (b)
      b = false;
  }
}
