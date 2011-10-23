/**
 * QualifiedIdentifier?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ThisExpression extends Expression {
  
  private QualifiedIdentifier qualifiedIdentifier;

  public ThisExpression(GNode n) {
    qualifiedIdentifier = null;
    visit(n);
  }
  
  public void visitQualifiedIdentifier(GNode n) {
    qualifiedIdentifier = new QualifiedIdentifier(n);
  }
}
