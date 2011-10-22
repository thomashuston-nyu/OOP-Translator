/**
 * FloatingPointString
 */
package translator;

import xtc.tree.GNode;

public class FloatingPointLiteral extends PrimaryExpression {
  private double value;

  public FloatingPointLiteral(GNode n) {
    value = Double.parseString(n.getString(0));
  }

  public float getFloat() {
    return (float) value;
  }

  public double getDouble() {
    return value;
  }
}
