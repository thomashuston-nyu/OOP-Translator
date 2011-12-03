public class Naming {

  public static void main(String[] args) {
    int bool;
    int int32_t;
    int $bool;
    int $int32_t;
    int $$$$$$$$$$$$$$$$$$$$$$$$$$$$$a;
    int $deep$deep$deep;

    deepMethod$void();
    deepMethod();
    $deepMethod();
    deepMethod(1, null);
    deepMethod$int32_t$Object();
  }

  public static void deepMethod$void() {
    System.out.println("deepMethod$void()");
  }

  public static void deepMethod() {
    System.out.println("deepMethod()");
  }

  public static void $deepMethod() {
    System.out.println("$deepMethod()");
  }

  public static void deepMethod(int x, Object o) {
    System.out.println("deepMethod(int x, Object o)");
  }

  public static void deepMethod$int32_t$Object() {
    System.out.println("deepMethod$int32_t$Object()");
  }
  
  public static void deepMethod(int x, String o) {
    System.out.println("deepMethod(int x, String o)");
  }

}
