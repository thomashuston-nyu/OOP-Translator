/**
 * (PrimitiveType/QualifiedIdentifier) ((ConcreteDimensions Dimensions? null)/(null Dimensions? ArrayInitializer))
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class NewArrayExpression extends PrimaryExpression {

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

}