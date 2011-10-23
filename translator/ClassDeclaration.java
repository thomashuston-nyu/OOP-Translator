package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * Parses an xtc ClassDeclaration node.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Marta Magdalena
 * @author Mike Morreale
 *
 * @version 0.1
 */
public class ClassDeclaration extends Declaration {
  
  private ClassBody body;
  private Extension extension;
  private List<Implementation> implementation;
  private boolean isAbstract;
  private boolean isFinal;
  private boolean isStatic;
  private String name;
  private Visibility visibility;
  
  /**
   * Constructs the ClassDeclaration.
   * 
   * @param n the ClassDeclaration node.
   */
  public ClassDeclaration(GNode n) {
    name = n.getString(1);
    extension = null;
    implementation = new ArrayList<Implementation>();
    isAbstract = false;
    isFinal = false;
    isStatic = false;
    visit(n);
  }
  
  /**
   * Gets the superclass.
   *
   * @return the extension.
   */
  public Extension getExtension() {
    return extension;
  }
  
  /**
   * Gets the list of implemented interfaces.
   *
   * @return the implementation list.
   */
  public List<Implementation> getImplementation() {
    return implementation;
  }
  
  public String getName() {
    return name;
  }
  
  /**
   * Gets the visibility of the class.
   *
   * @return the visibility.
   */
  public Visibility getVisibility() {
    return visibility;
  }
  
  public boolean isAbstract() {
    return isAbstract;
  }
  
  public boolean isFinal() {
    return isFinal;
  }
  
  public void visitClassBody(GNode n) {
    body = new ClassBody(n);
  }
  
  public void visitExtension(GNode n) {
    extension = new Extension(n);
  }
  
  public void visitImplementation(GNode n) {
    implementation.add(new Implementation(n));
  }
  
  public void visitModifiers(GNode n) {
    Modifiers modifiers = new Modifiers(n);
    for (String m : modifiers) {
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
  
  public String getHeaderStruct(int indent) {
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
    s.append("\n" + in + body.getConstructorDeclaration() + "\n\n");
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
    StringBuilder s = new StringBuilder();
    String in = getIndent(indent);
    s.append(in + "struct __" + name + "_VT {\n");
    in = getIndent(++indent);    
    List<MethodDeclaration> l = body.getMethods(Visibility.PUBLIC);
    MethodDeclaration hashCode = null;
    MethodDeclaration equals = null;
    MethodDeclaration getClass = null;
    MethodDeclaration toString = null;
    if (l != null) {
      for (MethodDeclaration m : l) {
        if (m.isFinal() || m.isStatic() || m.isAbstract())
          continue;
        if (m.getName().equals("hashCode")) {
          if (m.getReturnType().getType().equals("int") && m.getParameters().size() == 0)
            hashCode = m;
        } else if (m.getName().equals("equals")) {
          if (m.getReturnType().getType().equals("boolean") && m.getParameters().size() == 1 &&
              m.getParameters().get(0).getType().equals("Object"))
            equals = m;
        } else if (m.getName().equals("getClass")) {
          if (m.getReturnType().getType().equals("Class") && m.getParameters().size() == 0)
            getClass = m;
        } else if (m.getName().equals("toString")) {
          if (m.getReturnType().getType().equals("String") && m.getParameters().size() == 0)
            toString = m;
        }
      }
    }
    
    // isa
    s.append(in + "Class __isa;\n");
    
    // hashCode
    s.append(in + "int32_t (*hashCode)(");
    if (hashCode != null)
      s.append(name + ");\n");
    else
      s.append("Object);\n");
    
    // equals
    s.append(in + "bool (*equals)(");
    if (equals != null)
      s.append(name + ", " + name + ");\n");
    else
      s.append("Object, Object);\n");

    // getClass
    s.append(in + "Class (*getClass)(");
    if (getClass != null)
      s.append(name + ");\n");
    else
      s.append("Object);\n");
    
    // toString
    s.append(in + "String (*toString)(");
    if (toString != null)
      s.append(name + ");\n");
    else
      s.append("Object);\n");

    if (l != null) {
      for (MethodDeclaration m : l) {
        if (!m.isStatic() && m != hashCode && m != equals && m != getClass && m != toString) {
          s.append(in + m.getHeaderVTDeclaration(name) + "\n");
        }
      }
    }

    s.append("\n" + in + "__" + name + "_VT()\n" + in + ": ");
    s.append("__isa(__" + name + "::__class()),\n");
    
    // hashCode
    if (hashCode != null)
      s.append(in + "hashCode(&__" + name + "::hashCode),\n");
    else
      s.append(in + "hashCode(&__Object::hashCode),\n");
    
    // equals
    if (equals != null)
      s.append(in + "equals(&__" + name + "::equals),\n");
    else
      s.append(in + "equals(&__Object::equals),\n");

    // getClass
    s.append(in + "((Class(*)(" + name + "))&__Object::getClass),\n");
    
    // toString
    if (toString != null)
      s.append(in + "toString(&__" + name + "::toString)");
    else
      s.append(in + "toString(&__Object::toString)");
    
    if (l != null) {
      for (MethodDeclaration m : l) {
        if (!m.isStatic() && m != hashCode && m != equals && m != getClass && m != toString) {
          s.append(",\n" + in + m.getHeaderVTConstructor(name));
        }
      }
    }
    s.append(" {}\n");

    in = getIndent(--indent);
    s.append(in + "};\n");
    return s.toString();
  }

  public String getCC(int indent) {
    return body.getCC(indent);
  }
  
}
