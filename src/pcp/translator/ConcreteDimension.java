/**
 * Expression
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ConcreteDimension extends TranslationVisitor {
  private Expression expression;

  public ConcreteDimension(GNode n) {
    visit(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
}
