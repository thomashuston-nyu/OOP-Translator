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

import xtc.tree.GNode;
import xtc.tree.Node;

public class MethodDeclaration {

  private String name;
  private Type returnType;
  private Scope scope;
  public Type[] parameters;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  
  public MethodDeclaration(GNode n) {
    if (!n.getName().equals("MethodDeclaration"))
      throw new RuntimeException("Invalid node type");
    this.isAbstract = false;
    this.isFinal = false;
    this.isStatic = false;
    this.name = n.getString(3);
    this.parameters = null;
    modifiers(n);
    parameters(n);
    returnType(n);
  }
  
  public Scope getScope() {
    return scope;
  }
  
  private void modifiers(GNode n) {
    Node modifiers = n.getNode(0);
    if (modifiers.size() == 0) {
      scope = Scope.PRIVATE;
      return;
    }
    for (Object o : modifiers) {
      if (!(o instanceof Node))
        continue;
      Node modifier = (Node)o;
      if (modifier.getString(0).equals("public")) {
        scope = Scope.PUBLIC;
      } else if (modifier.getString(0).equals("private")) {
        scope = Scope.PRIVATE;
      } else if (modifier.getString(0).equals("protected")) {
        scope = Scope.PROTECTED;
      } else if (modifier.getString(0).equals("final")) {
        isFinal = true;
      } else if (modifier.getString(0).equals("static")) {
        isStatic = true;
      } else if (modifier.getString(0).equals("abstract")) {
        isAbstract = true;
      }
    }
  }
  
  public void parameters(GNode n) {
    Node params = n.getNode(4);
    int size = params.size();
    if (size == 0)
      return;
    parameters = new Type[size];
    for (int i = 0; i < size; i++) {
      if (!(params.get(i) instanceof Node))
        continue;
      Node parameter = (Node)params.get(i);
      parameters[i] = new Type(parameter);
    }
  }
  
  public void returnType(GNode n) {
    Node type = n.getNode(2);
    if (type.getName() == "VoidType") {
      returnType = new VoidType();
    } else if (type.getName() == "Type") {
      returnType = new Type(type);
    }
  }

}