/**
 * (BitwiseAndExpression EqualityExpression)/
 * yyValue:EqualityExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BitwiseAndExpression extends Expression implements Translatable {

  private BitwiseAndExpression bitwiseAndExpression;
  private EqualityExpression equalityExpression;

  public BitwiseAndExpression(GNode n) {
    visit(n);
  }

  public void visitBitwiseAndExpression(GNode n) {
    bitwiseAndExpression = new BitwiseAndExpression(n);
  }

  public void visitEqualityExpression(GNode n) {
    equalityExpression = new EqualityExpression(n);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}
