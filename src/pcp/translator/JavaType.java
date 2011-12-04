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
 * @version 2.0
 */
public class JavaType extends Visitor implements Translatable {
  
  // Map from Java primitive types to C++ primitive types
  // Includes void for convenience
  public final static Map<String, String> primitives = new HashMap<String, String>();
  static {
    primitives.put("byte", "unsigned char");
    primitives.put("short", "int16_t");
    primitives.put("int", "int32_t");
    primitives.put("long", "int64_t");
    primitives.put("float", "float");
    primitives.put("double", "double");
    primitives.put("boolean", "bool");
    primitives.put("char", "char");
    primitives.put("void", "void");
  }

  // Map from C++ primitive types to the corresponding wrapper classes
  private final static Map<String, String> primitiveWrappers = new HashMap<String, String>();
  static {
    primitiveWrappers.put("unsigned char", "Byte");
    primitiveWrappers.put("int16_t", "Short");
    primitiveWrappers.put("int32_t", "Integer");
    primitiveWrappers.put("int64_t", "Long");
    primitiveWrappers.put("float", "Float");
    primitiveWrappers.put("double", "Double");
    primitiveWrappers.put("bool", "Boolean");
    primitiveWrappers.put("char", "Character");
  }

  // Map from C++ primitive types to the Java class letter
  private final static Map<String, Character> primitiveLetters = new HashMap<String, Character>();
  static {
    primitiveLetters.put("unsigned char", 'B');
    primitiveLetters.put("int16_t", 'S');
    primitiveLetters.put("int32_t", 'I');
    primitiveLetters.put("int64_t", 'J');
    primitiveLetters.put("float", 'F');
    primitiveLetters.put("double", 'D');
    primitiveLetters.put("bool", 'Z');
    primitiveLetters.put("char", 'C');
  }

  // Map from classes to their superclass
  private static Map<String, String> hierarchy = new HashMap<String, String>();
  static {
    hierarchy.put("Object", null);
    hierarchy.put("Class", "Object");
    hierarchy.put("String", "Object");
  }

  // Map from types to the maximum dimension of arrays of that type
  private static Map<String, Integer> arrays = new HashMap<String, Integer>();

  // The dimensions of this type
  private int dimensions;

  // Whether this type is static
  private boolean isStatic;

  // The package the class type refers to
  private JavaPackage pkg;

