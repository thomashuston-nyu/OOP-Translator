/**
 * (AdditiveExpression AdditiveOperator MultiplicativeExpression)/
 * yyValue:MultiplicativeExpression
 */

package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class AdditiveExpression extends Expression {

  private AdditiveExpression additiveExpression;
  private String additiveOperator;
  private MultiplicativeExpression multiplicativeExpression;

  public AdditiveExpression(GNode n) {
    additiveOperator = n.getString(1);
    visit(n);
  }

  public void visitAdditiveExpression(GNode n) {
    additiveExpression = new AdditiveExpression(n);
  }

  public void visitMultiplicativeExpression(GNode n) {
    multiplicativeExpression = new MultiplicativeExpression(n);
  }
  
  public String getCC() {
    StringBuilder s = new StringBuilder();
    return s.toString();
  }

}
