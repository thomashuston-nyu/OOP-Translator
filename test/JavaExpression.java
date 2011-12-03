public class JavaExpression {

  public static void main(String[] args) {
    testAdditiveExpression();
    testArrayInitializerExpression();
    testBitwiseExpression();
    // testCallExpression();
    // testCastExpression();
    // testConditionalExpression();
    // testEqualityExpression();
    // testInstanceOfExpression();
    // testLiteralExpression();
    // testLogicalExpression();
    // testMultiplicativeExpression();
    // testNewArrayExpression();
    // testNewClassExpression();
    // testPostfixExpression();
    // testPrimaryIdentifier();
    // testRelationalExpression();
    // testSelectionExpression();
    // testSubscriptExpression();
    // testSuperExpression();
    // testThisExpression();
    // testUnaryExpression();
    // testVariableInitializer();
  }

  public static void testAdditiveExpression() {
    System.out.println("Additive Expressions");
    
    int i = 1;
    short s = 2;
    double d = 3.0;
    String str = "asdf";

    System.out.println(i + s);
    System.out.println(s + d);
    System.out.println(d + i);
    System.out.println(d + str);
    System.out.println(str + i);
    System.out.println(s + s);
  }

  public static void testArrayInitializerExpression() {
    System.out.println("\nArray Initializer Expressions");

    int[] a = {1, 2, 3, 4, 5};
    String[] s = {"a", "b", "c", "d", "e"};
    Object b = new Object();
    Object[] o = {"a", b, null};

    for (int i = 0; i < a.length; i++)
      System.out.print(a[i] + " ");
    System.out.println();
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

}
