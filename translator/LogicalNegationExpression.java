/**
 * UnaryExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class LogicalNegationExpression extends TranslationVisitor {
  private UnaryExpression unaryExpression;

  public LogicalNegationExpression(GNode n) {
    visit(n);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
}
