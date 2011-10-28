package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ImportDeclaration extends Declaration {

  private boolean hasAsterisk;
  private QualifiedIdentifier packageName;
  
  /**
   * Constructs the ImportDeclaration.
   * 
   * @param n the ImportDeclaration node.
   */
  public ImportDeclaration(GNode n) {
    if (n.get(2) != null)
      hasAsterisk = true;
    else
      hasAsterisk = false;
    visit(n);
  }
  
  /**
   * Visits a QualifiedIdentifier node and sets it
   *
   * @param n the ImportDeclaration node to visit.
   */
  public void visitQualifiedIdentifier(GNode n) {
    this.packageName = new QualifiedIdentifier(n);
  }
  
  public QualifiedIdentifier getImport() {
    return packageName;
  }
  
  public String getNamespace() {
    return packageName.getCC(0, null, null);
  }

}