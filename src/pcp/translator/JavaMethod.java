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

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * A method.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
/**
 * Modifiers null (VoidType/Type) Identifier FormalParameters Dimensions? ThrowsClause? Block/null
 */
public class JavaMethod extends Declaration {

  private Block body;
  private ThrowsClause exception;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private String name;
  private FormalParameters parameters;
  private Type returnType;
  private Visibility visibility;
  
  /**
   * Creates the method.
   *
   * @param n The method declaration node.
   */
  public JavaMethod(GNode n) {
    // Determine the visibility
    visibility = Visibility.PACKAGE_PRIVATE;
    for (Object o : n.getNode(0)) {
      String m = ((GNode)o).getString(0);
      if (m.equals("public"))
        visibility = Visibility.PUBLIC;
      else if (m.equals("private"))
        visibility = Visibility.PRIVATE;
      else if (m.equals("protected"))
        visibility = Visibility.PROTECTED;
      else if (m.equals("abstract"))
        isAbstract = true;
      else if (m.equals("final"))
        isFinal = true;
      else if (m.equals("static"))
        isStatic = true;
    }

    // Get the return type
    returnType = new Type(n.getGeneric(2));

    // Get the name of the constructor
    name = n.getString(3);

    // Get the parameters
    parameters = new FormalParameters(n.getGeneric(4));

    // Get the dimensions, throws clause, and method body
    if (n.size() == 6) {
      if (null != n.get(5))
        body = new Block(n.getGeneric(5));
    } else if (n.size() == 7) {
      if (n.getNode(5).hasName("Dimensions")) {
        // TODO What are dimensions in a method declaration?
      } else {
        exception = new ThrowsClause(n.getGeneric(5));
      }
      if (null != n.get(6))
        body = new Block(n.getGeneric(6));
    } else if (n.size() == 8) {
      // TODO What are the dimensions in a method declaration?
      exception = new ThrowsClause(n.getGeneric(6));
      if (null != n.get(7))
        body = new Block(n.getGeneric(7));
    }
  }

  /**
   * Gets the return type of this method.
   *
   * @return The return type.
   */
  public Type getReturnType() {
    return returnType;
  }

  /**
   * Gets the visibility of the method.
   *
   * @return The visibility.
   */
  public Visibility getVisibility() {
    return visibility;
  }

  /**
   * Returns <code>true</code> if this method is abstract.
   *
   * @return <code>true</code> if this method is abstract; 
   * <code>false</code> otherwise;
   */
  public boolean isAbstract() {
    return isAbstract;
  }


  /**
   * Returns <code>true</code> if this method is final.
   *
   * @return <code>true</code> if this method is final; 
   * <code>false</code> otherwise;
   */
  public boolean isFinal() {
    return isFinal;
  }

  /**
   * Returns <code>true</code> if this method is static.
   *
   * @return <code>true</code> if this method is static; 
   * <code>false</code> otherwise;
   */
  public boolean isStatic() {
    return isStatic;
  }
}
