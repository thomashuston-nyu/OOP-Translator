package translator;

import xtc.tree.Node;

public class Type {
  
  private String type;
  private boolean isArray;
  
  public Type(Node n) {
    if (n.getName() == "VoidType") {
      this.type = "void";
      this.isArray = false;
    } else if (n.getName() == "Type") {
      this.type = n.getNode(0).getString(0);
      this.isArray = n.get(1) != null;
    } else {
      throw new RuntimeException("Invalid node type");
    }
  }
  
  public Type(String type, boolean isArray) {
    this.type = type;
    this.isArray = isArray;
  }
  
  public String toString() {
    if (isArray)
      return type + "[]";
    else
      return type;
  }
  
}