package translator;

import xtc.tree.Node;

public class Implementation {

  private Type implementation;
  
  public Implementation(Node n) {
    if (!n.getName().equals("Implementation"))
      throw new RuntimeException("Invalid node type");
    this.implementation = new Type(n.getNode(0));
  }
  
  public String toString() {
    return "";
  }

}