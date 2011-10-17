package translator;

import xtc.tree.Node;

public class Modifier {
  
  private String modifier;
  
  public Modifier(Node n) {
    if (!n.getName().equals("Modifier"))
      throw new RuntimeException("Invalid node type");
    this.modifier = n.getString(0);
  }
  
  public boolean equals(Object o) {
    if (o instanceof String)
      return modifier.equals((String)o);
    return false;
  }
  
  public String toString() {
    return modifier;
  }

}