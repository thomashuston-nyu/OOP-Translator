public class ConstructorOverloading{
  public static void main(String args[]){
    Rectangle rectangle1=new Rectangle(2,4);
    int areaInFirstConstructor=rectangle1.first();
    System.out.println(" The area of a rectangle in first constructor is :  " + areaInFirstConstructor);
    Rectangle rectangle2=new Rectangle(5);
    int areaInSecondConstructor=rectangle2.second();
    System.out.println(" The area of a rectangle in first constructor is :  " + areaInSecondConstructor);
    Rectangle rectangle3=new Rectangle(2.0f);
    float areaInThirdConstructor=rectangle3.third();
    System.out.println(" The area of a rectangle in first constructor is :  " + areaInThirdConstructor);
    Rectangle rectangle4=new Rectangle(3.0f,2.0f);
    float areaInFourthConstructor=rectangle4.fourth();
    System.out.println(" The area of a rectangle in first constructor is :  " + areaInFourthConstructor);
    }
}

class Rectangle{
  int l, b;
  float p, q;
  public Rectangle(int x, int y){
    l = x;
    b = y;
    }
  public int first(){ 
    return(l * b);
    }
  public Rectangle(int x){
    l = x;
    b = x;
    }
  public int second(){
    return(l * b);
    }
  public Rectangle(float x){
    p = x;
    q = x;
    }
  public float third(){
    return(p * q);
    }
  public Rectangle(float x, float y){
    p = x;
    q = y;
    }
  public float fourth(){
    return(p * q);
    }
}
