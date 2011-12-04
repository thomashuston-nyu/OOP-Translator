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
    System.out.println("Super(int x)");
    this.x = x;
  }

  public Super(String s) {
    System.out.println("Super(String s)");
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
    Super sup1 = new Super();
    Super sup2 = new Super(4);
    Super sup3 = new Super("a");
    sup1.deep();
    sup2.deep(7);
    System.out.println(sup3.toString());

    Sub sub = new Sub();
    Super sup4 = new Sub();
    sub.deep();
    System.out.println(sub.toString());
    sup4.deep();
  }

}
