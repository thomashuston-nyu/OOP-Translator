public class OccurrencesInArray{
  public static void main(String[] args){
    int num= 100;
    String[] elements = new String[num];
    int a;
    int k;
    for(int i = 0; i < num; i++){
      elements[i] = (i * 15) + "";
      }
    for(int i = 0; i < elements.length; i++){
      a = 0;
      k = 1;
      for(int j = 0; j < elements.length; j++){
        if(j >= i){
          if(elements[i].equals(elements[j]) && j != i){
            k++;
            }
          }
        else if(elements[i].equals(elements[j])){
          a = 1;
          }
        }
      if(a != 1){
        System.out.println("Occurrence of \'" + elements[i] + "\' : " + k);
        }
      }
    }
}
