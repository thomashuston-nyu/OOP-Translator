/**
 * PrimitiveType Dimensions? UnaryExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BasicCastExpression extends Expression {
  private PrimitiveType primitiveType;
  private boolean isArray;
  private UnaryExpression unaryExpression;

  public BasicCastExpression(GNode n) {
    isArray = false;
    visit(n);
  }

  public void visitPrimitiveType(GNode n) {
    primitiveType = new PrimitiveType(n);
  }

  public void visitDimensions(GNode n) {
    isArray = true;
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
}
