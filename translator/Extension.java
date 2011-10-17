package translator;

import xtc.tree.Node;

public class Extension {

  private Type extension;
  
  public Extension(Node n) {
    if (!n.getName().equals("Extension"))
      throw new RuntimeException("Invalid node type");
    this.extension = new Type(n.getNode(0));
  }
  
  public String toString() {
    return "";
  }

}