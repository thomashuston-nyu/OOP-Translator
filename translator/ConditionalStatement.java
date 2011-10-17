package translator;

import xtc.tree.Node;

public class ConditionalStatement {

  public ConditionalStatement(Node n) {
    if (!n.getName().equals("ConditionalStatement"))
      throw new RuntimeException("Invalid node type");
  }
  
  public String toString() {
    return "";
  }
  
}
