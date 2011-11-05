package pcp.translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ClassBody extends TranslationVisitor {
  
  private JavaConstructor constructor;
  private Map<Visibility, List<JavaField>> fields;
  private Map<Visibility, List<JavaMethod>> methods;
  
  public ClassBody(GNode n) {
    fields = new HashMap<Visibility, List<JavaField>>();
    methods = new HashMap<Visibility, List<JavaMethod>>();
    visit(n);
  }
  
  // declarations
  
  public String getConstructorDeclaration() {
   /* if (constructor != null)
      return constructor.getHeaderDeclaration();
    else*/
      return null;
  }
  
  public void visitBlockDeclaration(GNode n) {
    
  }
  
  public void visitConstructorDeclaration(GNode n) {
    constructor = new JavaConstructor(n);
  }
  
  public void visitEmptyDeclaration(GNode n) {
    
  }
  
  public void visitJavaFieldDeclaration(GNode n) {
    JavaField f = new JavaField(n);
    Visibility v = f.getVisibility();
    if (!fields.containsKey(v))
      fields.put(v, new ArrayList<JavaField>());
    fields.get(v).add(f);
  }
  
  public void visitInterfaceDeclaration(GNode n) {
    
  }
  
  public void visitJavaMethodDeclaration(GNode n) {
    JavaMethod m = new JavaMethod(n);
    Visibility v = m.getVisibility();
    if (!methods.containsKey(v))
      methods.put(v, new ArrayList<JavaMethod>());
    methods.get(v).add(m);
  }
  
  public List<JavaField> getFields(Visibility v) {
    if (fields.containsKey(v))
      return fields.get(v);
    else
      return null;
  }
  
  public List<JavaMethod> getMethods(Visibility v) {
    if (methods.containsKey(v))
      return methods.get(v);
    else
      return null;
  }
          /*
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    if (variables == null)
      variables = new ArrayList<Variable>();
    for (Visibility v : Visibility.values()) {
      if (fields.containsKey(v)) {
        List<JavaField> f = fields.get(v);
        for (JavaField fd : f) {
          Variable a = new Variable(fd.getType(), fd.getName());
          variables.add(a);
        }
      }
    }
    if (constructor != null)
      s.append(constructor.getCC(indent, className, variables) + "\n\n");
    variables = new ArrayList<Variable>();
    List<JavaMethod> l = methods.get(Visibility.PUBLIC);
    if (l != null) {
      for (JavaMethod m : l) {
        if (!m.isStatic() && !m.isFinal() && !m.isAbstract())
          s.append(m.getCC(indent, className, variables) + "\n");
      }
    }
    s.append(getIndent(indent) + "Class __" + className + "::__class() {\n");
    s.append(getIndent(++indent) + "static Class k = new __Class(__rt::literal(\"" +
             className + "\"), __Object::__class());\n");
    s.append(getIndent(indent) + "return k;\n");
    s.append(getIndent(--indent) + "}\n\n");
    s.append(getIndent(indent) + "__" + className + "_VT __" + className +
             "::__vtable;\n");
    if (l != null) {
      for (JavaMethod m : l) {
        if (m.getName().equals("main") && m.isStatic() && m.getReturnType().getType().equals("void")) {
          s.append("\n" + m.getMainCC(indent, className, variables) + "\n");
          break;
        }
      }
    }
    return s.toString();
  }*/
}
