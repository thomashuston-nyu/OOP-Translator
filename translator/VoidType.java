package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class VoidType extends Type {
  
  public VoidType(GNode n) {
    super(n);
  }
  
  public String getType() {
    return "void";
  }
}
