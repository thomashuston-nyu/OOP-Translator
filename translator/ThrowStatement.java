/**
 * Expression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class ThrowStatement extends TranslationVisitor { 
  private Expression expression;
  
  public ThrowStatement(GNode n) {
    expression = null;
    visit(n);
  }
  
  public void visitExpression(Gnode n) {
    expression = new Expression(n);
  }  
}
