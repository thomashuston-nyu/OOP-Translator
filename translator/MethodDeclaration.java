package translator;

import xtc.tree.GNode;
import xtc.tree.Node;

public class MethodDeclaration {

  private GNode n;
  private String name;
  private Type returnType;
  private Scope scope;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  
  public MethodDeclaration(GNode n) {
    if (!n.getName().equals("MethodDeclaration"))
      throw new RuntimeException("Invalid node type");
    this.n = n;
    this.isAbstract = false;
    this.isFinal = false;
    this.isStatic = false;
    this.name = n.getString(3);
    modifiers();
    returnType();
  }
  
  private void modifiers() {
    Node modifiers = n.getNode(0);
    if (modifiers.size() == 0) {
      scope = Scope.PUBLIC;
      return;
    }
    for (Object o : modifiers) {
      Node modifier;
      if (o instanceof Node)
        modifier = (Node)o;
      else
        continue;
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
  
  public void returnType() {
    Node type = n.getNode(2);
    if (type.getName() == "VoidType") {
      returnType = new VoidType();
    } else if (type.getName() == "Type") {
      if (type.getNode(0).getName() == "PrimitiveType") {
        returnType = new PrimitiveType(type.getNode(0));
      } else if (type.getNode(0).getName() == "QualifiedIdentifier") {
        returnType = new QualifiedIdentifier(type.getNode(0));
      }
    }
  }  

}