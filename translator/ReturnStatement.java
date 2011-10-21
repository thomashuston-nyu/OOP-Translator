/**
 * Expression?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class ReturnStatement extends Statement {   
  private Expression expression;
  
  public ReturnStatement(GNode n) {
    expression = null;
    visit(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
}
