/**
 * Identifier?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BreakStatement extends Statement { 
  private String identifier;
  
  public BreakStatement(GNode n) {
    identifier = null;
    visit(n);
  }
  
  public void visitIdentifier(GNode n) {
    identifier = n.getString(0);
  }
}
