/**
 * ArrayInitializer/
 * Expression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class VariableInitializer extends TranslationVisitor {

  private ArrayInitializer array;
  private Expression expression;
  
  public VariableInitializer(GNode n) {
    array = null;
    expression = null;
    visit(n);
  }
  
  public void visitArrayInitializer(GNode n) {
    array = new ArrayInitializer(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

}
