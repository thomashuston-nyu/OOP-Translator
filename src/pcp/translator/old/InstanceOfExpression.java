/**
 * (RelationalExpression Type)/
 * yyValue:RelationalExpression
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class InstanceOfExpression extends Expression implements Translatable {
  
  private RelationalExpression relationalExpression;
  private JavaType type;

  public InstanceOfExpression(GNode n) {
    visit(n);
  }

  public void visitRelationalExpression(GNode n) {
    relationalExpression = new RelationalExpression(n);
  }

  public void visitType(GNode n) {
    type = new JavaType(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
