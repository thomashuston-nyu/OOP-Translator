/*
 * 0 - ?
 * 1 - QualifiedIdentifier
 * 2 - ClassName
 */
package translator;

import xtc.tree.Node;

public class ImportDeclaration {

  private QualifiedIdentifier packageName;
  private String className;
  
  public ImportDeclaration(Node n) {
    if (!n.getName().equals("ImportDeclaration"))
      throw new RuntimeException("Invalid node type");
    this.packageName = new QualifiedIdentifier(n.getNode(1));
    this.className = n.getString(2);
  }
  
  public String toString() {
    return "";
  }

}