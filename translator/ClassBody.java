/*
 * 0 - FieldDeclaration
 * 1 - MethodDeclaration
 */
package translator;

import java.lang.StringBuilder;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.Node;

public class ClassBody extends Translatable {
  
  private List<FieldDeclaration> fields;
  private List<MethodDeclaration> publicMethods;
  private List<MethodDeclaration> privateMethods;
  private List<MethodDeclaration> protectedMethods;
  
  public ClassBody(Node n) {
    if (!n.getName().equals("ClassBody"))
      throw new RuntimeException("Invalid node type");
    this.fields = new ArrayList<FieldDeclaration>();
    this.publicMethods = new ArrayList<MethodDeclaration>();
    this.privateMethods = new ArrayList<MethodDeclaration>();
    this.protectedMethods = new ArrayList<MethodDeclaration>();
    for (int i = 0; i < n.size(); i++) {
      if (n.getNode(i).getName() == "FieldDeclaration") {
        this.fields.add(new FieldDeclaration(n.getNode(i)));
      } else if (n.getNode(i).getName() == "MethodDeclaration") {
        MethodDeclaration method = new MethodDeclaration(n.getNode(i));
        if (method.getScope() == Scope.PUBLIC)
          publicMethods.add(method);
        else if (method.getScope() == Scope.PRIVATE)
          privateMethods.add(method);
        else if (method.getScope() == Scope.PROTECTED)
          protectedMethods.add(method);
      }
    }
  }
  
  public StringBuilder translate(int indent) {
    StringBuilder translation = new StringBuilder();
    if (publicMethods.size() > 0) {
      translation.append(getIndent(indent) + "public:\n");
      for (MethodDeclaration method : publicMethods) {
        translation.append(method.translate(indent+1).toString() + "\n");
      }
    }
    if (privateMethods.size() > 0) {
      translation.append(getIndent(indent) + "private:\n");
      for (MethodDeclaration method : privateMethods) {
        translation.append(method.translate(indent+1).toString() + "\n");
      }
    }
    if (protectedMethods.size() > 0) {
      translation.append(getIndent(indent) + "protected:\n");
      for (MethodDeclaration method : protectedMethods) {
        translation.append(method.translate(indent+1).toString() + "\n");
      }
    }
    return translation;
  }

}