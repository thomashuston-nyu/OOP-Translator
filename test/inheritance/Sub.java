package inheritance;

public class Sub extends Super {

  public Sub() {
    super(8);
    System.out.println("Sub()");
  }

  public void deep() {
    super.deep();
    System.out.println("Sub.deep()");
  }

  public String toString() {
    return "Sub.toString()";
  }

}
