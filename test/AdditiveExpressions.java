public class AdditiveExpressions {

  public static void main(String[] args) {

    byte b1 = 1, b2 = 2;
    short s1 = 1, s2 = 2;
    int i1 = 1, i2 = 2;
    long l1 = 1, l2 = 2;

    float f1 = 1.0, f2 = 2.0;
    double d1 = 1.0, d2 = 2.0;

    boolean n1 = true, n2 = false;

    char c1 = 'a', c2 = 'b';
    String t1 = "A", t2 = "B";

    Class c;

    c = (b1 + b1).getClass();
    System.out.println("byte + byte:\t" + c.getName());
    
    c = (b1 + s1).getClass();
    System.out.println("byte + short:\t" + c.getName());

    c = (b1 + i1).getClass();
    System.out.println("byte + int:\t" + c.getName());

    c = (b1 + l1).getClass();
    System.out.println("byte + long:\t" + c.getName());

    c = (b1 + f1).getClass();
    System.out.println("byte + float:\t" + c.getName());

    c = (b1 + d1).getClass();
    System.out.println("byte + double:\t" + c.getName());

    c = (s1 + s1).getClass();
    System.out.println("short + short:\t" + c.getName());

    c = (s1 + i1).getClass();
    System.out.println("short + int:\t" + c.getName());

    c = (s1 + l1).getClass();
    System.out.println("short + long:\t" + c.getName());

    c = (s1 + f1).getClass();
    System.out.println("short + float:\t" + c.getName());

    c = (s1 + d1).getClass();
    System.out.println("short + double:\t" + c.getName());

    c = (i1 + i1).getClass();
    System.out.println("int + int:\t" + c.getName());

    c = (i1 + l1).getClass();
    System.out.println("int + long:\t" + c.getName());

    c = (i1 + f1).getClass();
    System.out.println("int + float:\t" + c.getName());

    c = (i1 + d1).getClass();
    System.out.println("int + double:\t" + c.getName());

    c = (l1 + l1).getClass();
    System.out.println("long + long:\t" + c.getName());

    c = (l1 + f1).getClass();
    System.out.println("long + float:\t" + c.getName());

    c = (l1 + d1).getClass();
    System.out.println("long + double:\t" + c.getName());

    c = (f1 + f1).getClass();
    System.out.println("float + float:\t" + c.getName());

    c = (f1 + d1).getClass();
    System.out.println("float + double:\t" + c.getName());

    c = (d1 + d1).getClass();
    System.out.println("double + double:\t" + c.getName());

    c = (c1 + c2).getClass();
    System.out.println("char + char:\t" + c.getName());

    c = (c1 + b1).getClass();
    System.out.println("char + char:\t" + c.getName());

    c = (c1 + s1).getClass();
    System.out.println("char + char:\t" + c.getName());

    c = (c1 + i1).getClass();
    System.out.println("char + char:\t" + c.getName());

    c = (c1 + l1).getClass();
    System.out.println("char + char:\t" + c.getName());

    c = (c1 + f1).getClass();
    System.out.println("char + char:\t" + c.getName());

    c = (c1 + d1).getClass();
    System.out.println("char + char:\t" + c.getName());

    c = (c1 + t1).getClass();
    System.out.println("char + String:\t" + c.getName());

    c = (b1 + t1).getClass();
    c = (s1 + t1).getClass();
    c = (i1 + t1).getClass();
    c = (l1 + t1).getClass();
    c = (f1 + t1).getClass();
    c = (d1 + t1).getClass();
  }

}
