/**
 * UnaryExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BitwiseNegationExpression extends Expression implements Translatable {
  
  private UnaryExpression unaryExpression;

  public BitwiseNegationExpression(GNode n) {
    visit(n);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}