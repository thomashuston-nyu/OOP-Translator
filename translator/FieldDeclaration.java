/**
 * ("final":Word)? Modifiers Type Declarators
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class FieldDeclaration extends Declaration {
  
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private Visibility visibility;
  private Type type;
  private Declarators declarators;
  
  public FieldDeclaration(GNode n) {
    isAbstract = false;
    isFinal = false;
    isStatic = false;
    visibility = Visibility.PRIVATE;
    visit(n);
  }
  
  public String getName() {
    declarators.get(0);
  }
  
  public Visibility getVisibility() {
    return visibility;
  }
  
  public void visitDeclarators(GNode n) {
    declarators = new Declarators(n);
  }
  
  public void visitModifiers(GNode n) {
    Modifiers modifiers = new Modifiers(n);
    for (String s : modifiers) {
      if (s.equals("public"))
        visibility = visibility.PUBLIC;
      else if (s.equals("private"))
        visibility = visibility.PRIVATE;
      else if (s.equals("protected"))
        visibility = visibility.PROTECTED;
      else if (s.equals("final"))
        isFinal = true;
      else if (s.equals("static"))
        isStatic = true;
      else if (s.equals("abstract"))
        isAbstract = true;
    }
  }
  
  public void visitType(GNode n) {
    type = new Type(n);
  }
  
  public void visitWord(GNode n) {
    isFinal = true;
  }

}