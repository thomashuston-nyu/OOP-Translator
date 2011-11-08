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
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

/**
 * A Java file.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class JavaFile extends Visitor implements Translatable {
  
  private Map<Visibility, List<JavaClass>> classes;
  private List<JavaPackage> imports;
  private JavaPackage pkg;
  
  /**
   * Create a new Java file.
   * 
   * @param n The AST node.
   */
  public JavaFile(GNode n) {
    classes = new HashMap<Visibility, List<JavaClass>>();
    for (Visibility v : Visibility.values()) {
      classes.put(v, new ArrayList<JavaClass>());
    }
    imports = new ArrayList<JavaPackage>();
    for (Object o : n)
      if (o instanceof Node)
        dispatch((Node) o);
  }
  
  /**
   * Gets a list of the classes of a specific visibility.
   *
   * @param v The visibility of the classes to return.
   *
   * @return The classes of the specified visibility.
   */
  public List<JavaClass> getClasses(Visibility v) {
    return classes.get(v);
  }
  
  /**
   * Gets a list of the import declarations.
   *
   * @return The imports.
   */
  public List<JavaPackage> getImports() {
    return imports;
  }

  /**
   * Gets the package.
   * 
   * @return The package.
   */
  public JavaPackage getPackage() {
    return pkg;
  }
  
  /**
   * Gets the public class.
   *
   * @return The main class.
   */
  public JavaClass getPublicClass() {
    if (classes.get(Visibility.PUBLIC).size() == 0)
      return null;
    return classes.get(Visibility.PUBLIC).get(0);
  }
  
  /**
   * Visits a ClassDeclaration node and adds it to the
   * list in the classes hash for its visibility.
   * 
   * @param n The AST node to visit.
   */
  public void visitClassDeclaration(GNode n) {
    JavaClass c = new JavaClass(n);
    Visibility v = c.getVisibility();
    classes.get(v).add(c);
  }
  
  /**
   * Visits an ImportDeclaration node and adds it to
   * the imports list.
   *
   * @param n The AST node to visit.
   */
  public void visitImportDeclaration(GNode n) {
    imports.add(new JavaPackage(n));
  }
  
  /**
   * Visits a PackageDeclaration node and sets it as
   * the unit package.
   * 
   * @param n The AST node to visit.
   */
  public void visitPackageDeclaration(GNode n) {
    pkg = new JavaPackage(n);
  }

  public Printer translate(Printer out) {
    JavaClass publicClass = getPublicClass();
    if (null != publicClass)
      return publicClass.translate(out);
    else
      return out;
  }
  
}
