/**
 * null null Identifier Arguments
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class CallExpression extends TranslationVisitor {
  private String identifier;
  private Arguments arguments;

  public CallExpression(GNode n) {
    n.getString(2);
    visit(n);
  }

  public void visitArguments(GNode n) {
    arguments = new Arguments(n);
  }
}
