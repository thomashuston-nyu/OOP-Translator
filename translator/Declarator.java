/**
 * Identifier Dimensions? (ArrayInitializer/Expression)?
 */
package translator;

import java.util.ArrayList;
import java.util.List;

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
  
  public void visitAdditiveExpression(GNode n) {
    expression = new AdditiveExpression(n);
  }

  public void visitArrayInitializer(GNode n) {
    arrayInitializer = new ArrayInitializer(n);
  }
  
  public void visitDimensions(GNode n) {
    //
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public void visitNewArrayExpression(GNode n) {
    newArray = new NewArrayExpression(n);
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder(name);
    // if (arrayInitializer != null)
    //   s.append(" = new __rt::Array<" + arrayInitializer.getType() +
    //       ">(" + arrayInitializer.size() + ")");
    if (expression != null)
      s.append(" = " + expression.getCC(indent, className, variables));
    return s.toString();
  }
  
  public String getStringCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder(name);
    if (expression != null)
      s.append(" = __rt::literal(" + expression.getCC(indent, className, variables) + ")");
    return s.toString();
  }
  
}
