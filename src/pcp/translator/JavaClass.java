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
 * A Java class.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class JavaClass extends Visitor implements Translatable {
  
  private JavaMethod constructor;
  private JavaType extension;
  private Map<Visibility, List<JavaField>> fields;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private Map<Visibility, List<JavaMethod>> methods;
  private String name;
  private JavaClass parent;
  private Visibility visibility;
  
  /**
   * Constructs the class.
   * 
   * @param n The class declaration node.
   */
  public JavaClass(GNode n) {
    // Get the class name
    name = n.getString(1);

    // Initialize the modifiers to default values
    isAbstract = false;
    isFinal = false;
    isStatic = false;
    visibility = Visibility.PACKAGE_PRIVATE;

    // Instantiate the hashes
    fields = new HashMap<Visibility, List<JavaField>>();
    methods = new HashMap<Visibility, List<JavaMethod>>();
    for (Visibility v : Visibility.values()) {
      fields.put(v, new ArrayList<JavaField>());
      methods.put(v, new ArrayList<JavaMethod>());
    }

    // Visit the nodes in the class
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }
  }
  
  /**
   * Gets the reference to the superclass.
   *
   * @return The reference.
   */
  public JavaType getExtension() {
    return extension;
  }
  
  /**
   * Gets the name of the class.
   *
   * @return The name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Gets the superclass.
   *
   * @return The superclass.
   */
  public JavaClass getParent() {
    return parent;
  }

  /**
   * Gets the visibility of the class.
   *
   * @return The visibility.
   */
  public Visibility getVisibility() {
    return visibility;
  }
  
  /**
   * Tests whether the class has a superclass.
   *
   * @return <code>True</code> if the class has a superclass;
   * <code>false</code> otherwise.
   */
  public boolean hasExtension() {
    return extension != null;
  }
  
  /**
   * Tests whether the class is abstract.
   *
   * @return <code>True</code> if the class is abstract;
   * <code>false</code> otherwise.
   */
  public boolean isAbstract() {
    return isAbstract;
  }
  
  /**
   * Tests whether the class is final.
   *
   * @return <code>True</code> if the class is final;
   * <code>false</code> otherwise.
   */
  public boolean isFinal() {
    return isFinal;
  }
  
  /**   
   * Sets the superclass.
   *
   * @param parent The superclass.
   */
  public void setParent(JavaClass parent) {
    this.parent = parent;
  }
  
  /**
   * Visits the class body.
   *
   * @param n The AST node to visit.
   */
  public void visitClassBody(GNode n) {
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }
  }

  /**
   * Visits a constructor.
   *
   * @param n The AST node to visit.
   */
  public void visitConstructorDeclaration(GNode n) {
    constructor = new JavaMethod(n);
  }
  
  /**
   * Visits the extension.
   *
   * @param n The AST node to visit.
   */
  public void visitExtension(GNode n) {
    extension = new JavaType(n.getGeneric(0));
  }

  /**
   * Visits a field.
   *
   * @param n The AST node to visit.
   */
  public void visitFieldDeclaration(GNode n) {
    JavaField f = new JavaField(n);
    fields.get(f.getVisibility()).add(f);
  }

  /**
   * Visits a method.
   *
   * @param n The AST node to visit.
   */
  public void visitMethodDeclaration(GNode n) {
    JavaMethod m = new JavaMethod(n);
    methods.get(m.getVisibility()).add(m);
  }
  
  /**
   * Visits the modifiers.
   *
   * @param n The AST node to visit.
   */
  public void visitModifiers(GNode n) {
    for (Object o : n) {
      String m = ((GNode)o).getString(0);
      if (m.equals("public"))
        visibility = Visibility.PUBLIC;
      else if (m.equals("private"))
        visibility = Visibility.PRIVATE;
      else if (m.equals("protected"))
        visibility = Visibility.PROTECTED;
      else if (m.equals("final"))
        isFinal = true;
      else if (m.equals("abstract"))
        isAbstract = true;
      else if (m.equals("static"))
        isStatic = true;
    }
  }

  public Printer translate(Printer out) {
    return methods.get(Visibility.PUBLIC).get(0).translate(out);
  }

}
