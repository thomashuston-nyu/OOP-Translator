/**
 * (ShiftExpression ShiftOperator AdditiveExpression)/
 * yyValue:AdditiveExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ShiftExpression extends Expression {
  private ShiftExpression shiftExpression;
  private String shiftOperator;
  private AdditiveExpression additiveExpression;

  public ShiftExpression(GNode n) {
    shiftOperator = null;
    visit(n);
  }

  public void visitShiftExpression(GNode n) {
    shiftExpression = new ShiftExpression(n);
  }

  public void visitSymbol(GNode n) {
    shiftOperator = n.getString(0);
  }

  public void visitAdditiveExpression(GNode n) {
    additiveExpression = new AdditiveExpression(n);
  }
}
