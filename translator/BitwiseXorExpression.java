/**
 * (BitwiseXorExpression BitwiseAndExpression)/
 * yyValue:BitwiseAndExpression
 */

package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BitwiseXorExpression extends Expression {

  private BitwiseXorExpression bitwiseXorExpression;
  private BitwiseAndExpression bitwiseAndExpression;

  public BitwiseXorExpression(GNode n) {
    visit(n);
  }

  public void visitBitwiseXorExpression(GNode n) {
    bitwiseXorExpression = new BitwiseXorExpression(n);
  }

  public void visitBitwiseAndExpression(GNode n) {
    bitwiseAndExpression = new BitwiseAndExpression(n);
  }
  
  public String getCC() {
    return "";
  }

}
