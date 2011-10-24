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

  public void visitAdditiveExpression(GNode n) {
    expression = new AdditiveExpression(n);
  }

  public void visitCallExpression(GNode n) {
    expression = new CallExpression(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

  public void visitMultiplicative(GNode n) {
    expression = new MultiplicativeExpression(n);
  }

  public void visitPostfixExpression(GNode n) {
    expression = new PostfixExpression(n);
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return getIndent(indent) + expression.getCC(indent, className, variables) + ";\n";
  }
  
}
