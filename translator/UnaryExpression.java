/**
 * ("+":Symbol UnaryExpression)/
 * ("-":Symbol UnaryExpression)/
 * ("++":Symbol UnaryExpression)/
 * ("--":Symbol UnaryExpression)/
 * yyValue:BitwiseNegationExpression/
 * yyValue:LogicalNegationExpression/
 * yyValue:BasicCastExpression/
 * yyValue:CastExpression/
 * yyValue:PostfixExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class UnaryExpression extends TranslationVisitor {
  private UnaryExpression unaryExpression;
  private String symbol;

  public UnaryExpression(GNode n) {
    unaryExpression = null;
    symbol = null;
    visit(n);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }

  public void visitSymbol(GNode n) {
    symbol = n.getString(0);
  }
}
