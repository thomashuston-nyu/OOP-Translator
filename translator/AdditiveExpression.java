/**
 * (AdditiveExpression AdditiveOperator MultiplicativeExpression)/
 * yyValue:MultiplicativeExpression
 */

package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class AdditiveExpression extends TranslationVisitor {

  private AdditiveExpression additiveExpression;
  private String additiveOperator;
  private MultiplicativeExpression multiplicativeExpression;

  public AdditiveExpression(GNode n) {
    additiveOperator = null;
    visit(n);
  }

  public void visitAdditiveExpression(GNode n) {
    additiveExpression = new AdditiveExpression(n);
  }

  public void visitSymbol(GNode n) {
    additiveOperator = n.getString(0);
  }

  public void visitMultiplicativeExpression(GNode n) {
    multiplicativeExpression = new MultiplicativeExpression(n);
  }

}
