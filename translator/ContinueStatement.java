/**
 * Identifier?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ContinueStatement extends Statement implements Translatable {
  
  private String value;
  
  public ContinueStatement(GNode n) {
    if (n.size() == 0)
      value = null;
    else
      value = n.getString(0);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}