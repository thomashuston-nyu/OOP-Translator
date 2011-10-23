/**
 * UnaryExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class LogicalNegationExpression extends Expression {
  private UnaryExpression unaryExpression;

  public LogicalNegationExpression(GNode n) {
    visit(n);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
}
