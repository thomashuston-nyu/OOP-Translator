package translator;

import xtc.tree.Node;

public class Modifiers {

  private Modifier[] modifiers;
  
  public Modifiers(Node n) {
    if (!n.getName().equals("Modifiers"))
      throw new RuntimeException("Invalid node type");
    int size = n.size();
    if (size == 0)
      modifiers = null;
    else
      modifiers = new Modifier[size];
    for (int i = 0; i < size; i++) {
      modifiers[i] = new Modifier(n.getNode(i));
    }
  }
  
  public Modifier get(int index) {
    if (index >= 0 && index < modifiers.length)
      return modifiers[index];
    else
      return null;
  }
  
  public int size() {
    if (modifiers != null)
      return modifiers.length;
    else
      return 0;
  }
  
  public String toString() {
    
    return "";
  }

}