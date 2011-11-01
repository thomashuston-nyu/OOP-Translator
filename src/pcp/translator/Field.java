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
 * A class constructor.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
/**
 * ("final":Word)? Modifiers Type Declarators
 */
public class Field extends Declaration {
  
  private Declarators declarators;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private Type type;
  private Visibility visibility;
  
  /**
   * Creates the field.
   *
   * @param n The field declaration node.
   */
  public Field(GNode n) {
    // Check if the field is final
    int i = 0;
    if (n.getNode(1).hasName("Modifiers")) {
      isFinal = true;
      i++;
    }

    // Determine the modifiers
    isAbstract = false;
    isFinal = false;
    isStatic = false;
    visibility = Visibility.PACKAGE_PRIVATE;
    for (Object o : n.getNode(i)) {
      String m = ((GNode)o).getString(0);
      if (m.equals("public"))
        visibility = Visibility.PUBLIC;
      else if (m.equals("private"))
        visibility = Visibility.PRIVATE;
      else if (m.equals("protected"))
        visibility = Visibility.PROTECTED;
      else if (m.equals("abstract"))
        isAbstract = true;
      else if (m.equals("static"))
        isStatic = true;
    }

    // Get the type
    type = new Type(n.getGeneric(++i));

    // Get the declarators


  }
  
  public String getDeclaration() {
    String declaration = type.getType() + " " + declarators.get(0).getName();
    if (type.isArray()) {
      declaration += "[]";
    }
    return declaration;
  }

  public String getName() {
    Declarator d = declarators.get(0);
    return d.getName();
  }
  
  public String getType() {
    return type.getType();
  }
  
  public Visibility getVisibility() {
    return visibility;
  }
  
  public void visitDeclarators(GNode n) {
    declarators = new Declarators(n);
  }
  
  public void visitModifiers(GNode n) {
    Modifiers modifiers = new Modifiers(n);
    for (String s : modifiers) {
      if (s.equals("public"))
        visibility = visibility.PUBLIC;
      else if (s.equals("private"))
        visibility = visibility.PRIVATE;
      else if (s.equals("protected"))
        visibility = visibility.PROTECTED;
      else if (s.equals("final"))
        isFinal = true;
      else if (s.equals("static"))
        isStatic = true;
      else if (s.equals("abstract"))
        isAbstract = true;
    }
  }
  
  public void visitType(GNode n) {
    type = new Type(n);
  }
  
  public void visitWord(GNode n) {
    isFinal = true;
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder(getIndent(indent));
    if (isFinal)
      s.append("const ");
    if (isStatic)
      s.append("static ");
    if (isAbstract)
      s.append("abstract ");
    s.append(type.getType() + " ");
    if (type.getType().equals("String"))
      s.append(declarators.getCC(indent, className, variables, true) + ";\n");
    else
      s.append(declarators.getCC(indent, className, variables) + ";\n");
    Variable v = new Variable(type.getType(), declarators.get(0).getName());
    variables.add(v);
    return s.toString();
  }

}
