/**
 * ("final":Word)? Modifiers Type null Identifier Dimensions?
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class FormalParameter extends TranslationVisitor {
  
  private boolean isArray;
  private boolean isFinal;
  private Modifiers modifiers;
  private String name;
  private JavaType type;
  
  public FormalParameter(GNode n) {
    isArray = false;
    isFinal = false;
    visit(n);
    if (isFinal)
      name = n.getString(4);
    else
      name = n.getString(3);
  }
  
  public String getName() {
    return name;
  }
 
  public String getParameter() {
    String s = "";
    if (isFinal)
      s += "const ";
    s += type.getType() + " " + name;
    if (isArray)
      s += "[]";
    return s;
  }

  public String getType() {
    return type.getType();
  }

  public void visitDimensions(GNode n) {
    isArray = true;
  }
  
  public void visitModifiers(GNode n) {
    modifiers = new Modifiers(n);
  }
  
  public void visitType(GNode n) {
    type = new JavaType(n);
  }
  
  public void visitWord(GNode n) {
    isFinal = true;
  }

}
