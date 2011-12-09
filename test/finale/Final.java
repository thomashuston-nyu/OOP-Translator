package finale;

public class Final {

  public int a = 11;
  private int b;
  public static Object c = new Object();
  private static String d = new String("Final");

  Final() {
    System.out.println("Final()");
  }

  public static void main(String[] args) {
    System.out.println("There are " + args.length + " command-line arguments.");
    for (int i = 0; i < args.length; i++) {
      System.out.println(i + ": " + args[i]);
    }
    System.out.println();
    Final f = new Final();
    System.out.println("a = " + f.a);
    System.out.println("b = " + f.b);
    System.out.println("c = " + f.c);
    System.out.println("d = " + f.d);
  }

}
