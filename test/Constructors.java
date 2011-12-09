public class Constructors {

  public Constructors() {
    System.out.println("Constructors()");
  }

  public Constructors(int x) {
    System.out.println("Constructors(int)");
  }

  public Constructors(String s, int x, Object o) {
    System.out.println("Constructors(String, int, Object)");
  }

  public Constructors(Object o, int x, Object o2) {
    System.out.println("Constructors(Object, int, Object)");
  }

  public static void Constructors$void() {
    System.out.println("Constructors.Constructors$void()");
  }

  public static void Constructors$int32_t() {
    System.out.println("Constructors.Constructors$int32_t()");
  }

  public static void main(String[] args) {
    new Constructors();
    new Constructors(5);
    new Constructors("test", 5, "test");
    new Constructors(null, 0, null);
  }



}
