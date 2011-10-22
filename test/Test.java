package test;

public class Test extends Sample {
    int test_num;
    public Test() {
        test_num = 0;
    }

    public Test(int test_num) {
        this.test_num = test_num;
    }

    // override test in superclass Sample
    int test() {
      return 1;
    }
    
    void exceptional() {
        try {
            throw new Exception();
        }
        catch(Exception e) {
            System.out.println("no big deal");
        }
    }

    public static void main (String [] args) {
        int x = 2;
        for(int i = 0; i < x; i++) System.out.println("i is "+ i);
        while (x < 3) x++;
        do { x++; } while(x < 4);
        if(true) System.out.println("x is " + x);
    }
}
