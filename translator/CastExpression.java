/**
 * Type UnaryExpressionNotPlusMinus
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CastExpression extends Expression implements Translatable {
  
  private Type type;

  public CastExpression(GNode n) {
    visit(n);
  }

  public void visitType(GNode n) {
    type = new Type(n);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}
