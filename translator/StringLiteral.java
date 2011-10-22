/**
 * StringConstant
 */
package translator;

import xtc.tree.GNode;

public class StringLiteral extends PrimaryExpression {
  private String string;
  
  public StringLiteral(GNode n) {
    string = n.getString(0);
  }
}
