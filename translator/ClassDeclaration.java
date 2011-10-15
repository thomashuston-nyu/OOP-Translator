package translator;

import xtc.tree.GNode;
import xtc.tree.Node;

public class ClassDeclaration {
  private GNode n;
  private String name;
  private Scope scope;
  private boolean isAbstract;
  private boolean isFinal;
  private String parent;
  
  public ClassDeclaration(GNode n) {
    if (!n.getName().equals("ClassDeclaration"))
      throw new RuntimeException("Invalid node type");
    this.n = n;
    this.name = n.getString(1);
    this.isAbstract = false;
    this.isFinal = false;
    modifiers();
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
      } else if (modifier.getString(0).equals("abstract")) {
        isAbstract = true;
      }
    }
  }
}
