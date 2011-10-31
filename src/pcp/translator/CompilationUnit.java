/*
 * pcp - The Producer of C++ Programs
 * Copyright (C) 2011 Nabil Hassein, Thomas Huston, Mike Morreale, Marta Wilgan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * A compilation unit.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class CompilationUnit extends TranslationVisitor {
  
  private Map<Visibility, List<ClassDeclaration>> classes;
  private List<ImportDeclaration> imports;
  private PackageDeclaration pkg;
  
  /**
   * Create a new compilation unit.
   * 
   * @param n The AST node.
   */
  public CompilationUnit(GNode n) {
    classes = new HashMap<Visibility, List<ClassDeclaration>>();
    imports = new ArrayList<ImportDeclaration>();
    visit(n);
  }
  
  /**
   * Gets a list of the classes of a specific visibility.
   *
   * @param v The visibility of the classes to return.
   *
   * @return The classes of the specified visibility.
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
   * @return The imports.
   */
  public List<ImportDeclaration> getImports() {
    return imports;
  }

  /**
   * Gets the public class.
   *
   * @return The main class.
   */
  public ClassDeclaration getPublicClass() {
    if (!classes.containsKey(Visibility.PUBLIC))
        return null;
    return classes.get(Visibility.PUBLIC).get(0);
  }
  
  /**
   * Gets the package.
   * 
   * @return The package.
   */
  public PackageDeclaration getPackage() {
    return pkg;
  }
  
  /**
   * Visits a ClassDeclaration node and adds it to the
   * list in the classes hash for its visibility.
   * 
   * @param n The AST node to visit.
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
   * @param n The AST node to visit.
   */
  public void visitImportDeclaration(GNode n) {
    imports.add(new ImportDeclaration(n));
  }
  
  /**
   * Visits a PackageDeclaration node and sets it as
   * the unit package.
   * 
   * @param n The AST node to visit.
   */
  public void visitPackageDeclaration(GNode n) {
    pkg = new PackageDeclaration(n);
  }
  
}
