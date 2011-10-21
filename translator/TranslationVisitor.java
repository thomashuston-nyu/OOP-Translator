package translator;

import xtc.tree.Node;
import xtc.tree.Visitor;

public class TranslationVisitor extends Visitor {
  
  public TranslationVisitor() {
    
  }
  
  public void visit(Node n) {
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }
  }
  
}