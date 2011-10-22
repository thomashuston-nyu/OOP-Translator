/**
 * Expression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ExpressionStatement extends Statement {
  
  private Expression expression;
  
  public ExpressionStatement(GNode n) {
    visit(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
}