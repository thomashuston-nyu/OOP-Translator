package inheritance;

public class Super {
  public static final int CONSTANT = 5;
  
  int x;
  String s;
  boolean b = true;

  public Super() {
    this(3);
    System.out.println("Super()");
  }

  public Super(int x) {
    System.out.println("Super(int)");
    this.x = x;
  }

  public Super(String s) {
    System.out.println("Super(String)");
    this.s = s;
  }

  public void deep() {
    System.out.println("Super.deep()");
  }

  public boolean deep(int x) {
    if (x < this.x)
      System.out.println("x < this.x");
    return true;
  }

  public String toString() {
    return super.toString() + ", Super.toString()";
  }

  public static void main(String[] args) {
    System.out.println("Super sup1 = new Super():");
    Super sup1 = new Super();

    System.out.println("\nSuper sup2 = new Super(4):");
    Super sup2 = new Super(4);

    System.out.println("\nSuper sup3 = new Super(\"a\"):");
    Super sup3 = new Super("a");

    System.out.println("\nsup1.deep():");
    sup1.deep();

    System.out.println("\nsup2.deep(7):");
    sup2.deep(7);

    System.out.println("\nSystem.out.println(sup3):");
    System.out.println(sup3);

    System.out.println("\nSub sub1 = new Sub():");
    Sub sub1 = new Sub();

    System.out.println("\nSub sub2 = new Sub(5):");
    Sub sub2 = new Sub(5);

    System.out.println("\nSuper sup4 = new Sub():");
    Super sup4 = new Sub();

    System.out.println("\nsub1.deep():");
    sub1.deep();

    System.out.println("\nSystem.out.println(sub1):");
    System.out.println(sub1);

    System.out.println("\nsup4.deep():");
    sup4.deep();
  }

}
