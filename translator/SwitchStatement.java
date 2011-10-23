/**
 * Expression SwitchClause*
 */

package translator;

import java.util.List;
import java.util.ArrayList;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class SwitchStatement extends Statement implements Translatable {
  
  private Expression expression;
  private List<SwitchClause> switchClause;
 
  public SwitchStatement(GNode n) {
    switchClause = new ArrayList<SwitchClause>();
    visit(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

  public void visitSwitchClause(GNode n) {
    switchClause.add(new SwitchClause(n));
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}