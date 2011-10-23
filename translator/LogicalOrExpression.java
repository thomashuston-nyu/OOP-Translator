/**
 * (LogicalOrExpression LogicalAndExpression)/
 * yyValue:LogicalAndExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class LogicalOrExpression extends Expression {

  private LogicalOrExpression logicalOrExpression;
  private LogicalAndExpression logicalAndExpression;

  public LogicalOrExpression(GNode n) {
    visit(n);
  }

  public void visitLogicalOrExpression(GNode n) {
    logicalOrExpression = new LogicalOrExpression(n);
  }

  public void visitLogicalAndExpression(GNode n) {
    logicalAndExpression = new LogicalAndExpression(n);
  }
  
  public String getCC() {
    return "";
  }

}
