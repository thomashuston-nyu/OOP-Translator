import demo.Sub;
import demo.Super;

public class Sample {
  
  public static void main(String[] args) {
    Super sup = new Super(1);
    Sub sub = new Sub(2);

    sup.printX();
    sup.setX(5);
    int v = sup.getX();
    System.out.println(v);

    System.out.println(sup);
    System.out.println(sub);

    sub.printX();

    if (v < 5)
      System.out.println("v < 5");
    else if (v == 5)
      System.out.println("v = 5");
    else
      System.out.println("v > 5");

    while (v < 10)
      v++;
    System.out.println("v = 10");

    for (int i = 0; i < 10; i++)
      System.out.print(i);
    System.out.println();
  }

}
