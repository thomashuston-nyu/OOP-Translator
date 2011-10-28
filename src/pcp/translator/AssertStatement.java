/**
 * Expression Expression?
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class AssertStatement extends Statement implements Translatable {

  private List<Expression> expressions;

  public AssertStatement(GNode n) {
    expressions = new ArrayList<Expression>();
    visit(n);
  }

  public void visitExpression(GNode n) {
    expressions.add(new Expression(n));
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
