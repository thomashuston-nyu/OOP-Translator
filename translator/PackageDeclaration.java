package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * Parses an xtc PackageDeclaration node.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Marta Magdalena
 * @author Mike Morreale
 *
 * @version 0.1
 */
public class PackageDeclaration extends TranslationVisitor {
  
  private QualifiedIdentifier pkg;
  
  /**
   * Constructs the PackageDeclaration.
   * 
   * @param n the PackageDeclaration node.
   */
  public PackageDeclaration(GNode n) {
    visit(n);
  }
  
  /**
   * Gets the package identifier.
   *
   * @return the package.
   */
  public QualifiedIdentifier getPackage() {
    return this.pkg;
  }
  
  /**
   * Visits a QualifiedIdentifier node and sets it
   * as the package.
   * 
   * @param n the QualifiedIdentifier node to visit.
   */
  public void visitQualifiedIdentifier(GNode n) {
    this.pkg = new QualifiedIdentifier(n);
  }

}