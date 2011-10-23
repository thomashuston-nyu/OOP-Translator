/**
 * Identifier Dimensions? (ArrayInitializer/Expression)?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Declarator extends TranslationVisitor implements Translatable {
  
  private String name;
  private ArrayInitializer arrayInitializer;
  private Expression expression;
  private NewArrayExpression newArray;

  public Declarator(GNode n) {
    name = n.getString(0);
    arrayInitializer = null;
    expression = null;
    newArray = null;
    visit(n);
  }
  
  public String getName() {
    return name;
  }

  public void visitDimensions(GNode n) {
    //
  }

  public void visitArrayInitializer(GNode n) {
    arrayInitializer = new ArrayInitializer(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public void visitNewArrayExpression(GNode n) {
    newArray = new NewArrayExpression(n);
  }

  public String getCC(String className, int indent) {
    StringBuilder s = new StringBuilder(name + " ");
    // if (arrayInitializer != null)
    //   s.append(" = new __rt::Array<" + arrayInitializer.getType() +
    //       ">(" + arrayInitializer.size() + ")");
    if (expression != null)
      s.append(expression.getCC(className,indent));
    return s.toString();
  }
  
}
