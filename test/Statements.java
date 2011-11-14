public class Statements {

  private int x;
  public String[] s;

  public static void main(String[] args) {
    int[] a = new int[5];
    a[0] = 1;
    int[][] b = new int[2][4];
    b[0][1] = a[0];
    int[][][] c = new int[1][2][3];
    c[0][1][2] = b[0][1];
  }

}
