/**
 * Expression?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ReturnStatement extends Statement implements Translatable {   

  private Expression expression;
  
  public ReturnStatement(GNode n) {
    expression = null;
    visit(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}