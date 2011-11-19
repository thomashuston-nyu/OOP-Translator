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
import java.util.Set;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

/**
 * A constructor.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 1.1
 */
public class JavaConstructor extends JavaMethod implements Translatable {

  private JavaStatement body;
  private JavaClass cls;
  //private ThrowsClause exception;
  private Map<String, JavaExpression> initialize;
  private boolean isAbstract, isFinal, isStatic;
  private String name;
  private List<String> paramNames;
  private List<JavaType> paramTypes;
  private Map<String, JavaType> variables;
  private JavaVisibility visibility;


  // =========================== Constructors =======================
  
  /**
   * Creates the constructor.
   *
   * @param n The constructor declaration node.
   */
  public JavaConstructor(GNode n, JavaClass cls) {
    // Set the class
    this.cls = cls;

    // Initialize the maps
    initialize = new HashMap<String, JavaExpression>();
    variables = new HashMap<String, JavaType>();
    
    // Set the default visibility
    visibility = JavaVisibility.PACKAGE_PRIVATE;

    // Initialize the parameter lists
    paramNames = new ArrayList<String>();
    paramTypes = new ArrayList<JavaType>();

    // Get the name
    name= n.getString(2);

    // Dispatch over the child nodes
    for (Object o : n) {
      if (o instanceof Node) {
        dispatch((Node)o);
      }
    }
  }


  // ============================ Get Methods =======================

  /**
   * Gets the class constructor is for.
   *
   * @return The class.
   */
  public JavaClass getClassFrom() {
    return cls;
  }

  /**
   * Gets the variables declared in the constructor.
   *
   * @return The variables.
   */
  public Map<String, JavaType> getVariables() {
    return variables;
  }

  /**
   * Gets the type of the specified variable.
   *
   * @param name The name of the variable.
   *
   * @return The type if the variable exists;
   * <code>null</code> otherwise.
   */
  public JavaType getVariableType(String name) {
    return variables.get(name);
  }

  /**
   * Gets the visibility of the constructor.
   *
   * @return The visibility.
   */
  public JavaVisibility getVisibility() {
    return visibility;
  }

  /**
   * Checks if a variable was declared in this method.
   *
   * @return <code>True</code> if the variable was declared
   * in the method; <code>false</code> otherwise.
   */
  public boolean hasVariable(String name) {
    return variables.containsKey(name);
  }

  /**
   * Returns <code>true</code>.
   *
   * @return <code>true</code>.
   */
  public boolean isConstructor() {
    return true;
  }


  // ============================ Set Methods =======================
  
  /**
   * Adds a variable to initialize in the constructor.
   *
   * @param name The name of the variable.
   * @param value The value of the variable.
   */
  public void addInitializer(String name, JavaExpression value) {
    initialize.put(name, value);
  }

  /**
   * Adds a variable to the method scope.
   *
   * @param name The name of the variable.
   * @param type The type of the variable.
   */
  public void addVariable(String name, JavaType type) {
    variables.put(name, type);
  }


  // =========================== Visit Methods ======================

  /**
   * Parses the body of the constructor.
   *
   * @param n The block node.
   */
  public void visitBlock(GNode n) {
    body = new JavaStatement(n, this);
  }

  /**
   * Parses the dimensions of the constructor.
   *
   * @param n The dimensions node.
   */
  public void visitDimensions(GNode n) {
    // TODO: What are dimensions in a method declaration?  
  }
     
  /**
   * Parses the parameters of the constructor.
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
      paramNames.add("$" + param.getString(++j));
      if (++j < param.size() - 1)
        paramTypes.get(paramTypes.size() - 1).setDimensions(param.getNode(j).size());
    }
  }

  /**
   * Parses the modifiers of the constructor.
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
    }
  }

  /**
   * Parses the exceptions thrown by the constructor.
   *
   * @param n The throws clause node.
   */
  public void visitThrowsClause(GNode n) {
    // TODO: handle exceptions
    //exception = new ThrowsClause(n);
  }


  // ======================== Translation Methods ===================

  /**
   * Translates the constructor into a declaration for
   * the C++ header struct and writes it to the
   * output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translateHeaderDeclaration(Printer out) {
    out.indent().p("__").p(cls.getName()).p("(");
    int size = paramNames.size();
    for (int i = 0; i < size; i++) {
      paramTypes.get(i).translate(out);
      if (paramTypes.get(i).isArray())
        out.p("*");
      out.p(" ").p(paramNames.get(i));
      if (i < size - 1)
        out.p(", ");
    }
    return out.pln(");");
  }

  /**
   * Translates the constructor into C++ and writes
   * it to the output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translate(Printer out) {
    out.indent().p("__").p(name).p("::__").p(name).p("(");
    int size = paramNames.size();
    for (int i = 0; i < size; i++) {
      paramTypes.get(i).translate(out);
      if (paramTypes.get(i).isArray())
        out.p("*");
      out.p(" ").p(paramNames.get(i));
      if (i < size - 1)
        out.p(", ");
    }
    out.pln(")");
    out.indent().p(": __vptr(&__vtable)");
    Set<String> keys = initialize.keySet();
    for (String key : keys) {
      out.pln(",").indent().p(key).p("(");
      initialize.get(key).translate(out);
      out.p(")");
    }
    out.pln(" {").incr();
    body.translate(out);
    return out.decr().indent().pln("}");
  }

}
