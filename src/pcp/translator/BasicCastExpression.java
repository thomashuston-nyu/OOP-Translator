/**
 * PrimitiveType Dimensions? UnaryExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BasicCastExpression extends Expression implements Translatable {
  private PrimitiveType primitiveType;
  private boolean isArray;
  private UnaryExpression unaryExpression;

  public BasicCastExpression(GNode n) {
    isArray = false;
    visit(n);
  }

  public void visitPrimitiveType(GNode n) {
    primitiveType = new PrimitiveType(n);
  }

  public void visitDimensions(GNode n) {
    isArray = true;
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
