/**
 * ("final":Word)? Modifiers Type Declarators
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class FieldDeclaration extends TranslationVisitor {
  
  private boolean isFinal;
  private Modifiers modifiers;
  private Type type;
  private Declarators declarators;
  
  public FieldDeclaration(GNode n) {
    isFinal = false;
    visit(n);
  }
  
  public void visitDeclarators(GNode n) {
    declarators = new Declarators(n);
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