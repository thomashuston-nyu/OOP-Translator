/**
 * HexLiteral/
 * OctalLiteral/
 * DecimalLiteral
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class IntegerLiteral extends PrimaryExpression {

  private String value;

  public IntegerLiteral(GNode n) {
    value = n.getString(0);
  }
}
