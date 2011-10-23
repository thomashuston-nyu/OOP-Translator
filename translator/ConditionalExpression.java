/**
 * (LogicalOrExpression Expression ConditionalExpression)/
 * yyValue:LogicalOrExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ConditionalExpression extends Expression implements Translatable {
  
  private LogicalOrExpression logicalOrExpression;
  private Expression expression;
  private ConditionalExpression conditionalExpression;

  public ConditionalExpression(GNode n) {
    visit(n);
  }

  public void visitLogicalOrExpression(GNode n) {
    logicalOrExpression = new LogicalOrExpression(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

  public void visitConditionalExpression(GNode n) {
    conditionalExpression = new ConditionalExpression(n);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}
