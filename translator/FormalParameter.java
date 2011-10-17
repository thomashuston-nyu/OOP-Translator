package translator;

import xtc.tree.Node;

public class FormalParameter {

  private Modifiers modifiers;
  private Type type;
  private String name;
  
  public FormalParameter(Node n) {
    if (!n.getName().equals("FormalParameter"))
      throw new RuntimeException("Invalid node type");
    this.modifiers = new Modifiers(n.getNode(0));
    this.type = new Type(n.getNode(1));
    this.name = n.getString(3);
  }
  
  public String toString() {
    return this.type + " " + this.name;
  }

}