package translator;

import xtc.tree.Node;

public class Type {
  
  private String type;
  private boolean isArray;
  
  public Type(Node n) {
    this.type = n.getNode(0).getString(0);
    this.isArray = n.get(1) == null;
  }
  
  public String toString() {
    if (isArray)
      return type + "[]";
    else
      return type;
  }
  
}