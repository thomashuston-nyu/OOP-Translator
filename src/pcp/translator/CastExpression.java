/**
 * Type UnaryExpressionNotPlusMinus
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CastExpression extends Expression implements Translatable {
  
  private JavaType type;

  public CastExpression(GNode n) {
    visit(n);
  }

  public void visitType(GNode n) {
    type = new JavaType(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}