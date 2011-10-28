/**
 * QualifiedIdentifier?
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ThisExpression extends Expression implements Translatable {
  
  private QualifiedIdentifier qualifiedIdentifier;

  public ThisExpression(GNode n) {
    qualifiedIdentifier = null;
    visit(n);
  }
  
  public void visitQualifiedIdentifier(GNode n) {
    qualifiedIdentifier = new QualifiedIdentifier(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
