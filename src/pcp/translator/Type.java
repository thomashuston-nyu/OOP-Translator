/**
 * (PrimitiveType/QualifiedIdentifier) Dimensions?
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Type extends TranslationVisitor implements Translatable {
  
  private boolean isArray;
  private String type;
  
  public Type(GNode n) {
    visit(n);
  }
  
  public String getType() {
    return type;
  }

  public boolean isArray() {
    return isArray;
  }

  public void visitDimensions(GNode n) {
    isArray = true;
  }
  
  public void visitPrimitiveType(GNode n) {
    type = n.getString(0);
    if (type == "int")
      type = "int32_t";
    else if (type == "boolean")
      type = "bool";
  }
  
  public void visitQualifiedIdentifier(GNode n) {
    QualifiedIdentifier q = new QualifiedIdentifier(n);
    type = q.get(0);
    // TODO correct this to allow for explicit package references (e.g. java.lang.Object)
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    s.append(type);
    return s.toString();
  }

}
