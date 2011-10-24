/**
 * PackageDeclaration? ImportDeclaration* Declaration*
 */
package translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xtc.tree.GNode;
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
public class CompilationUnit extends TranslationVisitor implements Translatable {
  
  private HashMap<Visibility, List<ClassDeclaration>> classes;
  private List<ImportDeclaration> imports;
  private PackageDeclaration pkg;
  
  /**
   * Constructs the CompilationUnit.
   * 
   * @param n the CompilationUnit node.
   */
  public CompilationUnit(GNode n) {
    classes = new HashMap<Visibility, List<ClassDeclaration>>();
    imports = new ArrayList<ImportDeclaration>();
    pkg = null;
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
      return classes.get(v);
    else
      return null;
  }
  
  /**
   * Gets a list of the import declarations.
   *
   * @return a list of all imports.
   */
  public List<ImportDeclaration> getImports() {
    return imports;
  }
  
  /**
   * Gets the package.
   * 
   * @return the package.
   */
  public PackageDeclaration getPackage() {
    return pkg;
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
    imports.add(new ImportDeclaration(n));
  }
  
  /**
   * Visits a PackageDeclaration node and sets it as
   * the unit package.
   * 
   * @param n the PackageDeclaration node to visit.
   */
  public void visitPackageDeclaration(GNode n) {
    pkg = new PackageDeclaration(n);
  }
  
  
  public List<Declaration> getDependencies() {
    List<Declaration> l = new ArrayList<Declaration>();
    if (pkg != null)
      l.add(pkg);
    for (ImportDeclaration i : imports)
      l.add(i);
    return l;
  }
  
  public String getName() {
    List<ClassDeclaration> c = classes.get(Visibility.PUBLIC);
    return c.get(0).getName();
  }
  
  public Extension getExtension() {
    List<ClassDeclaration> c = classes.get(Visibility.PUBLIC);
    return c.get(0).getExtension();
  }
  
  public ClassDeclaration getPublic() {
    List<ClassDeclaration> c = classes.get(Visibility.PUBLIC);
    return c.get(0);
  }
  
  public void setParent(ClassDeclaration parent) {
    List<ClassDeclaration> c = classes.get(Visibility.PUBLIC);
    c.get(0).setParent(parent);
  }
  
  
  // TRANSLATION METHODS
  
  public String getHeader(int indent) {
    StringBuilder s = new StringBuilder();
    String in = getIndent(indent);
    /*if (imports.size() > 0) {
      for (ImportDeclaration i : imports)  {
        s.append("using namespace " + i.getNamespace() + ";\n");
      }
    }
    if (pkg != null) {
      s.append(pkg.getNamespace(indent));
      indent += pkg.size();
      in = getIndent(indent);
    }*/
    List<ClassDeclaration> l = classes.get(Visibility.PUBLIC);
    if (l != null) {
      for (ClassDeclaration c : l) {
        String className = c.getName();
        s.append(in + "struct __" + className + ";\n");
        s.append(in + "struct __" + className + "_VT;\n\n");
        s.append(in + "typedef __" + className + "* " + className + ";\n\n");
        s.append(c.getHeaderStruct(indent));
        s.append("\n");
        s.append(c.getHeaderVTStruct(indent));
      }
    }
    /*if (pkg != null) {
      s.append("\n}");
    }*/
    s.append("\n");
    return s.toString();
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    String in = getIndent(indent);
    /*if (pkg != null) {
      s.append(pkg.getNamespace(indent));
      indent += pkg.size();
      in = getIndent(indent);
    }*/
    List<ClassDeclaration> l = classes.get(Visibility.PUBLIC);
    if (l != null) {
      for (ClassDeclaration c : l) {
        s.append(c.getCC(indent, c.getName(), variables));
      }
    }
   /* if (pkg != null) {
      s.append("\n}");
    }*/
    return s.toString();
  }

}
