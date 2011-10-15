package translator;

import xtc.tree.Node;

public class PrimitiveType extends Type {

  private String type;
  private boolean isArray;
  
  public PrimitiveType(Node n) {
    this.type = n.getString(0);
    this.isArray = n.get(1) == null;
  }
  
  public String toString() {
    if (isArray)
      return type + "[]";
    else
      return type;
  }

}