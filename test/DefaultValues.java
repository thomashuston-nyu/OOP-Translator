public class DefaultValues {
  byte a;
  short b;
  int c;
  long d;
  float e;
  double f;
  boolean g;
  char h;
  Object i;
  String j;
  int[] k;
  int[] l = new int[5];
  Object[] m;
  Object[] n = new Object[5];
  boolean[][] o;
  boolean[][] p = new boolean[5][5];

  public DefaultValues() {}

  public void test() {
    System.out.println("byte\t\t" + a);
    System.out.println("short\t\t" + b);
    System.out.println("int\t\t\t" + c);
    System.out.println("long\t\t" + d);
    System.out.println("float\t\t" + e);
    System.out.println("double\t\t" + f);
    System.out.println("boolean\t\t" + g);
    System.out.println("char\t\t" + h);
    System.out.println("Object\t\t" + i);
    System.out.println("String\t\t" + j);
    System.out.println("int[]\t\t" + k);
    System.out.println("new int[5]\t\t" + l);
    System.out.println("Object[]\t\t" + m);
    System.out.println("new Object[5]\t" + n);
    System.out.println("boolean[][]\t\t" + o);
    System.out.println("new boolean[5][5]\t" + p);
  }

  public static void main(String[] args) {
    DefaultValues x = new DefaultValues();
    x.test();
  }

}
