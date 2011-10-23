/**
 * null null Identifier Arguments
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CallExpression extends Expression {
  
  private String identifier;
  private Arguments arguments;

  public CallExpression(GNode n) {
    identifier = n.getString(2);
    visit(n);
  }

  public void visitArguments(GNode n) {
    arguments = new Arguments(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    // TODO allow calling of methods from other classes
    StringBuilder s = new StringBuilder();
    s.append("__vptr->" + identifier + "(__this");
    if (arguments.size() > 0)
      s.append(", " + arguments.getCC(indent, className, variables));
    s.append(")");
    return s.toString();
  }
  
}
