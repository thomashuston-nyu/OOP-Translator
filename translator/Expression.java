/**
 * (ConditionalExpression AssignmentOperator Expression)/
 * yyValue:ConditionalExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Expression extends PrimaryExpression implements Translatable {
  
  private Expression left;
  private String operator;
  private Expression right;
  
  public Expression() {
    // Here so subclasses don't complain
  }
  
  public Expression(GNode n) {
    left = null;
    right = null;
    operator = n.getString(1);
    visit(n);
  }

  public void visitConditionalExpression(GNode n) {
    setExpression(new ConditionalExpression(n));
  }
  
  public void visitExpression(GNode n) {
    setExpression(new Expression(n));
  }
  
  public void visitPrimaryIdentifier(GNode n) {
    setExpression(new PrimaryIdentifier(n));
  }
  
  private void setExpression(Expression e) {
    if (left == null)
      left = e;
    else
      right = e;
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    if (left != null && right != null) {
      StringBuilder s = new StringBuilder();
      s.append(left.getCC(indent, className, variables) + " " + operator +
               " " + right.getCC(indent, className, variables));
      return s.toString();
    }
    return "";
  }
  
}
