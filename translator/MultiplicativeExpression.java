/**
 * (MultiplicativeExpression MultiplicativeOperator UnaryExpression)/
 * yyValue:UnaryExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class MultiplicativeExpression extends Expression implements Translatable {
  
  private MultiplicativeExpression multiplicativeExpression;
  private String multiplicativeOperator;
  private UnaryExpression unaryExpression;

  public MultiplicativeExpression(GNode n) {
    multiplicativeOperator = null;
    visit(n);
  }

  public void visitMultiplicativeExpression(GNode n) {
    multiplicativeExpression = new MultiplicativeExpression(n);
  }

  public void visitSymbol(GNode n) {
    multiplicativeOperator = n.getString(0);
  }

  public void visitUnaryExpression(GNode n) {
    unaryExpression = new UnaryExpression(n);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}
