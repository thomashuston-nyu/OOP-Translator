/**
 * (AdditiveExpression AdditiveOperator MultiplicativeExpression)/
 * yyValue:MultiplicativeExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class AdditiveExpression extends Expression implements Translatable {

  private Expression left;
  private String operator;
  private Expression right;

  public AdditiveExpression(GNode n) {
    left = null;
    right = null;
    operator = n.getString(1);
    visit(n);
  }
  
  public void visitAdditiveExpression(GNode n) {
    setExpression(new AdditiveExpression(n));
  }
  
  public void visitCallExpression(GNode n) {
    setExpression(new CallExpression(n));
  }
  
  public void visitMultiplicativeExpression(GNode n) {
    setExpression(new MultiplicativeExpression(n));
  }
  
  public void visitStringLiteral(GNode n) {
    setExpression(new StringLiteral(n));
  }
  
  public void visitUnaryExpression(GNode n) {
    setExpression(new UnaryExpression(n));
  }
  
  private void setExpression(Expression e) {
    if (left == null)
      left = e;
    else
      right = e;
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    if (left != null && right != null) {
      s.append(left.getCC(indent, className, variables) + " " 
               + operator + " " + right.getCC(indent, className, variables));  
    }
    return s.toString();
  }

}
