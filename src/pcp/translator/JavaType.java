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

import java.util.HashMap;
import java.util.Map;

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * A Java type.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
/**
 * (PrimitiveType/QualifiedIdentifier) Dimensions?
 */
public class JavaType {
  
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
  }

  private int dimensions;
  private ClassReference classType;
  private String primitiveType;
  
  public JavaType(GNode n) {
    if (n.getNode(0).hasName("PrimitiveType")) {
      primitiveType = n.getNode(0).getString(0);
    } else {
      classType = new ClassReference(n);
    }
    if (n.size() == 2 && null != n.get(1)) {
      dimensions = n.getNode(1).size();
    } else {
      dimensions = 0;
    }
  }
  
  public String getType() {
    if (primitiveType != null)
      return primitives.get(primitiveType);
    else
      return classType.getPath();
  }

  public boolean isArray() {
    return 0 < dimensions;
  }

}
