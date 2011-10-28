/**
 * (PrimitiveType/QualifiedIdentifier) ((ConcreteDimensions Dimensions? null)/(null Dimensions? ArrayInitializer))
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class NewArrayExpression extends Expression implements Translatable {

  private ArrayInitializer arrayInitializer;
  private ConcreteDimensions concrete;
  private String dimensions;
  private PrimitiveType primitive;
  private QualifiedIdentifier qualified;
  
  public NewArrayExpression(GNode n) {
    arrayInitializer = null;
    concrete = null;
    dimensions = null;
    primitive = null;
    qualified = null;
    visit(n);
  }
  
  public void visitArrayInitializer(GNode n) {
    arrayInitializer = new ArrayInitializer(n);
  }
  
  public void visitConcreteDimensions(GNode n) {
    concrete = new ConcreteDimensions(n);
  }
  
  public void visitDimensions(GNode n) {
    dimensions = n.getString(0);
  }
  
  public void visitPrimitiveType(GNode n) {
    primitive = new PrimitiveType(n);
  }
  
  public void visitQualifiedIdentifier(GNode n) {
    qualified = new QualifiedIdentifier(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    s.append("new __rt::Array<");
    if (primitive != null)
      s.append(primitive.getType());
    else if (qualified != null)
      s.append(qualified.getType());
    s.append(">(" + concrete.getDimensions() + ");");
    return s.toString();
  }

}