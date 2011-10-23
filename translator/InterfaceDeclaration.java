/**
 * Modifiers Identifier null Extension? ClassBody
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class InterfaceDeclaration extends Declaration implements Translatable {
  
  private String name;
  private Extension extension;
  private ClassBody body;
  
  public InterfaceDeclaration(GNode n) {
    extension = null;
    visit(n);
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
  
  public String getCC(String className, int indent) {
    return "";
  }

}