/**
 * UnaryExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class LogicalNegationExpression extends Expression implements Translatable {

  private UnaryExpression unaryExpression;

  public LogicalNegationExpression(GNode n) {
    visit(n);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
