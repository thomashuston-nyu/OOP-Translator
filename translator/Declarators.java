package translator;

import xtc.tree.Node;

public class Declarators {

  private Declarator[] declarators;
  
  public Declarators(Node n) {
    if (!n.getName().equals("Declarators"))
      throw new RuntimeException("Invalid node type");
    int size = n.size();
    if (size == 0)
      declarators = null;
    else
      declarators = new Declarator[size];
    for (int i = 0; i < size; i++) {
      declarators[i] = new Declarator(n.getNode(i));
    }
  }
  
  public Declarator get(int index) {
    if (index >= 0 && index < declarators.length)
      return declarators[index];
    else
      return null;
  }
  
  public int size() {
    if (declarators != null)
      return declarators.length;
    else
      return 0;
  }
  
  public String toString() {
    return "";
  }

}