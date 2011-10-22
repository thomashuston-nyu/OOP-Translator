/**
 * ("final":Word)? Modifiers Type null Identifier Dimensions?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class FormalParameter extends TranslationVisitor {
  
  private boolean isArray;
  private boolean isFinal;
  private Modifiers modifiers;
  private String name;
  private Type type;
  
  public FormalParameter(GNode n) {
    isArray = false;
    isFinal = false;
    visit(n);
  }
  
  public void visitDimensions(GNode n) {
    isArray = true;
  }
  
  public void visitIdentifier(GNode n) {
    name = n.getString(0);
  }
  
  public void visitModifiers(GNode n) {
    modifiers = new Modifiers(n);
  }
  
  public void visitType(GNode n) {
    type = new Type(n);
  }
  
  public void visitWord(GNode n) {
    isFinal = true;
  }

}