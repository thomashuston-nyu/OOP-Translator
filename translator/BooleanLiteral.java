/**
 * "true":Word/
 * "false":Word
 */
package translator;

import xtc.tree.GNode;

public class BooleanLiteral extends PrimaryExpression {
  private boolean value;

  public BooleanLiteral(GNode n) {
    if (n.getString(0).equals("true")) {
      value = true;
    } else {
      value = false;
    }
  }
}