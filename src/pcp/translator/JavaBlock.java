/*
 * pcp - The Producer of C++ Programs
 * Copyright (C) 2011 Nabil Hassein, Thomas Huston, Mike Morreale, parent.getMethod()arta Wilgan
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
 * A block containing any number of statements.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 2.1.1
 */
public class JavaBlock extends Visitor implements Scope, Translatable {

  // If this is a constructor, keep a reference to the constructor object
  private JavaConstructor constructor;

  // If this is a method, keep a reference to the method object
  private JavaMethod method;
  
  // The type of block (constructor, method, or block)
  private String name;

  // The parent scope
  private Scope parent;

  // The statements making up the body of the block
  private List<JavaStatement> statements;

  // The variables declared in this scope
  private Map<String, JavaType> variables;

  
  // =========================== Constructors =======================

  /**
   * Creates a new block.
   *
   * @param n The block node.
   * @param parent The parent scope.
   */
  public JavaBlock(GNode n, Scope parent) {
    this(n, parent, null);
  }

  /**
   * Creates a new block.
   *
   * @param n The block node.
   * @param parent The parent scope.
   * @param method The constructor/method this is a block for.
   */
  public JavaBlock(GNode n, Scope parent, JavaMethod method) {
    // Set the parent scope
    this.parent = parent;

    // Initialize the variable map
    variables = new HashMap<String, JavaType>();

    // Set the scope name
    if (null != method) {
      LinkedHashMap<String, JavaType> parameters; 
      if (method.isMethod()) {
        name = "JavaMethod";
        this.method = method;
        parameters = this.method.getParameters();
      } else {
        name = "JavaConstructor";
        this.constructor = (JavaConstructor)method;
        parameters = this.constructor.getParameters();
      }
      // Add the method/constructor parameters to the scope
      Set<String> params = parameters.keySet();
      for (String param : params) {
        variables.put(param, parameters.get(param));
      }
    } else {
      name = "JavaBlock";
    }

    // Parse all the statements in the block
    statements = new ArrayList<JavaStatement>();
    if (n.hasName("Block")) {
      boolean first = true;
      for (Object o : n) {
        // If the first statement is a this() or super() call,
        // skip it and let the constructor translate it
        if (first) {
          first = false;
          GNode node = (GNode)o;
          if (node.hasName("ExpressionStatement") &&
              node.getNode(0).hasName("CallExpression"))
            if (node.getNode(0).getString(2).equals("this") ||
                node.getNode(0).getString(2).equals("super"))
            continue;
        }
        statements.add(new JavaStatement((GNode)o, this));
      }
    } else {
      statements.add(new JavaStatement(n, this));
    }
  }


  // ============================ Get Methods =======================
  
  /**
   * Gets the constructor for which this is the body.
   *
   * @return The constructor.
   */
  public JavaConstructor getConstructor() {
    return constructor;
  }

  /**
   * Gets the method for which this is the body.
   *
   * @return The method.
   */
  public JavaMethod getMethod() {
    return method;
  }

  /**
   * Gets the parent scope.
   *
   * @return The parent scope.
   */
  public Scope getParentScope() {
    return parent;
  }

  /**
   * Gets the scope in which the specified variable is declared.
   *
   * @param name The name of the variable.
   *
   * @return The scope of the variable if it exists;
   * <code>null</code> otherwise.
   */
  public Scope getVariableScope(String name) {
    if (variables.containsKey(name))
      return this;
    return parent.getVariableScope(name);
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
    if (variables.containsKey(name))
      return variables.get(name);
    return parent.getVariableType(name);
  }

  /**
   * Checks if the current scope is of the specified type.
   *
   * @param type The type of the scope.
   *
   * @return <code>True</code> if the scope is of the specified
   * type; <code>false</code> otherwise.
   */
  public boolean hasName(String type) {
    return this.name.equals(type);
  }

  /**
   * Checks if a variable is currently in scope.
   *
   * @return <code>True</code> if the variable is in scope;
   * <code>false</code> otherwise.
   */
  public boolean isInScope(String name) {
    if (variables.containsKey(name))
      return true;
    return parent.isInScope(name);
  }

  /**
   * Checks if the specified variable is static.
   *
   * @return <code>True</code> if the variable is static;
   * <code>false</code> otherwise.
   */
  public boolean isVariableStatic(String name) {
    return parent.isVariableStatic(name);
  }


  // ============================ Set Methods =======================
  
  /**
   * Adds a variable to the scope.
   *
   * @param name The name of the variable.
   * @param type The type of the variable.
   */
  public void addVariable(String name, JavaType type) {
    variables.put(name, type);
  }


  // ======================== Translation Methods ===================
  
  /**
   * Translates the block and adds it 
   * to the output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translate(Printer out) {
    for (JavaStatement s : statements)
      s.translate(out);
    return out;
  }

}
