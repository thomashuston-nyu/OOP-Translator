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
  private String unaryExpression;

  public UnaryExpression(GNode n) {
    unaryExpression = null;
    visit(n);
  }

  public void visitSymbol(GNode n) {
    unaryExpression = n.getString(0);
  }
}
