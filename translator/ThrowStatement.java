/**
 * Expression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ThrowStatement extends Statement implements Translatable { 

  private Expression expression;
  
  public ThrowStatement(GNode n) {
    expression = null;
    visit(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }

}