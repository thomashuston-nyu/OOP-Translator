package test;

import java.util.ArrayList;

public class Tests {
  boolean b;
  byte by;
  char c;
  double d;
  float f;
  int i;
  long l;
  short sh;
  
  Class cl;
  Object o;
  String s;

  int[] ai;
  String[] as;

  public Tests() {
  }

  public void testAdditiveExpression() {
    int x;
    x = 1 + 2;
    System.out.println(x);
    x = 1 + +2;
    System.out.println(x);
    x = -1 + -2;
    System.out.println(x);
    x = 1 - 2;
    System.out.println(x);
    x = 1 - +2;
    System.out.println(x);
    x = 1 - -2;
    System.out.println(x);
  }

  public void testMultiplicativeExpression() {
    int x;
    x = 4 * 2;
    System.out.println(x);
    x = 4 * +2;
    System.out.println(x);
    x = 4 * -2;
    System.out.println(x);
    x = 4 / 2;
    System.out.println(x);
    x = 4 / +2;
    System.out.println(x);
    x = 4 / -2;
    System.out.println(x);
  }

  public void testUnaryExpression() {
    int x = 5;
    ++x;
    System.out.println(x);
    x++;
    System.out.println(x);
    --x;
    System.out.println(x);
    x--;
    System.out.println(x);
  }

  public static void main(String[] args) {
    
  }
}
