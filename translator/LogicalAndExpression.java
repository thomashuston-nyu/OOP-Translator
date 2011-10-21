/**
 * (LogicalAndExpression BitwiseOrExpression)/
 * yyValue:BitwiseOrExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class LogicalAndExpression extends TranslationVisitor {
  
  public LogicalAndExpression(GNode n) {
    visit(n);
  }
  
}
