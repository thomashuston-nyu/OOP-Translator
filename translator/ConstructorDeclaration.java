/**
 * Modifiers null Identifier FormalParameters ThrowsClause? Block
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class ConstructorDeclaration extends TranslationVisitor {
  
  private Block body;
  private String name;
  private FormalParameters parameters;
  private ThrowsClause throwsClause;
  private Visibility visibility;
  
  public ConstructorDeclaration(GNode n) {
    throwsClause = null;
    visit(n);
  }
  
  public void visitBlock(GNode n) {
    body = new Block(n);
  }
  
  public void visitFormalParameters(GNode n) {
    parameters = new FormalParameters(n);
  }
  
  public void visitIdentifier(GNode n) {
    name = n.getString(0);
  }
  
  public void visitMethodDeclaration(GNode n) {
    public void visitModifiers(GNode n) {
      Modifiers modifiers = new Modifiers(n);
      for (String m : modifiers) {
        if (m.equals("public"))
          visibility = Visibility.PUBLIC;
        else if (m.equals("private"))
          visibility = Visibility.PRIVATE;
        else if (m.equals("protected"))
          visibility = Visibility.PROTECTED;
      }
    }
  }
  
  public void visitThrowsClause(GNode n) {
    throwsClause = new ThrowsClause(n);
  }

}