/**
 * Identifier Dimensions? (ArrayInitializer/Expression)?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Declarator extends TranslationVisitor {
  
  private String name;
  private boolean isArray;
  private ArrayInitializer arrayInitializer;
  private Expression expression;

  public Declarator(GNode n) {
    name = n.getString(0);
    isArray = false;
    arrayInitializer = null;
    expression = null;
    visit(n);
  }
  
  public String getName() {
    return name;
  }

  public void visitDimensions(GNode n) {
    isArray = true;
  }

  public void visitArrayInitializer(GNode n) {
    arrayInitializer = new ArrayInitializer(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

  public String getCC() {
    StringBuilder s = new StringBuilder(name);
    // if (arrayInitializer != null)
    //   s.append(" = new __rt::Array<" + arrayInitializer.getType() +
    //       ">(" + arrayInitializer.size() + ")");
    s.append(expression.getCC());
    return s.toString();
  }
  
}
