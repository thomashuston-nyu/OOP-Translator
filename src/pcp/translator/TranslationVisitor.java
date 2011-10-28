package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.Node;
import xtc.tree.Visitor;

public class TranslationVisitor extends Visitor {
  
  public TranslationVisitor() {
    
  }
  
  public String getIndent(int indent) {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      s.append("  ");
    }
    return s.toString();
  }
  
  public void visit(Node n) {
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }
  }
  
}