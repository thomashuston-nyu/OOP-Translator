/**
 * Expression Expression*
 */

package translator;

import java.util.List;
import java.util.ArrayList;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class ExpressionList {

  private List<Expression> expression;

  public ExpressionList(GNode n) {
    expression = new ArrayList<Expression>();
    visit(n);
  }

  public void visitExpression(GNode n) {
    expression.add(new Expression(n));
  }

}
