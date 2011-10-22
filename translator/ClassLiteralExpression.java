/**
 * VoidType/
 * Type
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ClassLiteralExpression extends PrimaryExpression {

  private Type returnType;
  
  public ClassLiteralExpression(GNode n) {
    visit(n);
  }
  
  public void visitType(GNode n) {
    returnType = new Type(n);
  }
  
  public void visitVoidType(GNode n) {
    returnType = new VoidType(n);
  }

}