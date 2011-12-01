public class Test {

  String s;

  public Test() {
    System.out.println("Test()");
    this.s = "Default";
  }

  public Test(String s) {
    System.out.println("Test(String)");
    this.s = s;
  }

  public Test(Object o) {
    this(o.toString());
    System.out.println("Test(Object)");
  }

  public static void main(String[] args) {
    Test t1 = new Test();
    System.out.println(t1.s);
    Test t2 = new Test("2");
    System.out.println(t2.s);
    Test t3 = new Test(t1);
    System.out.println(t3.s);
  }

}
