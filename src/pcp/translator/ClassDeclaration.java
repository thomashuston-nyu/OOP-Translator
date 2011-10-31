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
import xtc.tree.Visitor;

/**
 * A class declaration.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class ClassDeclaration extends Declaration {
  
  private HashMap<String, List<Object>> vtable;
  private ClassDeclaration parent;
  
  private ClassBody body;
  private Extension extension;
  private List<Implementation> interfaces;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private String name;
  private Visibility visibility;
  
  /**
   * Constructs the ClassDeclaration.
   * 
   * @param n The ClassDeclaration node.
   */
  public ClassDeclaration(GNode n) {
    name = n.getString(1);
    interfaces = new ArrayList<Implementation>();
    isAbstract = false;
    isFinal = false;
    isStatic = false;
    visibility = Visibility.PACKAGE_PRIVATE;
    visit(n);
  }
  
  /**
   * Gets the superclass.
   *
   * @return The superclass.
   */
  public Extension getExtension() {
    return extension;
  }
  
  /**
   * Gets the list of implemented interfaces.
   *
   * @return The interfaces.
   */
  public List<Implementation> getImplementation() {
    return interfaces;
  }
  
  /**
   * Gets the name of the class.
   *
   * @return The name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Gets the superclass.
   *
   * @return The superclass.
   */
  public ClassDeclaration getParent() {
    return parent;
  }

  /**
   * Gets the visibility of the class.
   *
   * @return The visibility.
   */
  public Visibility getVisibility() {
    return visibility;
  }
  
  /**
   * Tests whether the class has a superclass.
   *
   * @return True if it has a superclass; false otherwise.
   */
  public boolean hasExtension() {
    return extension != null;
  }
  
  /**
   * Tests whether the class is abstract.
   *
   * @return True if the class is abstract; false otherwise.
   */
  public boolean isAbstract() {
    return isAbstract;
  }
  
  /**
   * Tests whether the class is final.
   *
   * @return True if the class is final; false otherwise.
   */
  public boolean isFinal() {
    return isFinal;
  }
  
  /**   
   * Sets the superclass.
   *
   * @param parent The superclass.
   */
  public void setParent(ClassDeclaration parent) {
    this.parent = parent;
  }
  
  /**
   * Visits the class body.
   *
   * @param n The AST node to visit.
   */
  public void visitClassBody(GNode n) {
    body = new ClassBody(n);
  }
  
  /**
   * Visits the extension.
   *
   * @param n The AST node to visit.
   */
  public void visitExtension(GNode n) {
    extension = new Extension(n);
  }
  
  /**
   * Visits an interface.
   *
   * @param n The AST node to visit.
   */
  public void visitImplementation(GNode n) {
    interfaces.add(new Implementation(n));
  }
  
  /**
   * Visits the modifiers.
   *
   * @param n The AST node to visit.
   */
  public void visitModifiers(GNode n) {
    Modifiers modifiers = new Modifiers(n);
    for (Object o : n) {
      String m = ((GNode)o).getString(0);
      if (m.equals("public"))
        visibility = Visibility.PUBLIC;
      else if (m.equals("private"))
        visibility = Visibility.PRIVATE;
      else if (m.equals("protected"))
        visibility = Visibility.PROTECTED;
      else if (m.equals("final"))
        isFinal = true;
      else if (m.equals("abstract"))
        isAbstract = true;
      else if (m.equals("static"))
        isStatic = true;
    }
  }
  
  
  
  public void createVTable() {
    vtable = new HashMap<String, List<Object>>();
    HashMap<String, List<Object>> parentVT = null;
    if (parent != null)
       parentVT = parent.getVTable();
    List<MethodDeclaration> l = body.getMethods(Visibility.PUBLIC);
    if (l != null) {
      for (MethodDeclaration m : l) {
        if (m.isFinal() || m.isStatic() || m.isAbstract())
          continue;
        if (m.getName().equals("hashCode") && 
            m.getReturnType().getType().equals("int") &&
            m.getParameters().size() == 0) {
          List<Object> o = new ArrayList<Object>();
          o.add(name);
          o.add(m);
          vtable.put("hashCode", o);
        } else if (m.getName().equals("equals") && 
                   m.getReturnType().getType().equals("boolean") && 
                   m.getParameters().size() == 1 &&
                   m.getParameters().get(0).getType().equals("Object")) {
          List<Object> o = new ArrayList<Object>();
          o.add(name);
          o.add(m);
          vtable.put("equals", o);
        } else if (m.getName().equals("toString") &&
                   m.getReturnType().getType().equals("String") && 
                   m.getParameters().size() == 0) {
          List<Object> o = new ArrayList<Object>();
          o.add(name);
          o.add(m);
          vtable.put("toString", o);
        } else {
          List<Object> o = new ArrayList<Object>();
          o.add(name);
          o.add(m);
          vtable.put(m.getName(), o);
        }
      }
    }
    
    if (!vtable.containsKey("hashCode")) {
      if (parentVT != null && parentVT.containsKey("hashCode")) {
        vtable.put("hashCode", parentVT.get("hashCode"));
      }
    }
    if (!vtable.containsKey("equals")) {
      if (parentVT != null && parentVT.containsKey("equals")) {
        vtable.put("equals", parentVT.get("equals"));
      }
    }
    if (!vtable.containsKey("toString")) {
      if (parentVT != null && parentVT.containsKey("toString")) {
        vtable.put("toString", parentVT.get("toString"));
      }
    }
    if (parentVT != null) {
      Set<String> keys = parentVT.keySet();
      for (String key : keys) {
        if (!vtable.containsKey(key))
          vtable.put(key, parentVT.get(key));
      }
    }
  }
  
  public HashMap<String, List<Object>> getVTable() {
    if (vtable == null)
      createVTable();
    return vtable;
  }
  
  public String getHeaderStruct(int indent) {
    if (vtable == null)
      createVTable();
    StringBuilder s = new StringBuilder();
    String in = getIndent(indent);
    s.append(in + "struct __" + name  + " {\n");
    in = getIndent(++indent);
    s.append(in + "__" + name + "_VT* __vptr;\n");
    for (Visibility v : Visibility.values()) {
      List<FieldDeclaration> fields = body.getFields(v);
      if (fields != null) {
        for (FieldDeclaration f : fields) {
          s.append(in + f.getDeclaration() + ";\n");
        }
      }
    }
    String construct = body.getConstructorDeclaration();
    if (construct != null)
      s.append("\n" + in + construct + "\n\n");
    List<MethodDeclaration> l = body.getMethods(Visibility.PUBLIC);
    if (l != null)
      for (MethodDeclaration m : l)
        if (!m.isStatic() && !m.isAbstract() && !m.isFinal())
          s.append(in + m.getHeaderDeclaration(name) + "\n");
    s.append("\n" + in + "static Class __class();\n\n");
    
    s.append(in + "static __" + name + "_VT __vtable;\n");
    in = getIndent(--indent);
    s.append(in + "};\n");
    return s.toString();
  }

  public String getHeaderVTStruct(int indent) {
    HashMap<String, List<Object>> parentVT = null;
    StringBuilder s = new StringBuilder();
    String in = getIndent(indent);
    s.append(in + "struct __" + name + "_VT {\n");
    in = getIndent(++indent);    
    List<MethodDeclaration> l = body.getMethods(Visibility.PUBLIC);
    
    s.append(in + "Class __isa;\n");
    
    if (vtable.containsKey("hashCode")) {
      s.append(in + ((MethodDeclaration)vtable.get("hashCode").get(1)).getHeaderVTDeclaration(name) + "\n");
    } else {
      s.append(in + "int32_t (*hashCode)(Object);\n");
    }
    if (vtable.containsKey("equals")) {
      s.append(in + ((MethodDeclaration)vtable.get("equals").get(1)).getHeaderVTDeclaration(name) + "\n");
    } else {
      s.append(in + "bool (*equals)(Object, Object);\n");
    }
    s.append(in + "Class (*getClass)(" + name + ");\n");
    if (vtable.containsKey("toString")) {
      s.append(in + ((MethodDeclaration)vtable.get("toString").get(1)).getHeaderVTDeclaration(name) + "\n");
    } else {
      s.append(in + "String (*toString)(" + name + ");\n");
    }

    Set<String> methods = vtable.keySet();
    for (String method : methods) {
      if (!method.equals("hashCode") &&
          !method.equals("equals") &&
          !method.equals("toString")) {
        s.append(in + ((MethodDeclaration)vtable.get(method).get(1)).getHeaderVTDeclaration(name) + "\n");
      }
    }

    s.append("\n" + in + "__" + name + "_VT()\n" + in + ": ");
    s.append("__isa(__" + name + "::__class())");
    
    if (vtable.containsKey("hashCode")) {
      List<Object> o = vtable.get("hashCode");
      String t = (String)o.get(0);
      MethodDeclaration m = (MethodDeclaration)o.get(1);
      if (!t.equals(name))
        s.append(",\n" + in + m.getHeaderVTConstructor(name, t));
      else
        s.append(",\n" + in + m.getHeaderVTConstructor(name, null));
    } else {
      s.append(",\n" + in + "hashCode(&__Object::hashCode)");
    }
    if (vtable.containsKey("equals")) {
      List<Object> o = vtable.get("equals");
      String t = (String)o.get(0);
      MethodDeclaration m = (MethodDeclaration)o.get(1);
      if (!t.equals(name))
        s.append(",\n" + in + m.getHeaderVTConstructor(name, t));
      else
        s.append(",\n" + in + m.getHeaderVTConstructor(name, null));
    } else {
      s.append(",\n" + in + "equals(&__Object::equals)");
    }
    s.append(",\n" + in + "getClass((Class(*)(" + name + "))&__Object::getClass)");
    if (vtable.containsKey("toString")) {
      List<Object> o = vtable.get("toString");
      String t = (String)o.get(0);
      MethodDeclaration m = (MethodDeclaration)o.get(1);
      if (!t.equals(name))
        s.append(",\n" + in + m.getHeaderVTConstructor(name, t));
      else
        s.append(",\n" + in + m.getHeaderVTConstructor(name, null));
    } else {
      s.append(",\n" + in + "toString((String(*)(" + name + "))&__Object::toString)");
    }
    
    for (String method : methods) {
      if (!method.equals("hashCode") &&
          !method.equals("equals") &&
          !method.equals("toString")) {
        List<Object> o = vtable.get(method);
        String t = (String)o.get(0);
        MethodDeclaration m = (MethodDeclaration)o.get(1);
        if (!t.equals(name))
          s.append(",\n" + in + m.getHeaderVTConstructor(name, t));
        else
          s.append(",\n" + in + m.getHeaderVTConstructor(name, null));
      }
    }
    
    s.append(" {}\n");

    in = getIndent(--indent);
    s.append(in + "};\n");
    return s.toString();
  }
  
  public String getHeader(int indent) {
    StringBuilder s = new StringBuilder();
    List<MethodDeclaration> l1 = body.getMethods(Visibility.PRIVATE);
    if (l1 != null) {
      for (MethodDeclaration m : l1) {
        s.append(m.getHeaderDeclaration(name) + "\n");
      }
    }
    List<MethodDeclaration> l2 = body.getMethods(Visibility.PUBLIC);
    if (l2 != null) {
      for (MethodDeclaration m : l2) {
        if (m.isStatic()) {
          s.append(m.getHeaderDeclaration(name) + "\n");
        }
      }
    }
    return s.toString();
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return body.getCC(indent, name, variables);
  }
  
}
