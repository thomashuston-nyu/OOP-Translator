/**
 * (EqualityExpression EqualityOperator InstanceOfExpression)/
 * yyValue:InstanceOfExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class EqualityExpression extends Expression implements Translatable {
    
  private Expression left;
  private String operator;
  private Expression right;

  public EqualityExpression(GNode n) {
    operator = n.getString(1);
    visit(n);
  }

  public void visitAdditiveExpression(GNode n) {
    setExpression(new AdditiveExpression(n));
  }

  public void visitBooleanLiteral(GNode n) {
    setExpression(new BooleanLiteral(n));
  }

  public void visitEqualityExpression(GNode n) {
    setExpression(new EqualityExpression(n));
  }

  public void visitIntegerLiteral(GNode n) {
    setExpression(new IntegerLiteral(n));
  }

  public void visitInstanceOfExpression(GNode n) {
    setExpression(new InstanceOfExpression(n));
  }

  public void visitMultiplicativeExpression(GNode n) {
    setExpression(new MultiplicativeExpression(n));
  }

  public void visitPrimaryIdentifier(GNode n) {
    setExpression(new PrimaryIdentifier(n));
  }

  public void setExpression(Expression expression) {
    if (left == null)
      left = expression;
    else
      right = expression;
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    s.append(left.getCC(indent, className, variables) + " " + operator + " " + right.getCC(indent, className, variables));
    return s.toString();
  }

}
