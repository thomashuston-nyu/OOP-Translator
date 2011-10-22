/**
 * Modifiers null (VoidType/Type) Identifier FormalParameters Dimensions? ThrowsClause? Block/null
 */
package translator;

import java.lang.StringBuilder;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class MethodDeclaration extends Declaration {

  private String name;
  private Type returnType;
  private Visibility visibility;
  private FormalParameters parameters;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  
  public MethodDeclaration(GNode n) {
    isAbstract = false;
    isFinal = false;
    isStatic = false;
    visibility = Visibility.PRIVATE;
    name = n.getString(3);
    visit(n);
  }
  
  public String getName() {
    return name;
  }
  
  public FormalParameters getParameters() {
    return parameters;
  }
  
  public Type getReturnType() {
    return returnType;
  }
  
  public Visibility getVisibility() {
    return visibility;
  }
  
  public boolean isAbstract() {
    return isAbstract;
  }
  
  public boolean isFinal() {
    return isFinal;
  }
  
  public boolean isStatic() {
    return isStatic;
  }
  
  private void visitModifiers(GNode n) {
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
    returnType = new Type(n);
  }
  
  public void visitVoidType(GNode n) {
    returnType = new VoidType(n);
  }
  
/*  public StringBuilder translate(int indent) {
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
  
  public StringBuilder translateHeader(int indent, String className) {
    StringBuilder translation = new StringBuilder(getIndent(indent));
    translation.append("static " + returnType.toString());
    translation.append(" " + name + "(" + className + ");");
    return translation;
  }
*/
}