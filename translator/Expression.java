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
    operator = n.getString(1);
    visit(n);
  }

  // expressions
  public void visitAdditiveExpression(GNode n) {
    setExpression(new AdditiveExpression(n));
  }

  public void visitCallExpression(GNode n) {
    setExpression(new CallExpression(n));
  }

  public void visitConditionalExpression(GNode n) {
    setExpression(new ConditionalExpression(n));
  }
  
  public void visitEqualityExpression(GNode n) {
    setExpression(new EqualityExpression(n));
  }

  public void visitExpression(GNode n) {
    setExpression(new Expression(n));
  }

  public void visitMultiplicativeExpression(GNode n) {
    setExpression(new MultiplicativeExpression(n));
  }
  
  public void visitPostfixExpression(GNode n) {
    setExpression(new PostfixExpression(n));
  }

  public void visitPrimaryIdentifier(GNode n) {
    setExpression(new PrimaryIdentifier(n));
  }

  public void visitRelationalExpression(GNode n) {
    setExpression(new RelationalExpression(n));
  }

  // literals
  public void visitBooleanLiteral(GNode n) {
    setExpression(new BooleanLiteral(n));
  }

  public void visitCharacterLiteral(GNode n) {
    setExpression(new CharacterLiteral(n));
  }

  public void visitFloatingPointLiteral(GNode n) {
    setExpression(new FloatingPointLiteral(n));
  }

  public void visitIntegerLiteral(GNode n) {
    setExpression(new IntegerLiteral(n));
  }

  public void visitStringLiteral(GNode n) {
    setExpression(new StringLiteral(n));
  }
  
  private void setExpression(Expression expression) {
    if (left == null)
      left = expression;
    else
      right = expression;
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return left.getCC(indent, className, variables) + " " + operator + " " + right.getCC(indent, className, variables);
  }
  
}
