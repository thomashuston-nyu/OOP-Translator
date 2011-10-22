/**
 * Expression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ConcreteDimension extends TranslationVisitor {
  private Expression expression;

  public ConcreteDimension(GNode n) {
    visit(n);
  }

  public visitExpression(GNode n) {
    expression = new Expression(n);
  }
}
