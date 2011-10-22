/**
 * Identifier?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class ContinueStatement extends Statement {
  
  private String value;
  
  public ContinueStatement(GNode n) {
    if (n.size() == 0)
      value = null;
    else
      value = n.getString(0);
  }
  
}