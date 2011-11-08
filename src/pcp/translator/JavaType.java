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

/**
 * A Java primitive type, class type, or void type.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class JavaType implements Translatable {
  
  private final static Map<String, String> primitives = new HashMap<String, String>();
  static {
    primitives.put("byte", "int8_t");
    primitives.put("short", "int16_t");
    primitives.put("int", "int32_t");
    primitives.put("long", "int64_t");
    primitives.put("float", "float");
    primitives.put("double", "double");
    primitives.put("boolean", "bool");
    primitives.put("char", "char");
    primitives.put("void", "void");
  }

  private int dimensions;
  private List<String> classType;
  private String primitiveType;
  
  /**
   * Creates the type.
   *
   * @param n The type node.
   */
  public JavaType(GNode n) {
    if (n.hasName("Type")) {
      if (n.getNode(0).hasName("PrimitiveType")) {
        primitiveType = n.getNode(0).getString(0);
      } else {
        classType = new ArrayList<String>();
        for (Object o : n.getNode(0)) {
          classType.add((String)o);
        }
      }
      if (n.size() == 2 && null != n.get(1)) {
        dimensions = n.getNode(1).size();
      } else {
        dimensions = 0;
      }
    } else if (n.hasName("VoidType")) {
      primitiveType = "void";
    } else if (n.hasName("PrimitiveType")) {
      primitiveType = n.getString(0);
    } else if (n.hasName("QualifiedIdentifier")) {
      classType = new ArrayList<String>();
      for (Object o : n) {
        classType.add((String)o);
      }
    } else {
      Global.runtime.errConsole().pln(n.toString()).flush();
    }
  }

  /**
   * Gets the number of array dimensions.
   *
   * @return The dimensions.
   */
  public int getDimensions() {
    return dimensions;
  }

  /**
   * Gets the path to the class reference.
   *
   * @return The path.
   */
  public String getPath() {
    if (classType == null)
      return null;
    StringBuilder s = new StringBuilder();
    int size = classType.size();
    for (int i = 0; i < size; i++) {
      s.append(classType.get(i));
      if (i < size - 1)
        s.append("/");
    }
    s.append(".java");
    return s.toString();
  }

  /**
   * Gets the class reference.
   *
   * @return The class reference.
   */
  public List<String> getReference() {
    return classType;
  }
  
  /**
   * Gets the C++ primitive type or class reference.
   *
   * @return The C++ type.
   */
  public String getType() {
    if (primitiveType != null) {
      return primitives.get(primitiveType);
    } else {
      Set<String> fileKeys = Global.files.keySet();
      for (String filepath : fileKeys) {
        JavaFile file = Global.files.get(filepath);
        if (null != file.getPackage()) {
          Global.runtime.console().pln(file.getPackage().getPath()).flush();
        }
      }
      return classType.get(0);
    }
  }

  /**
   * Checks if the type is an array.
   *
   * @return <code>True</code> if it is an array;
   * <code>false</code> otherwise.
   */
  public boolean isArray() {
    return 0 < dimensions;
  }

  public Printer translate(Printer out) {
    return out.p(getType());
  }

}
