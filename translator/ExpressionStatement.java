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
  
  public void visitCallExpression(GNode n) {
    expression = new CallExpression(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return getIndent(indent) + expression.getCC(indent, className, variables) + ";\n";
  }
  
}