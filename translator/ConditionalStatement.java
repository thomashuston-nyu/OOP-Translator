package translator;

import xtc.tree.GNode;
import xtc.tree.Node;

public class ConditionalStatement {

  private GNode n;
  
  public ConditionalStatement(GNode n) {
    if (!n.getName().equals("ConditionalStatement"))
      throw new RuntimeException("Invalid node type");
    this.n = n;
  }
}
