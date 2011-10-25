import demo.Sub;
import demo.Super;

public class Sample {
  
  public static void main(String[] args) {
    
    // Create superclass
    Super sup = new Super(1);
    
    // Create subclass
    Sub sub = new Sub(2);
    
    // Call printMessage
    sup.printMessage();
    
    // Call printMessage from superclass
    sub.printMessage();
    
    // Test toString methods
    System.out.println(sup);
    System.out.println(sub);

    // Print out the initial value of superclass's x
    int v = sup.getX();
    System.out.println(v);
    
    // Change the value of superclass's x
    sup.setX(5);
    v = sup.getX();
    
    // Check if v equals 5
    if (v < 5) {
      System.out.println("v < 5");
    } else if (v == 5) {
      System.out.println("v = 5");
    } else {
      System.out.println("v > 5");
    }

    // Change the value of subclass's x to 20
    sub.setXplusTen(10);
    int w = sub.getX();
    System.out.println(w);
    
    // If w is greater than 10, decrement it until w = 10
    while (w > 10)
      w--;
    System.out.print("w = ");
    System.out.println(w);

    // Print out 0-9 on the same line
    for (int i = 0; i < 10; i++)
      System.out.print(i);
    System.out.println();
  }

}
