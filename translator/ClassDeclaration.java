/*
 * 0 - Modifiers
 * 1 - Name
 * 2 - ?
 * 3 - Extends
 * 4 - Implements
 * 5 - ClassBody
 */

package translator;

import java.lang.StringBuilder;

import xtc.tree.Node;

public class ClassDeclaration extends Translatable {
  private String name;
  private Scope scope;
  private boolean isAbstract;
  private boolean isFinal;
  private Extension extension;
  private Implementation implementation;
  private ClassBody body;
  private String parent;
  
  public ClassDeclaration(Node n) {
    if (!n.getName().equals("ClassDeclaration"))
      throw new RuntimeException("Invalid node type");
    this.name = n.getString(1);
    this.isAbstract = false;
    this.isFinal = false;
    modifiers(n);
    if (n.get(3) != null)
      this.extension = new Extension(n.getNode(3));
    else
      this.extension = null;
    if (n.get(4) != null)
      this.implementation = new Implementation(n.getNode(4));
    else
      this.implementation = null;
    this.body = new ClassBody(n.getNode(5));
  }

  private void modifiers(Node n) {
    Modifiers modifiers = new Modifiers(n.getNode(0));
    if (modifiers.size() == 0) {
      scope = Scope.PUBLIC;
      return;
    }
    for (int i = 0; i < modifiers.size(); i++) {
      Modifier modifier = modifiers.get(i);
      if (modifier.equals("public")) {
        scope = Scope.PUBLIC;
      } else if (modifier.equals("private")) {
        scope = Scope.PRIVATE;
      } else if (modifier.equals("protected")) {
        scope = Scope.PROTECTED;
      } else if (modifier.equals("final")) {
        isFinal = true;
      } else if (modifier.equals("abstract")) {
        isAbstract = true;
      }
    }
  }
  
  public StringBuilder translate(int indent) {
    StringBuilder translation = new StringBuilder(getIndent(indent));
    if (isFinal)
      translation.append("final ");
    translation.append("class " + name + " {\n");
    translation.append(body.translate(indent+1));
    translation.append(getIndent(indent) + "}");
    return translation;
  }
  
}
