/**
 * VoidType/
 * Type
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ClassLiteralExpression extends Expression implements Translatable {

  private JavaType returnType;
  
  public ClassLiteralExpression(GNode n) {
    visit(n);
  }
  
  public void visitType(GNode n) {
    returnType = new JavaType(n);
  }
  
  public void visitVoidType(GNode n) {
    returnType = new VoidType(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }

}
