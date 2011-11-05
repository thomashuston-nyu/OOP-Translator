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
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class UnaryExpression extends Expression implements Translatable {

  private UnaryExpression unaryExpression;
  private String symbol;

  public UnaryExpression(GNode n) {
    unaryExpression = null;
    symbol = n.getString(0);
    visit(n);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    if (unaryExpression != null)
      return symbol + unaryExpression.getCC(indent, className, variables);
    else
      return symbol;
  }
  
}
