/*
 * 0 - Modifiers
 * 1 - Type
 * 2 - Declarators
 */

package translator;

import xtc.tree.Node;

public class FieldDeclaration {

  private Modifiers modifiers;
  private Type type;
  private Declarators declarators;
  
  public FieldDeclaration(Node n) {
    if (!n.getName().equals("FieldDeclaration"))
      throw new RuntimeException("Invalid node type");
    this.modifiers = new Modifiers(n.getNode(0));
    this.type = new Type(n.getNode(1));
    this.declarators = new Declarators(n.getNode(2));
  }
  
  public String toString() {
    return modifiers + " " + type + " " + declarators;
  }

}