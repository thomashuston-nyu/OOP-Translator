public class Statements {

  private static int x = 5;
  private final int m = 8;
  private String[] s = new String[5];
  Object o[] = {"HEY"};
  private String ss[] = new String[5], tt[] = new String[4];
  private int yy = 1, zz = 2, xx = 3;

  public static void main(String[] args) {

    int x = 1 < 3 ? 2 : 5;
    int y = 5;
    int z = 8;
    int a = x = 5;
    int b = 1 + 5;
    int c = 5 | 3;
    int d = a;
    int e = x++;
    int f = -e;
    int[] j = new int[5];
    int k[][] = new int[b][5];
    int[] l = {0, 1, 2};
    b++;
    --b;
    System.out.println(x);
    boolean m = x <= y;
    Statements.x = x;
    switch(x) {
      case 1:
        x = 1;
        x = 2;
        break;
      case 2:
        x = 2;
      default:
      case 3:
        x = 3;
        break;
      case 4:
        x = 7;
        break;
      case 5:
        x = 5;
    }
  }

}
