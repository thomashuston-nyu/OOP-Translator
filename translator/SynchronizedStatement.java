/**
 * Expression Block
 */
package translate;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class SynchronizedStatement extends TranslationVisitor {
  private Expression expression;
  private Block block;

  public SynchronizedStatement(GNode n) {
    visit(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

  public void visitBlock(GNode n) {
    block = new Block(n);
  }
}
