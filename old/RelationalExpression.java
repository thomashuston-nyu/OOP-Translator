/**
 * (RelationalExpression RelationalOperator ShiftExpression)/
 * yyValue:ShiftExpression
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class RelationalExpression extends Expression implements Translatable {

  private Expression left;
  private String operator;
  private Expression right;
  private Statement statement;

  public RelationalExpression(GNode n) {
    operator = n.getString(1);
    visit(n);
  }

  public void visitExpressionStatement(GNode n) {
    statement = new ExpressionStatement(n);
  }

  public void visitIntegerLiteral(GNode n) {
    setExpression(new IntegerLiteral(n));
  }

  public void visitPrimaryIdentifier(GNode n) {
    setExpression(new PrimaryIdentifier(n));
  }

  public void visitRelationalExpression(GNode n) {
    setExpression(new RelationalExpression(n));
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
