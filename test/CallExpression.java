public class CallExpression {
  
  private int x;

  public CallExpression() {
    x = 5;
  }

  public CallExpression(int x) {
    this.x = x;
  }

  public void voidM() {
  }

  public int intM() {
    return x;
  }

  private short shortM() {
    return 1;
  }

  private byte byteM() {
    return 2;
  }

  protected String stringM() {
    return "";
  }

  protected boolean booleanM() {
    return true;
  }

  long longM() {
    return 3;
  }

  char charM() {
    return 'a';
  }

  public static float floatM() {
    return (float)4.0;
  }

  public static void main(String[] args) {
    CallExpression e = new CallExpression();
    e.voidM();
    System.out.println(e.intM());
    System.out.println(e.shortM());
    System.out.println(e.byteM());
    System.out.println(e.stringM());
    System.out.println(e.booleanM());
    System.out.println(e.longM());
    System.out.println(e.charM());
    System.out.println(e.floatM());
    System.out.println(floatM());
    System.out.println(CallExpression.floatM());
  }

}
