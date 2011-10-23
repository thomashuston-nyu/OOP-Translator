/**
 * null null Identifier Arguments
 */
package translator;

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
  
  public String getCC(String className, int indent) {
    return getIndent(indent) + identifier + "(" + arguments.getCC(className,indent) + ");\n";
  }
  
}
