/**
 * PrimaryIdentifier Identifier
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class SelectionExpression extends Expression implements Translatable {
  
  private Expression expression;
  private String identifier;
  
  public SelectionExpression(GNode n) {
    identifier = n.getString(1);
    visit(n);
  }
  
  public void visitPrimaryIdentifier(GNode n) {
    expression = new PrimaryIdentifier(n);
  }
  
  public String getName() {
    return expression.getCC(0, null, null) + "." + identifier;
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return expression.getCC(indent, className, variables) + "::" + identifier;
  }
  
}
