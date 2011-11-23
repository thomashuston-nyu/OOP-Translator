import demo.*;

public class Sample {

  public static int stat_int = 11;
  public static final Object oo = new Object();

  public void test(Sample a, Object o, Sample b) {
    System.out.println(a);
    return;
  }

  public Sample test2() {
    return this;
  }

  public Sample test() {
    return this;
  }
  
  public static void main(String[] args) {
    
    // Create superclass
    Super sup = new Super();
    
    // Create subclass
    Sub sub = new Sub(2);
    
    // Call printMessage
    sup.printMessage();
    
    // Call printMessage from superclass
    sub.printMessage();
    
    // Test toString methods
    System.out.println(sup);
    System.out.println(sub);

    // Print out the initial value of superclass's x
    int v = sup.getX();
    System.out.println(v);
    
    // Change the value of superclass's x
    sup.setX(5);
    v = sup.getX();
    
    // Check if v equals 5
    if (v < 5) {
      System.out.println("v < 5");
    } else if (v == 5) {
      System.out.println("v = 5");
    } else {
      System.out.println("v > 5");
    }

    // Change the value of subclass's x to 20
    sub.setXplusTen(10);
    int w = sub.getX();
    System.out.println(w);
    
    // If w is greater than 10, decrement it until w = 10
    while (w > 10)
      w--;
    System.out.print("w = ");
    System.out.println(w);

    // Print out 0-9 on the same line
    for (int i = 0; i < 10; i++)
      System.out.print(i);
    System.out.println();

    System.out.println("test " + sup.toString());

    Sample sam = new Sample();
    sam.test(sam, sam, sam);

    if (sam.equals(sam))
      System.out.println("TRUE");

    Super[] ss = new Super[4];
    ss[1] = new Super();
    ss[2] = sup;
    System.out.println(ss[1]);

    for (int ii = 0; ii < args.length; ii++) {
      System.out.println(args[ii]);     
    }

    System.out.println(ss);

    int[][] iii = new int[3][4];
    System.out.println(iii);
    iii[0][0] = 5;
    System.out.println(iii[0][0]);

    long[][] l = new long[2][5];
    System.out.println(l);
    l[1][4] = 11;
    System.out.println(l[1][4]);

    String[][] sss = new String[5][4];
    System.out.println(sss);
    sss[3][3] = "HELLO";
    sss[4][3] = "THERE";
    System.out.println(sss[3][3] + " " + sss[4][3]);

    int tt[][] = {{0, 1},{5, 4},{3, 1, 6}};
    System.out.println(tt[2][0]);

    System.out.println(oo);   

    String dj = "DJ";

    ((Object)dj).toString();

    sam.test().test2().test(sam, sam, sam);
    
  }

}
