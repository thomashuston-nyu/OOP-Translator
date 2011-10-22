package test;

import java.lang.StringBuilder;
import java.util.*;

public class Sample extends Object {
    
  private String string1;
  public Object o1;
  
  public Sample() {
    int[] test = new int[5];
    test[3] = 2;
    System.out.println(test[3]);
  }
  
  int test() {
    return 0;
  }

  void test2() {
    if (1 == 1)
      System.out.println("1");
    else if (2 == 2)
      System.out.println("2");
    else
      System.out.println("3");
  }

  String test3() throws Exception {
    return "";
  }

  Object test4() {
    return new Object();
  }

  public final static String[] test2(int x, int y, Object[] z) {
    return new String[3];
  }

  public int compareTo(Object o) {
    return 0;
  }
  
  private String hey;

  public static void main(String[] args) {
    
  }

}