  // The class or primitive type
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
    setDimensions(dim);
  }

  /**
   * Creates the type from a string, package, and dimensions.
   *
   * @param type The type.
   * @param pkg The package.
   * @param dim The dimensions of the array.
   */
  public JavaType(String type, JavaPackage pkg, int dim) {
    this(type);
    setDimensions(dim);
    this.pkg = pkg;
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
    JavaType t;
    if (null != primitiveType) {
      t = new JavaType(primitiveType);
    } else {
      t = new JavaType(classType);
    }
    t.setDimensions(dimensions - 1);
    return t;
  }

  /**
   * Gets the unqualified class type.
   *
   * @return The class type.
   */
  public String getClassType() {
    return classType;
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
    if (null != JavaFile.getJavaFile(getPath()))
      return JavaFile.getJavaFile(getPath());
    else
      pcp.Translator.errConsole.p("File not found for type: ").pln(getPath()).flush();
    return null;
  }

  /**
   * Gets the Java type.
   *
   * @return The type.
   */
  public String getJavaType() {
    if (null != primitiveType)
      return primitiveType;
    if (null != classType)
      return classType;
    return null;
  }
  
  /**
   * Gets the mangled C++ type.
   *
   * @return The type.
   */
  public String getMangledType() {
    if (0 == dimensions)
      return getType();
    StringBuilder s = new StringBuilder();
    s.append("array" + dimensions + "_");
    if (null != primitiveType)
      s.append(primitives.get(primitiveType).replace(' ','_'));
    else
      s.append(classType);
    return s.toString();
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
    for (int i = 0; i < dimensions; i++) {
      if (i > 0)
        type.append("__rt::Ptr<");
      type.append("__rt::Array<");
    }
    if (null != primitiveType) {
      type.append(primitives.get(primitiveType));
    } else {
      if (null != pkg)
        type.append(pkg.getNamespace()).append("::");
      type.append(classType);
    }
    for (int i = 0; i < dimensions; i++) {
      type.append(" >");
      if (i < dimensions - 1)
        type.append(" >");
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

  /**
   * Checks if the type is static.
   *
   * @return <code>True</code> if it is static;
   * <code>false</code> otherwise.
   */
  public boolean isStatic() {
    return isStatic;
  }


  // ============================ Set Methods =======================

  /**
   * Set the dimensions of the array.
   *
   * @param dim The dimensions.
   */
  public void setDimensions(int dim) {
    if (dim >= 0)
      dimensions = dim;
    else
      pcp.Translator.errConsole.p("Invalid array dimensions: ").pln(dim).flush();
    String type;
    if (null != primitiveType)
      type = primitiveType;
    else
      type = classType;
    if (arrays.containsKey(type)) {
      if (arrays.get(type) < dimensions)
        arrays.put(type, dimensions);
    } else {
      arrays.put(type, dimensions);
    }
  }

  /**
   * Set the package of the class.
   *
   * @param pkg The package.
   */
  public void setPackage(JavaPackage pkg) {
    this.pkg = pkg;
  }

  /**
   * Marks this as a static type.
   */
  public void setStatic() {
    isStatic = true;
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

  /**
   * Creates the array template for the type and
   * adds it to the output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translateArraySpecialization(Printer out) {
    out.indent().pln("template<>");
    out.indent().p("java::lang::Class Array<");
    for (int i = 1; i < dimensions; i++) {
      out.p("Ptr<Array<");
    }
    if (null != classType) {
      if (null != pkg)
        out.p(pkg.getNamespace()).p("::");
      else if (classType.equals("Object") || classType.equals("Class") 
          || classType.equals("String"))
        out.p("java::lang::");
      out.p(classType);
    } else {
      out.p(primitives.get(primitiveType));
    }
    for (int i = 1; i < dimensions; i++) {
      out.p(" > >");
    }
    out.pln(" >::__class() {").incr();
    out.indent().pln("static java::lang::Class k = ");
    out.indentMore().p("new java::lang::__Class(literal(\"");
    for (int i = 1; i < dimensions; i++) {
      out.p("[");
    }
    out.p("[");
    if (null != classType) {
      out.p("L");
      if (null != pkg)
        out.p(pkg.getPackagename()).p("::");
      else if (classType.equals("Object") || classType.equals("Class") 
          || classType.equals("String"))
        out.p("java.lang.");
      out.p(classType);
    } else {
      out.p(primitiveLetters.get(primitives.get(primitiveType)));
    }
    if (null != classType)
      out.p(";");
    out.pln("\"),").incr();
    if (1 < dimensions) {
      for (int i = 0; i < 2; i++) {
        out.indentMore().p("Array<");
        if (1 == dimensions) {
          out.p("java::lang::Object");
        } else {
          for (int j = 2; j < dimensions; j++) {
            out.p("Ptr<Array<");
          }
          if (null != classType) {
            if (null != pkg)
              out.p(pkg.getNamespace()).p("::");
            else if (classType.equals("Object") || classType.equals("Class") 
                || classType.equals("String"))
              out.p("java::lang::");
            out.p(classType);
          } else {
            out.p(primitives.get(primitiveType));
          }
          for (int j = 2; j < dimensions; j++) {
            out.p(" > >");
          }
        }
        out.p(" >::__class()");
        if (0 == i)
          out.pln(",");
        else
          out.pln(");").decr();
      }
    } else {
      out.indentMore().pln("Array<java::lang::Object >::__class(),");
      out.indentMore();
      if (null != pkg)
        out.p(pkg.getNamespace()).p("::");
      else if (null != primitiveType || classType.equals("Object") ||
          classType.equals("Class") || classType.equals("String"))
        out.p("java::lang::");
      out.p("__");
      if (null != classType)
        out.p(classType).pln("::__class());").decr();
      else
        out.p(primitiveWrappers.get(primitives.get(primitiveType))).pln("::TYPE());").decr();
    }
    out.indent().pln("return k;");
    return out.decr().indent().pln("}").pln();
  }


  // ========================== Static Methods ======================

  /**
   * Adds a type to the hierarchy.
   *
   * @param type The name of the class.
   * @param parent The name of its parent class.
   */
  public static void addType(String type, String parent) {
    hierarchy.put(type, parent);
  }

  /**
   * Gets the mapping of array types to maximum dimensions.
   *
   * @return The array mapping.
   */
  public static Map<String, Integer> getArrayDimensions() {
    return arrays;
  }

  /**
   * Gets the class hierarchy.
   *
   * @return The class hierarchy.
   */
  public static Map<String, String> getHierarchy() {
    return hierarchy;
  }

  /**
   * Checks if the specified type is primitive.
   *
   * @param type The type to check.
   *
   * @return <code>True</code> if the type is primitive;
   * <code>false</code> otherwise.
   */
  public static boolean isPrimitive(String type) {
    return primitives.containsKey(type);
  }

}
