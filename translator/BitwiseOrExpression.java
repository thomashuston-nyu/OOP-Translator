/**
 * (BitwiseOrExpression BitwiseXorExpression)/
 * yyValue:BitwiseXorExpression
 */

package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class BitwiseOrExpression extends TranslationVisitor{

  private BitwiseOrExpression bitwiseOrExpression;
  private BitwiseXorExpression bitwiseXorExpression;

  public BitwiseOrExpression(GNode n) {
    visit(n);
  }

  public void visitBitwiseOrExpression(GNode n) {
    bitwiseOrExpression = new BitwiseOrExpression(n);
  }

  public void visitBitwiseXorExpression(GNode n) {
    bitwiseXorExpression = new BitwiseXorExpression(n);
  }

}
