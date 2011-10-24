/**
 * Expression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ExpressionStatement extends Statement implements Translatable {
  
  private Expression expression;
  
  public ExpressionStatement(GNode n) {
    visit(n);
  }

  // expressions
  public void visitAdditiveExpression(GNode n) {
    expression = new AdditiveExpression(n);
  }

  public void visitCallExpression(GNode n) {
    expression = new CallExpression(n);
  }

  public void visitConditionalExpression(GNode n) {
    expression = new ConditionalExpression(n);
  }
  
  public void visitEqualityExpression(GNode n) {
    expression = new EqualityExpression(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

  public void visitMultiplicative(GNode n) {
    expression = new MultiplicativeExpression(n);
  }

  public void visitPrimaryIdentifier(GNode n) {
    expression = new PrimaryIdentifier(n);
  }

  public void visitPostfixExpression(GNode n) {
    expression = new PostfixExpression(n);
  }

  public void visitRelationalExpression(GNode n) {
    expression = new RelationalExpression(n);
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return getIndent(indent) + expression.getCC(indent, className, variables) + ";\n";
  }
  
}
