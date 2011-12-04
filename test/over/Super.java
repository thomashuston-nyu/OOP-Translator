package over;

public class Super {

  public static void main(String[] args) {
    x();
    x(1, new Super());
    x(new SubSub(), new Sub());
    x(new SubSubSub(), new Super(), new SubSub2());
    x(new SubSubSub(), null);
  }

}
