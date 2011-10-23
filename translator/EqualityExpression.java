/**
 * (EqualityExpression EqualityOperator InstanceOfExpression)/
 * yyValue:InstanceOfExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class EqualityExpression extends Expression implements Translatable {
    
  private EqualityExpression equalityExpression;
  private String equalityOperator;
  private InstanceOfExpression instanceOfExpression;

  public EqualityExpression(GNode n) {
    equalityOperator = null;
    visit(n);
  }

  public void visitEqualityExpression(GNode n) {
    equalityExpression = new EqualityExpression(n);
  }

  public void visitSymbol(GNode n) {
    equalityOperator = n.getString(0);
  }

  public void visitInstanceOfExpression(GNode n) {
    instanceOfExpression = new InstanceOfExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }

}
