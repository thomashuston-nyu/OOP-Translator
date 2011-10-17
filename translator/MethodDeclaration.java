/*
 * Children of GNode
 * 0 - Modifiers
 * 1 - ?
 * 2 - Type
 * 3 - String name
 * 4 - FormalParameters
 * 5 - ?
 * 6 - ThrowsClause
 * 7 - Body
 */

package translator;

import java.lang.StringBuilder;

import xtc.tree.Node;

public class MethodDeclaration extends Translatable {

  private String name;
  private Type returnType;
  private Scope scope;
  private FormalParameters parameters;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  
  public MethodDeclaration(Node n) {
    if (!n.getName().equals("MethodDeclaration"))
      throw new RuntimeException("Invalid node type");
    this.isAbstract = false;
    this.isFinal = false;
    this.isStatic = false;
    this.name = n.getString(3);
    this.parameters = new FormalParameters(n.getNode(4));
    modifiers(n);
    returnType(n);
  }
  
  public Scope getScope() {
    return this.scope;
  }
  
  private void modifiers(Node n) {
    Modifiers modifiers = new Modifiers(n.getNode(0));
    if (modifiers.size() == 0) {
      scope = Scope.PRIVATE;
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
      } else if (modifier.equals("static")) {
        isStatic = true;
      } else if (modifier.equals("abstract")) {
        isAbstract = true;
      }
    }
  }
  
  public void returnType(Node n) {
    Node type = n.getNode(2);
    if (type.getName() == "VoidType") {
      returnType = new VoidType();
    } else if (type.getName() == "Type") {
      returnType = new Type(type);
    }
  }
  
  public StringBuilder translate(int indent) {
    StringBuilder translation = new StringBuilder(getIndent(indent));
    if (isFinal)
      translation.append("const ");
    if (isAbstract)
      translation.append("abstract ");
    if (isStatic)
      translation.append("static ");
    translation.append(returnType + " " + name + "(" + parameters + ") {\n");
    translation.append(getIndent(indent + 1));
    translation.append("\n");
    translation.append(getIndent(indent));
    translation.append("}");
    return translation;
  }

}