package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * Parses an xtc ClassDeclaration node.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Marta Magdalena
 * @author Mike Morreale
 *
 * @version 0.1
 */
public class ClassDeclaration extends TranslationVisitor {
  
  private ClassBody body;
  private Extension extension;
  private List<Implementation> implementation;
  private boolean isAbstract;
  private boolean isFinal;
  private String name;
  private Visibility visibility;
  
  /**
   * Constructs the ClassDeclaration.
   * 
   * @param n the ClassDeclaration node.
   */
  public ClassDeclaration(GNode n) {
    extension = null;
    implementation = new ArrayList<Implementation>();
    isAbstract = false;
    isFinal = false;
    visit(n);
  }
  
  /**
   * Gets the superclass.
   *
   * @return the extension.
   */
  public Extension getExtension() {
    return extension;
  }
  
  /**
   * Gets the list of implemented interfaces.
   *
   * @return the implementation list.
   */
  public List<Implementation> getImplementation() {
    return implementation;
  }
  
  public String getName() {
    return name;
  }
  
  /**
   * Gets the visibility of the class.
   *
   * @return the visibility.
   */
  public Visibility getVisibility() {
    return visibility;
  }
  
  public boolean isAbstract() {
    return isAbstract;
  }
  
  public boolean isFinal() {
    return isFinal;
  }
  
  public void visitClassBody(GNode n) {
    body = new ClassBody(n);
  }
  
  public void visitExtension(GNode n) {
    extension = new Extension(n);
  }
  
  public void visitIdentifier(GNode n) {
    name = n.getString(0);
  }
  
  public void visitImplementation(GNode n) {
    implementation.add(new Implementation(n));
  }
  
  public void visitModifiers(GNode n) {
    Modifiers modifiers = new Modifiers(n);
    for (String m : modifiers) {
      if (m.equals("public"))
        visibility = Visibility.PUBLIC;
      else if (m.equals("private"))
        visibility = Visibility.PRIVATE;
      else if (m.equals("protected"))
        visibility = Visibility.PROTECTED;
      else if (m.equals("final"))
        isFinal = true;
      else if (m.equals("abstract"))
        isAbstract = true;
    }
  }
  
}
