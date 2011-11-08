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
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

/**
 * A method or constructor.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class JavaMethod extends Visitor implements Translatable {

  private JavaStatement body;
  //private ThrowsClause exception;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private String name;
  private List<Integer> paramDimensions;
  private List<String> paramNames;
  private List<JavaType> paramTypes;
  private JavaType returnType;
  private Visibility visibility;
  
  /**
   * Creates the method.
   *
   * @param n The method declaration node.
   */
  public JavaMethod(GNode n) {
    
    // Set the default visibility
    visibility = Visibility.PACKAGE_PRIVATE;

    // Initialize the parameter lists
    paramDimensions = new ArrayList<Integer>();
    paramNames = new ArrayList<String>();
    paramTypes = new ArrayList<JavaType>();

    // Get the name
    if (n.hasName("MethodDeclaration"))
      name = n.getString(3);
    else
      name= n.getString(2);

    // Dispatch over the child nodes
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }

  }

  /**
   * Gets the return type of this method.
   *
   * @return The return type.
   */
  public JavaType getReturnType() {
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

  /**
   * Parses the body of the method.
   *
   * @param n The block node.
   */
  public void visitBlock(GNode n) {
    body = new JavaStatement(n);
  }

  /**
   * Parses the dimensions of the method.
   *
   * @param n The dimensions node.
   */
  public void visitDimensions(GNode n) {
    // TODO: What are dimensions in a method declaration?  
  }
     
  /**
   * Parses the parameters of the method.
   *
   * @param n The formal parameters node.
   */
  public void visitFormalParameters(GNode n) {
    for (Object o : n) {
      Node param = (Node)o;
      int j;
      if (param.getNode(1).hasName("Type")) 
        j = 1;
      else
        j = 2;
      paramTypes.add(new JavaType(param.getGeneric(j++)));
      paramNames.add(param.getString(++j));
      if (++j < param.size() - 1)
        paramDimensions.add(param.getNode(j).size());
      else
        paramDimensions.add(0);
    }
  }

  /**
   * Parses the modifiers of the method.
   *
   * @param n The modifiers node.
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
      else if (m.equals("abstract"))
        isAbstract = true;
      else if (m.equals("final"))
        isFinal = true;
      else if (m.equals("static"))
        isStatic = true;
    }
  }

  /**
   * Parses the exceptions thrown by the method.
   *
   * @param n The throws clause node.
   */
  public void visitThrowsClause(GNode n) {
    // TODO: handle exceptions
    //exception = new ThrowsClause(n);
  }

  /**
   * Parses the return type of the method.
   *
   * @param n The type node.
   */
  public void visitType(GNode n) {
    returnType = new JavaType(n);
  }

  /**
   * Sets the return type to void.
   *
   * @param n The void type node.
   */
  public void visitVoidType(GNode n) {
    returnType = new JavaType(n);
  }

  public Printer translate(Printer out) {
    return body.translate(out);
  }

}
