public class Demo {
    public static void main (String [] args) {
        try {
            if (args.length < 1)
                throw new RuntimeException();
            boolean input;
            if (args[0].equals("true"))
                input = true;
            else
                input = false;

            boolean[][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][] bool = {{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{ input }}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}};
            System.out.println(bool[0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0]);
        } catch (RuntimeException e) {
            System.out.println("exceptions work too!");
        } finally {
            System.out.println("finally!");
        }

      Overloaded.m(null, null, null, null, null, new Super());
    }
}

class Super {}

class Sub extends Super {}

class Deep1 extends Sub {}

class Deep2 extends Sub {}

class CrazyDeep extends Deep2 {}

class Overloaded {

  public static void m(Object o, String s, Super sup, Deep2 d2, CrazyDeep cd, Sub sub) {
    System.out.println("m(Object, String, Super, Deep2, CrazyDeep, Sub)");
  }

  public static void m(String s, Sub sub, Deep1 d1, Super s2, Object o, Super sup) {
    System.out.println("m(String, Sub, Deep1, Sub, Object, Super)");
  }

  public static void m(String s, Sub sub, Deep1 d1, Super s2, Sub sub1, Super sup) {
    System.out.println("m(String, Sub, Deep1, Sub, Sub, Super)");
  }

}
