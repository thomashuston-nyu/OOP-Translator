/**
 * (LogicalOrExpression LogicalAndExpression)/
 * yyValue:LogicalAndExpression
 */

package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class LogicalOrExpression extends TranslationVisitor {

  private LogicalOrExpression logicalOrExpression;
  private LogicalAndExpression logicalAndExpression;

  public LogicalOrExpression() {
    visit(n);
  }

  public void visitLogicalOrExpression(GNode n) {
    logicalOrExpression = new LogicalOrExpression();
  }

  public void visitLogicalAndExpression(GNode n) {
    logicalAndExpression = new LogicalAndExpression();
  }

}
