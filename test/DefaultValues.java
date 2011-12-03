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

  public DefaultValues() {}

  public void test() {
    System.out.println(a);
    System.out.println(b);
    System.out.println(c);
    System.out.println(d);
    System.out.println(e);
    System.out.println(f);
    System.out.println(g);
    System.out.println(h);
    System.out.println(i);
    System.out.println(j);
    System.out.println(k);
    System.out.println(l);
    System.out.println(l[0]);
    System.out.println(m);
    System.out.println(n);
  }

  public static void main(String[] args) {
    DefaultValues x = new DefaultValues();
    x.test();
  }

}
