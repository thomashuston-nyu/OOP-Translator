package translator;

import xtc.tree.Node;

public class Declarator {
  
  private String[] declarators;
  
  public Declarator(Node n) {
    if (!n.getName().equals("Declarator"))
      throw new RuntimeException("Invalid node type");
    this.declarators = new String[3];
    for (int i = 0; i < 3; i++) {
      if (n.get(i) != null)
        this.declarators[i] = n.getString(i);
      else
        this.declarators[i] = null;
    }
  }
  
  public String toString() {
    return declarators[0];
  }
  
}