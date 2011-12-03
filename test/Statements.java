public class Statements {

  public static void main(String[] args) {
    testBreakStatement();
    testConditionalStatement();
    testContinueStatement();
    testDoWhileStatement();
    testForStatement();
    testReturnStatement();
    testSwitchStatement();
    testThrowStatement();
    testTryCatchFinallyStatement();
    testWhileStatement();
  }

  public static void testBreakStatement() {
    System.out.println("Break Statements");
    
    while (true)
      break;

    for (int i = 0; i < 5; i++) {
      if (i == 2)
        break;
      System.out.println(i);
    }
  }

  public static void testConditionalStatement() {
    System.out.println("\nConditional Statements");
    
    int x = 1;
    int y = 2;
    
    if (x == y)
      System.out.println("x == y");
    else if (x > y)
      System.out.println("x > y");
    else
      System.out.println("x < y");
  }

  public static void testContinueStatement() {
    System.out.println("\nContinue Statements");

    for (int i = 0; i < 5; i++) {
      if (i % 2 == 0)
        continue;
      System.out.println(i);
    }
  }

  public static void testDoWhileStatement() {
    System.out.println("\nDo/While Statements");
    
    int x = 0;
    do {
      System.out.println(x);
      x++;
    } while (x < 3);
  }

  public static void testForStatement() {
    System.out.println("\nFor Statements");

    for (int i = 0; i < 3; i++)
      System.out.println(i);
  }

  public static void testReturnStatement() {
    System.out.println("\nReturn Statements");

    System.out.println(returnHelper());
  }

  public static int returnHelper() {
    return 3;
  }

  public static void testSwitchStatement() {
    System.out.println("\nSwitch Statements");

    int x = 2;
    switch (x) {
      case 1:
        System.out.println("case 1");
        break;
      case 2:
        System.out.println("case 2");
      case 3:
        System.out.println("case 3");
        break;
      default:
        System.out.println("default");
        break;
    }
  }

  public static void testThrowStatement() {
    System.out.println("\nThrow Statements");

    try {
      throw new NullPointerException();
    } catch (NullPointerException e) {
      System.out.println("Exception caught");
    }
  }

  public static void testTryCatchFinallyStatement() {
    System.out.println("\nTry/Catch/Finally Statements");

    int[] x = {1, 2, 3, 4, 5};
    try {
      int y = x[5];
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Exception caught");
    } finally {
      System.out.println("Finally executed");
    }
  }

  public static void testWhileStatement() {
    System.out.println("\nWhile Statements");

    int x = 0;
    while (x < 3) {
      System.out.println(x);
      x++;
    }
  }

}
