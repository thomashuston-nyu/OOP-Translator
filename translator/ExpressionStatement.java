/**
 * Expression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ExpressionStatement extends Statement implements Translatable {
  
  private Expression expression;
  
  public ExpressionStatement(GNode n) {
    visit(n);
  }
  
  public void visitCallExpression(GNode n) {
    expression = new CallExpression(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public String getCC(String className, int indent) {
    return expression.getCC(className,indent);
  }
  
}