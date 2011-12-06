package inheritance;

public class Sub extends Super {

  public Sub() {
    super(8);
    System.out.println("Sub()");
  }

  public Sub(int x) {
    super("hey");
    System.out.println("Sub(int)");
  }

  public void deep() {
    super.deep();
    System.out.println("Sub.deep()");
  }

  public void deep(Object o) {
    System.out.println("Sub.deep(Object)");
  }
  
  public void deep(String s) {
    System.out.println("Sub.deep(String)");
  }

  public String toString() {
    return "Sub.toString()";
  }

}
