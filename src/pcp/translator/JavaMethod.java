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
  private JavaClass inClass;
  //private ThrowsClause exception;
  private boolean isAbstract, isConstructor, isFinal, isStatic;
  private String name;
  private List<String> paramNames;
  private List<JavaType> paramTypes;
  private JavaType returnType;
  private Visibility visibility;
  
  /**
   * Creates the method.
   *
   * @param n The method declaration node.
   */
  public JavaMethod(GNode n, JavaClass inClass) {
    // Set the class
    this.inClass = inClass;
    
    // Set the default visibility
    visibility = Visibility.PACKAGE_PRIVATE;

    // Initialize the parameter lists
    paramNames = new ArrayList<String>();
    paramTypes = new ArrayList<JavaType>();

    // Get the name
    if (n.hasName("MethodDeclaration")) {
      name = n.getString(3);
    } else {
      name= n.getString(2);
      isConstructor = true;
    }

    // Dispatch over the child nodes
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }
  }

  /**
   * Gets the class the method is in.
   *
   * @return The class.
   */
  public JavaClass getClassFrom() {
    return inClass;
  }

  /**
   * Gets the name of the method.
   *
   * @return The name.
   */
  public String getName() {
    return name;
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
        paramTypes.get(paramTypes.size() - 1).setDimensions(param.getNode(j).size());
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

  /**
   * Translates the method into a declaration for
   * the C++ header struct and writes it to the
   * output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translateHeaderDeclaration(Printer out) {
    // Create the declaration for a constructor
    if (isConstructor) {
      out.indent().p("__").p(inClass.getName()).p("(");
      int size = paramNames.size();
      for (int i = 0; i < size; i++) {
        paramTypes.get(i).translate(out).p(" ");
        out.p(paramNames.get(i));
        if (i < size - 1)
          out.p(", ");
      }
      return out.pln(");");

    // Create the declaration for a method
    } else {
      out.indent().p("static ");
      returnType.translate(out).p(" ");
      out.p(name).p("(");
      if ((visibility == Visibility.PUBLIC || visibility == Visibility.PROTECTED) && !isStatic) {
        out.p(inClass.getName());
        if (paramNames.size() > 0)
          out.p(", ");
      }
      int size = paramNames.size();
      for (int i = 0; i < size; i++) {
        paramTypes.get(i).translate(out);
        if (i < size - 1)
          out.p(", ");
      }
      return out.pln(");");
    }
  }

  /**
   * Translates the method into a declaration for
   * the C++ vtable header struct and writes it to
   * the output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translateVTableDeclaration(Printer out, JavaClass caller) {
    out.indent().p(returnType.getType()).p(" (*")
      .p(name).p(")(").p(caller.getName());
    for (JavaType param : paramTypes) {
      out.p(", ");
      param.translate(out);
    }
    return out.pln(");");
  }

  /**
   * Translates the method into a constructor
   * initialization for the C++ vtable header struct
   * and writes it to the output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translateVTableReference(Printer out, JavaClass caller) {
    out.indent().p(name).p("(");
    if (caller != inClass) {
      out.p("(").p(returnType.getType()).p("(*)(").p(caller.getName());
      for (JavaType param : paramTypes) {
        out.p(",");
        param.translate(out);
      }
      out.p("))");
    }
    out.p("&__").p(inClass.getName()).p("::").p(name);
    return out.p(")");
  }

  /**
   * Translates the method into C++ and writes
   * it to the output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translate(Printer out) {
    return body.translate(out);
  }

}
