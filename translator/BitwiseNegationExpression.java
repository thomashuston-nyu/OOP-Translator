/**
 * UnaryExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class BitwiseNegationExpression extends TranslationVisitor {
  private UnaryExpression unaryExpression;

  public BitwiseNegationExpression(GNode n) {
    visit(n);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
}
