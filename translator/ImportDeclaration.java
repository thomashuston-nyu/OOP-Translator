package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * Parses an xtc ImportDeclaration node.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Marta Wilgan
 * @author Mike Morreale
 *
 * @version 0.1
 */
public class ImportDeclaration extends TranslationVisitor {

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

}