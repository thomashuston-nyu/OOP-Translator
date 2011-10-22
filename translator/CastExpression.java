/**
 * Type UnaryExpressionNotPlusMinus
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CastExpression extends TranslationVisitor {
  private Type type;

  public CastExpression(GNode n) {
    visit(n);
  }

  public void visitType(GNode n) {
    type = new Type(n);
  }
}
