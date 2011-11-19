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
 * A Java primitive type, class type, or void type.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 1.1
 */
public class JavaType extends Visitor implements Translatable {
  
  // Map from Java primitive types to C++ primitive types
  // Includes void for convenience
  public final static Map<String, String> primitives = new HashMap<String, String>();
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
  private JavaPackage pkg;
  private String classType, primitiveType;
  

  // =========================== Constructors =======================
  
  /**
   * Creates the type from a GNode.
   *
   * @param n The type node.
   */
  public JavaType(GNode n) {
    dispatch(n);
  }

  /**
   * Creates the type from a string.
   *
   * @param type The type.
   */
  public JavaType(String type) {
    if (primitives.containsKey(type))
      primitiveType = type;
    else
      classType = type;
  }

  /**
   * Creates the type from a string and dimensions.
   *
   * @param type The type.
   * @param dim The dimensions of the array.
   */
  public JavaType(String type, int dim) {
    this(type);
    dimensions = dim;
  }


  // ============================ Get Methods =======================

  /**
   * Gets the type stored in the array.
   *
   * @return The type stored in the array.
   */
  public JavaType getArrayType() {
    if (0 == dimensions)
      return null;
    if (null != primitiveType)
      return new JavaType(primitiveType);
    else
      return new JavaType(classType);
  }

  /**
   * Gets the dimensions if it's an array.
   *
   * @return The dimensions if it's an array;
   * <code>0</code> otherwise.
   */
  public int getDimensions() {
    return dimensions;
  }

  /**
   * Gets the file for the class type.
   *
   * @return The file.
   */
  public JavaFile getFile() {
    if (null == classType)
      return null;
    if (Global.files.containsKey(getPath()))
      return Global.files.get(getPath());
    else
      Global.runtime.errConsole().p("File not found for type: ").pln(getPath()).flush();
    return null;
  }

  /**
   * Gets the path to the class type.
   *
   * @return The path.
   */
  public String getPath() {
    if (null == classType)
      return null;
    if (null != pkg) {
      return pkg.getPath() + "/" + classType + ".java";
    } else {
      return classType + ".java";
    }
  }

  /**
   * Gets the C++ primitive type or class reference.
   *
   * @return The C++ type.
   */
  public String getType() {
    StringBuilder type = new StringBuilder();
    for (int i = 0; i < dimensions; i++)
      type.append("__rt::Array<");
    if (null != primitiveType) {
      type.append(primitives.get(primitiveType));
    } else {
      if (null != pkg)
        type.append(pkg.getNamespace()).append("::");
      type.append(classType);
    }
    for (int i = 0; i < dimensions; i++) {
      type.append(">");
      if (i < dimensions - 1)
        type.append("*");
    }
    return type.toString();
  }

  /**
   * Checks if this type is equal to the specified Java type.
   *
   * @return <code>True</code> if it is the specified type;
   * <code>false</code> otherwise.
   */
  public boolean hasType(String type) {
    if (null != primitiveType && primitiveType.equals(type))
      return true;
    if (null != classType && classType.equals(type))
      return true;
    return false;
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

  /**
   * Checks if the type is primitive.
   *
   * @return <code>True</code> if it is primitive;
   * <code>false</code> otherwise.
   */
  public boolean isPrimitive() {
    return 0 == dimensions && null != primitiveType;
  }


  // ============================ Set Methods =======================

  /**
   * Set the package of the class.
   *
   * @param pkg The package.
   */
  public void setPackage(JavaPackage pkg) {
    this.pkg = pkg;
  }

  /**
   * Set the dimensions of the array.
   *
   * @param dim The dimensions.
   */
  public void setDimensions(int dim) {
    if (dim >= 0)
      dimensions = dim;
    else
      Global.runtime.errConsole().p("Invalid array dimensions: ").pln(dim).flush();
  }


  // =========================== Visit Methods ======================

  /**
   * Sets the primitive type.
   *
   * @param n The primitive type node.
   */
  public void visitPrimitiveType(GNode n) {
    primitiveType = n.getString(0);
  }

  /**
   * Sets the package and class type.
   *
   * @param n The qualified identifier node.
   */
  public void visitQualifiedIdentifier(GNode n) {
    List<String> pkg = new ArrayList<String>();
    int size = n.size();
    for (int i = 0; i < size; i++) {
      if (i < size - 1)
        pkg.add(n.getString(i));
      else
        classType = n.getString(i);
    }
    if (0 < pkg.size())
      this.pkg = new JavaPackage(pkg);
  }

  /**
   * Sets the primitive or class type.
   *
   * @param n The type node.
   */
  public void visitType(GNode n) {
    dispatch(n.getNode(0));
    if (null != n.get(1))
      dimensions = n.getNode(1).size();
  }

  /**
   * Sets the type to void.
   *
   * @param n The void type node.
   */
  public void visitVoidType(GNode n) {
    primitiveType = "void"; 
  }


  // ======================== Translation Methods ===================

  /**
    * Translates the type and adds it 
    * to the output stream.
    *
    * @param out The output stream.
    *
    * @return The output stream.
    */
  public Printer translate(Printer out) {
    return out.p(getType());
  }

}
