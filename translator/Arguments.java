/**
 * (Expression Expression*)/
 * ()
 */
package translator;

import java.util.List;
import java.util.ArrayList;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Arguments extends TranslationVisitor {
  private List<Expression> expressions;

  public Arguments(GNode n) {
    expressions = new ArrayList<Expression>();
  }

  public void visitExpression(GNode n) {
    expressions.add(n);
  }
}
