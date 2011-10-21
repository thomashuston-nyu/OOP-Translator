/**
 * PackageDeclaration? ImportDeclaration* Declaration*
 */
package translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * Parses an xtc CompilationUnit node.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Marta Wilgan
 * @author Mike Morreale
 *
 * @version 0.1
 */
public class CompilationUnit extends TranslationVisitor {
  
  private HashMap<Visibility, List<ClassDeclaration>> classes;
  private List<ImportDeclaration> imports;
  private PackageDeclaration pkg;
  
  /**
   * Constructs the CompilationUnit.
   * 
   * @param n the CompilationUnit node.
   */
  public CompilationUnit(GNode n) {
    this.classes = new HashMap<Visibility, List<ClassDeclaration>>();
    this.imports = new ArrayList<ImportDeclaration>();
    visit(n);
  }
  
  /**
   * Gets a list of the classes of a specific visibility.
   *
   * @param v the visibility of the classes to return.
   *
   * @return a list of classes of the specified visibility.
   */
  public List<ClassDeclaration> getClasses(Visibility v) {
    if (classes.containsKey(v))
      return this.classes.get(v);
    else
      return null;
  }
  
  /**
   * Gets a list of the import declarations.
   *
   * @return a list of all imports.
   */
  public List<ImportDeclaration> getImports() {
    return this.imports;
  }
  
  /**
   * Gets the package.
   * 
   * @return the package.
   */
  public PackageDeclaration getPackage() {
    return this.pkg;
  }
  
  /**
   * Visits a ClassDeclaration node and adds it to the
   * list in the classes HashMap for its visibility.
   * 
   * @param n the ClassDeclaration node to visit.
   */
  public void visitClassDeclaration(GNode n) {
    ClassDeclaration c = new ClassDeclaration(n);
    Visibility v = c.getVisibility();
    if (!classes.containsKey(v))
      classes.put(v, new ArrayList<ClassDeclaration>());
    classes.get(v).add(c);
  }
  
  /**
   * Visits an ImportDeclaration node and adds it to
   * the imports list.
   *
   * @param n the ImportDeclaration node to visit.
   */
  public void visitImportDeclaration(GNode n) {
    this.imports.add(new ImportDeclaration(n));
  }
  
  /**
   * Visits a PackageDeclaration node and sets it as
   * the unit package.
   * 
   * @param n the PackageDeclaration node to visit.
   */
  public void visitPackageDeclaration(GNode n) {
    this.pkg = new PackageDeclaration(n);
  }

}