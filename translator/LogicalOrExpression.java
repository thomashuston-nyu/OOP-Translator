/**
 * (LogicalOrExpression LogicalAndExpression)/
 * yyValue:LogicalAndExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class LogicalOrExpression extends Expression implements Translatable {

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
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }

}
