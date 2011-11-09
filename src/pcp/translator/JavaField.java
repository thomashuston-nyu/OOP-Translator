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

/**
 * A field declaration with or without initialization;
 * may appear at the class scope or inside a method.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 1.1
 */
public class JavaField extends JavaStatement implements Translatable {
  
  private boolean isAbstract, isFinal, isStatic;
  private List<String> names;
  private JavaType type;
  private List<JavaExpression> values;
  private Visibility visibility;
  
  /**
   * Creates the field.
   *
   * @param n The field declaration node.
   */
  public JavaField(GNode n) {
    // Set the default visibility
    visibility = Visibility.PACKAGE_PRIVATE;

    // Determine the modifiers
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
      else if (m.equals("static"))
        isStatic = true;
      else if (m.equals("final"))
        isFinal = true;
    }

    // Get the type
    type = new JavaType(n.getGeneric(1));

    // Get the variable names and initialized values
    names = new ArrayList<String>();
    values = new ArrayList<JavaExpression>();
    for (Object o : n.getNode(2)) {
      Node declarator = (Node)o;
      names.add(declarator.getString(0));
      if (null != declarator.get(2))
        values.add(new JavaExpression(declarator.getGeneric(2)));
      else
        values.add(null);
    }

    // Get the dimensions if it's an array
    if (null != n.getNode(2).getNode(0).get(1))
      type.setDimensions(n.getNode(2).getNode(0).getNode(1).size());
  }


  // ============================ Get Methods =======================
  
  /**
   * Gets the type of the field.
   *
   * @return The type.
   */
  public JavaType getType() {
    return type;
  }
  
  /**
   * Gets the visibility of the field.
   *
   * @return The visibility.
   */
  public Visibility getVisibility() {
    return visibility;
  }


  // ======================== Translation Methods ===================

  /**
    * Translates the field and adds it 
    * to the output stream.
    *
    * @param out The output stream.
    *
    * @return The output stream.
    */
  public Printer translate(Printer out) {
    out.indent();
    type.translate(out).p(" ");
    int size = names.size();
    for (int i = 0; i < size; i++) {
      out.p(names.get(i));
      if (null != values.get(i)) {
        out.p(" = ");
        values.get(i).translate(out);
      }
      if (i < size - 1)
        out.p(", ");
    }
    return out.pln(";");
  }

}
