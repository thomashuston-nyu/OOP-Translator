public class Static {

  static int count = 0;

  public Static() {
    Static.count++;
  }

  public static void main(String[] args) {
    Static s1 = new Static();
    Static s2 = new Static();
    System.out.println(count);
  }

}
