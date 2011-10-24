/**
 * Expression null Identifier Arguments
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CallExpression extends Expression {
  
  private Expression expression;
  private String identifier;
  private Arguments arguments;

  public CallExpression(GNode n) {
    identifier = n.getString(2);
    visit(n);
  }

  public void visitArguments(GNode n) {
    arguments = new Arguments(n);
  }
  
  public void visitSelectionExpression(GNode n) {
    expression = new SelectionExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    // TODO allow calling of methods from other classes
    StringBuilder s = new StringBuilder();
    if ((identifier.equals("println") || identifier.equals("print")) && expression != null &&
        ((SelectionExpression)expression).getName().equals("System.out")) {
      s.append(identifier + "(" + arguments.getCC(indent, className, variables) + ")");
    } else {
      s.append("__this->__vptr->" + identifier + "(__this");
      if (arguments.size() > 0)
        s.append(", " + arguments.getCC(indent, className, variables));
      s.append(")");
    }
    return s.toString();
  }
  
}
