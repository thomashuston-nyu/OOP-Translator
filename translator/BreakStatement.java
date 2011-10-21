/**
 * Identifier?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class BreakStatement extends TranslationVisitor { 
  private String identifier;
  
  public BreakStatement(GNode n) {
    identifier = null;
    visit(n);
  }
  
  public void visitIdentifier(Gnode n) {
    identifier = n.getString(0);
  }
}
