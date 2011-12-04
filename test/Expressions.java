public class Expressions {

  public static void main(String[] args) {
    testAdditiveExpression();
    testArrayExpression();
    testBitwiseExpression();
    testCastExpression();
    testConditionalExpression();
    testExpression();
    testInstanceOfExpression();
    testLiteralExpression();
    testLogicalExpression();
    testMultiplicativeExpression();
    testPostfixExpression();
    testRelationalExpression();
  }

  public static void testAdditiveExpression() {
    System.out.println("Additive Expressions");
    
    int i = 1;
    short s = 2;
    double d = 3.1;
    String str = "asdf";

    System.out.println(i + s);
    System.out.println(s + d);
    System.out.println(d + i);
    System.out.println(d + str);
    System.out.println(str + i);
    System.out.println(s + s);
    System.out.println(i + s + d + str);
  }

  public static void testArrayExpression() {
    System.out.println("\nArray Expressions");

    int[] a = {1, 2, 3, 4, 5};
    int[][] b = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}};
    int[][] c = {{1, 2, 3}, {4, 5, 6, 7}};
    String[] s = {"a", "b", "c", "d", "e"};
    Object n = new Object();
    Object[] o = {"a", n, null};

    int[][][][][][][][][][] deepArray = new int[1][2][3][4][5][6][7][8][9][10];
    deepArray[0][1][1][2][1][3][2][6][1][5] = 9;
    int p = deepArray[0][1][1][2][1][3][2][6][1][5];
    
    System.out.println(deepArray[0][1][1][2][1][3][2][6][1][5]);
    System.out.println(deepArray[0][1][1][2][1][3][2][6][1][6]);
    System.out.println(p);

    for (int i = 0; i < a.length; i++)
      System.out.print(a[i] + " ");
    System.out.println();

    for (int i = 0; i < b.length; i++) {
      for (int j = 0; j < b[i].length; j++)
        System.out.print(b[i][j] + " ");
      System.out.println();
    }
    
    for (int i = 0; i < c.length; i++) {
      for (int j = 0; j < c[i].length; j++)
        System.out.print(c[i][j] + " ");
      System.out.println();
    }

    for (int i = 0; i < s.length; i++)
      System.out.print(s[i] + " ");
    System.out.println();
    
    for (int i = 0; i < o.length; i++)
      System.out.print(o[i] + " ");
    System.out.println();
  }

  public static void testBitwiseExpression() {
    System.out.println("\nBitwise Expressions");

    byte a = 1;
    short b = 2;
    int c = 3;
    long d = 4l;

    System.out.println(b & c);
    System.out.println(d & a);
    System.out.println(a | c);
    System.out.println(b | a);
    System.out.println(c ^ d);
    System.out.println(b ^ b);
    System.out.println(~a);
    System.out.println(~d);
    System.out.println(b << 2);
    System.out.println(c >> 3);
  }

  public static void testCastExpression() {
    System.out.println("\nCast Expressions");
    
    int a = 5;
    long b = 2l;
    double c = 8.0;
    String s = "asdf";
    
    System.out.println((int)a);
    System.out.println((int)b);
    System.out.println((int)c);
    System.out.println((Object)s);
  }

  public static void testConditionalExpression() {
    System.out.println("\nConditional Expressions");
    
    int a = 2;
    int b = 3;
    boolean c = true;

    System.out.println(c ? a : b);
  }

  public static void testExpression() {
    System.out.println("\nExpressions");
    
    int a, b, c, d, e;

    a = 8;
    b = 5;
    System.out.println(a += b);
    a = 8;
    b = 5;
    System.out.println(a -= b);
    a = 8;
    b = 5;
    System.out.println(a *= b);
    a = 8;
    b = 5;
    System.out.println(a /= b);
    a = 8;
    b = 5;
    System.out.println(a %= b);
    a = 8;
    b = 5;
    System.out.println(a &= b);
    a = 8;
    b = 5;
    System.out.println(a |= b);
    a = 8;
    b = 5;
    System.out.println(a ^= b);
    a = 8;
    b = 5;
    System.out.println(a >>= b);
    a = 8;
    b = 5;
    System.out.println(a <<= b);
    e = -3;
    System.out.println(c = d = e);
    e = +4;
    System.out.println(c = d = e);
  }

  public static void testInstanceOfExpression() {
    System.out.println("\ninstanceof Expressions");

    Object o1 = new Object();
    String s = "a";
    Object o2 = s;

    System.out.println(o1 instanceof Object);
    System.out.println(o1 instanceof String);
    System.out.println(s instanceof Object);
    System.out.println(s instanceof String);
    System.out.println(o2 instanceof Object);
    System.out.println(o2 instanceof String);
  }

  public static void testLiteralExpression() {
    System.out.println("\nLiteral Expressions");

    byte a = 1;
    short b = 2;
    int c = 3;
    long d = 4l;
    long e = 4L;
    float f = 5.0f;
    float g = 5.1F;
    double h = 6.0;
    double i = 6.1;
    char j = 'a';
    char k = 98;
    boolean l = true;
    boolean m = false;
    String n = "asdf";
    Object o = null;

    System.out.println(a);
    System.out.println(b);
    System.out.println(c);
    System.out.println(d);
    System.out.println(e);
    System.out.println(f);
    System.out.println(g);
    System.out.println(h);
    System.out.println(i);
    System.out.println(j);
    System.out.println(k);
    System.out.println(l);
    System.out.println(m);
    System.out.println(n);
    System.out.println(o);
  }

  public static void testLogicalExpression() {
    System.out.println("\nLogical Expressions");

    boolean a = true;
    boolean b = false;

    System.out.println(a && b);
    System.out.println(a || b);
    System.out.println(!a);
    System.out.println(!b);
  }

  public static void testMultiplicativeExpression() {
    System.out.println("\nMultiplicative Expressions");

    int a = 2;
    short b = 3;
    long c = 4l;
    double d = 5.0;

    System.out.println(a * b);
    System.out.println(b * c);
    System.out.println(c * d);
    System.out.println(a * b * c * d);
  }

  public static void testPostfixExpression() {
    System.out.println("\nPostfix Expressions");

    int a = 3;

    System.out.println(a++);
    System.out.println(a--);
  }
  
  public static void testRelationalExpression() {
    System.out.println("\nRelational Expressions");

    int a = 97;
    short b = 97;
    long c = 97l;
    char d = 'a';
    byte e = 97;
    double f = 97.0;
    int g = 98;
    Object o1 = new Object();
    Object o2 = o1;
    Object o3 = new Object();

    System.out.println(a == b);
    System.out.println(b == c);
    System.out.println(c == d);
    System.out.println(d == e);
    System.out.println(e == f);
    System.out.println(f == g);
    System.out.println(o1 == o2);
    System.out.println(o2 == o3);

    a = 1;
    b = 2;
    c = 3l;
    d = 'a';
    e = 5;
    f = 6.1;
    System.out.println(a < b);
    System.out.println(b <= c);
    System.out.println(c > d);
    System.out.println(e >= f);
  }

}
