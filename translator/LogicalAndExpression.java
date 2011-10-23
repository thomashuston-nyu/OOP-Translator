/**
 * (LogicalAndExpression BitwiseOrExpression)/
 * yyValue:BitwiseOrExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class LogicalAndExpression extends Expression {
  
  private LogicalAndExpression logicalAndExpression;
  private BitwiseOrExpression bitwiseOrExpression;

  public LogicalAndExpression(GNode n) {
    visit(n);
  }
  
  public void visitLogicalAndExpression(GNode n) {
    logicalAndExpression = new LogicalAndExpression(n);
  }

  public void visitBitwiseOrExpression(GNode n) {
    bitwiseOrExpression = new BitwiseOrExpression(n);
  }
  
  public String getCC() {
    return "";
  }
  
}
