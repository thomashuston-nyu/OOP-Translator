package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PackageDeclaration extends Declaration implements Translatable {
  
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
  
  public String getNamespace(int indent) {
    StringBuilder s = new StringBuilder();
    String in = getIndent(indent);
    int size = pkg.size();
    for (int i = 0; i < size; i++) {
      s.append(in + "namespace " + pkg.get(i) + " {\n");
      in = getIndent(++indent);
    }
    return s.toString();
  }
  
  public int size() {
    return pkg.size();
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
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