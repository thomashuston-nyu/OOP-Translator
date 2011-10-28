/**
 * Identifier?
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BreakStatement extends Statement implements Translatable { 

  private String identifier;
  
  public BreakStatement(GNode n) {
    identifier = null;
    visit(n);
  }
  
  public void visitIdentifier(GNode n) {
    identifier = n.getString(0);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
