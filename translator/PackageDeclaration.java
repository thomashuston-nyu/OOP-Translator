package translator;

import java.lang.StringBuilder;

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
 * @author Marta Wilgan
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
    return pkg;
  }
  
  public String getCC() {
    StringBuilder s = new StringBuilder();
    int size = pkg.size();
    for (int i = 0; i < size; i++) {
      s.append(pkg.get(i));
      if (i < size - 1)
        s.append("::");
    }
    return s.toString();
  }
  
  /**
   * Visits a QualifiedIdentifier node and sets it
   * as the package.
   * 
   * @param n the QualifiedIdentifier node to visit.
   */
  public void visitQualifiedIdentifier(GNode n) {
    pkg = new QualifiedIdentifier(n);
  }

}