public class Statements {

  private static int x = 5;

  public static void main(String[] args) {

    int x = 1 < 3 ? 2 : 5;
    int y = true || false;
    int z = true == false;
    int a = x = 5;
    boolean y = (x instanceof Integer);
    int b = 1 + 5;
    int c = 5 | 3;
    int d = this.a;
    int e = x++;
    int f = -e;
    int g = a && b;
    int h = b || c;
    int i = !a;
    int[] j = new int[5];
    int k[][] = new int[b][5];
    int[] l = {0, 1, 2};
    b++;
    --b;
    System.out.in.println(x);
    boolean m = x <= y;
    x << 5;
    x >>> 5;
    this.x = x;
    this.x.getName();
  }

}
