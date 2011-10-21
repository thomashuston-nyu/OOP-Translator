package translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class ClassBody extends TranslationVisitor {
  
  private List<FieldDeclaration> fields;
  private Map<Visibility, List<MethodDeclaration>> methods;
  
  public ClassBody(GNode n) {
    fields = new ArrayList<FieldDeclaration>();
    methods = new HashMap<Visibility, List<MethodDeclaration>>();
  }
  
  public void visitBlockDeclaration(GNode n) {
    
  }
  
  public void visitConstructorDeclaration(GNode n) {
    
  }
  
  public void visitEmptyDeclaration(GNode n) {
    
  }
  
  public void visitFieldDeclaration(GNode n) {
    fields.add(new FieldDeclaration(n));
  }
  
  public void visitInterfaceDeclaration(GNode n) {
    
  }
  
  public void visitMethodDeclaration(GNode n) {
    MethodDeclaration m = new MethodDeclaration(n);
    Visibility v = m.getVisibility();
    if (!methods.containsKey(v))
      methods.put(v, new ArrayList<MethodDeclaration>());
    methods.get(v).add(m);
  }
  
  
/*  public StringBuilder translate(int indent) {
    StringBuilder translation = new StringBuilder();
    if (publicMethods.size() > 0) {
      translation.append(getIndent(indent) + "public:\n");
      for (MethodDeclaration method : publicMethods) {
        translation.append(method.translate(indent+1).toString() + "\n");
      }
    }
    if (privateMethods.size() > 0) {
      translation.append(getIndent(indent) + "private:\n");
      for (MethodDeclaration method : privateMethods) {
        translation.append(method.translate(indent+1).toString() + "\n");
      }
    }
    if (protectedMethods.size() > 0) {
      translation.append(getIndent(indent) + "protected:\n");
      for (MethodDeclaration method : protectedMethods) {
        translation.append(method.translate(indent+1).toString() + "\n");
      }
    }
    return translation;
  }
  
  public StringBuilder translateHeader(int indent, String name) {
    StringBuilder translation = new StringBuilder();
    String ind = getIndent(indent);
    translation.append(ind + "__" + name + "_VT* __vptr;\n");
    for (FieldDeclaration field : fields) {
      translation.append(field.translate(indent).toString() + ";\n");
    }
    translation.append("\n");
    for (MethodDeclaration m : publicMethods) {
      if (!m.isStatic())
        translation.append(m.translateHeader(indent,name).toString() + "\n");
    }
    return translation;
  }
  
  public StringBuilder translateVT(int indent) {
    StringBuilder translation = new StringBuilder();
    return translation;
  }
  
  public StringBuilder translateBody(int indent) {
    StringBuilder translation = new StringBuilder();
    return translation;
  }*/

}