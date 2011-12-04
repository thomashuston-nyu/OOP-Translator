public class MethodChaining {
  int x;

  public MethodChaining() {
    x = 1;
  }

  public MethodChaining a() {
    x++;
    return this;
  }

  public MethodChaining b() {
    x *= 2;
    return this;
  }

  public MethodChaining c() {
    x--;
    return this;
  }

  public void d() {
    System.out.println(x);
  }

  public MethodChaining e(int x) {
    System.out.println(x);
    return this;
  }

  public MethodChaining e(String s) {
    System.out.println(s);
    return this;
  }

  public int getX() {
    return x;
  }

  public static void main(String[] args) {
    MethodChaining m = new MethodChaining();
    m.a().b().c().d();
    MethodChaining n = m.e(1).e("jkl;").e(1);
    System.out.println(n.getX());
  }

}
