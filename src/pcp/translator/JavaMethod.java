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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 *
 * @version 1.1
 */
public class JavaMethod extends Visitor implements Translatable {

  private JavaBlock body;
  private JavaClass cls;
  //private ThrowsClause exception;
  private boolean isAbstract, isFinal, isStatic;
  private String name;
  private LinkedHashMap<String, JavaType> parameters; 
  private JavaType returnType;
  private Map<String, JavaType> variables;
  private JavaVisibility visibility;


  // =========================== Constructors =======================
  
  /**
   * Empty constructor for subclass use.
   */
  public JavaMethod() {
    // Nothing to do
  }

  /**
   * Creates the method.
   *
   * @param n The method declaration node.
   */
  public JavaMethod(GNode n, JavaClass cls) {
    // Set the class
    this.cls = cls;

    // Set the default visibility
    visibility = JavaVisibility.PACKAGE_PRIVATE;

    // Initialize the parameters map
    parameters = new LinkedHashMap<String, JavaType>();

    // Dispatch over the child nodes
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }

    // Name mangling for method overloading
    if (n.getString(3).contains("$")) {
      Global.runtime.errConsole().pln("$ is not allowed in method names: " + n.getString(3)).flush();
      throw new RuntimeException();
    }
    StringBuilder s = new StringBuilder();
    s.append(n.getString(3));
    Set<String> params = parameters.keySet();
    for (String param : params) {
      s.append("$" + parameters.get(param).getMangledType());
    }
    name = s.toString();

    // Create the body of the method
    body = new JavaBlock(n.getGeneric(7), cls, this);

  }


  // ============================ Get Methods =======================

  /**
   * Gets the class the method is in.
   *
   * @return The class.
   */
  public JavaClass getClassFrom() {
    return cls;
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
   * Gets the parameters of the constructor.
   *
   * @return The parameters.
   */
  public LinkedHashMap<String, JavaType> getParameters() {
    return parameters;
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
  public JavaVisibility getVisibility() {
    return visibility;
  }

  /**
   * Returns <code>true</code> if this method is abstract.
   *
   * @return <code>true</code> if this method is abstract; 
   * <code>false</code> otherwise.
   */
  public boolean isAbstract() {
    return isAbstract;
  }

  /**
   * Returns <code>true</code> if this method is final.
   *
   * @return <code>true</code> if this method is final; 
   * <code>false</code> otherwise.
   */
  public boolean isFinal() {
    return isFinal;
  }

  /**
   * Returns <code>true</code> if this method is static.
   *
   * @return <code>true</code> if this method is static; 
   * <code>false</code> otherwise.
   */
  public boolean isStatic() {
    return isStatic;
  }

  /**
   * Returns <code>true</code> if this method is virtual.
   *
   * @return <code>True</code> if this method is virtual;
   * <code>false</code> otehrwise.
   */
  public boolean isVirtual() {
    return !isStatic && (visibility == JavaVisibility.PUBLIC || visibility == JavaVisibility.PROTECTED);
  }


  // =========================== Visit Methods ======================

  /**
   * Parses the body of the method.
   *
   * @param n The block node.
   */
  public void visitBlock(GNode n) {
    // Already initialized
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
      JavaType paramType = new JavaType(param.getGeneric(j));
      if (null != param.getNode(j).get(1))
        paramType.setDimensions(param.getNode(j).getNode(1).size());
      parameters.put("$" + param.getString(j + 2), paramType);
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
        visibility = JavaVisibility.PUBLIC;
      else if (m.equals("private"))
        visibility = JavaVisibility.PRIVATE;
      else if (m.equals("protected"))
        visibility = JavaVisibility.PROTECTED;
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


  // ======================== Translation Methods ===================

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
    out.indent().p("static ");
    returnType.translate(out);
    if (returnType.isArray())
      out.p("*");
    out.p(" ").p(name).p("(");
    if (!isStatic) {
      out.p(cls.getName());
      if (parameters.size() > 0)
        out.p(", ");
    }
    Set<String> params = parameters.keySet();
    int count = 0;
    for (String param : params) {
      parameters.get(param).translate(out);
      if (parameters.get(param).isArray())
        out.p("*");
      if (count < params.size() - 1)
        out.p(", ");
      count++;
    }
    return out.pln(");");
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
    Set<String> params = parameters.keySet();
    for (String param : params) {
      out.p(", ");
      parameters.get(param).translate(out);
      if (parameters.get(param).isArray())
        out.p("*");
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
    if (caller != cls) {
      out.p("(").p(returnType.getType()).p("(*)(").p(caller.getName());
      Set<String> params = parameters.keySet();
      for (String param : params) {
        out.p(",");
        parameters.get(param).translate(out);
        if (parameters.get(param).isArray())
          out.p("*");
      }
      out.p("))");
    }
    out.p("&__").p(cls.getName()).p("::").p(name);
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
    out.indent();
    returnType.translate(out).p(" ");
    out.p("__").p(cls.getName()).p("::").p(name).p("(");
    if (!isStatic) {
      out.p(cls.getName()).p(" __this");
      if (parameters.size() > 0)
        out.p(", ");
    }
    Set<String> params = parameters.keySet();
    int count = 0;
    for (String param : params) {
      parameters.get(param).translate(out);
      if (parameters.get(param).isArray())
        out.p("*");
      out.p(" ").p(param);
      if (count < params.size() - 1)
        out.p(", ");
      count++;
    }
    out.pln(") {").incr();
    body.translate(out);
    out.decr().indent().pln("}");
    return out;
  }

}
