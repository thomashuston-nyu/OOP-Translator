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
public class JavaField {
  
  //private Declarators declarators;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private JavaType type;
  private Visibility visibility;
  
  /**
   * Creates the field.
   *
   * @param n The field declaration node.
   */
  public JavaField(GNode n) {
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
    type = new JavaType(n.getGeneric(++i));

    // Get the declarators


  }
  
  public String getDeclaration() {
    String declaration = "";//type.getType() + " " + declarators.get(0).getName();
    if (type.isArray()) {
      declaration += "[]";
    }
    return declaration;
  }

  public String getName() {
    //Declarator d = declarators.get(0);
    //return d.getName();
    return "";
  }
  
  public String getType() {
    return type.getType();
  }
  
  public Visibility getVisibility() {
    return visibility;
  }

}
