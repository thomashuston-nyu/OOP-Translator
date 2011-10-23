/**
 * FloatingPointString
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class FloatingPointLiteral extends PrimaryExpression {
  private double value;

  public FloatingPointLiteral(GNode n) {
    value = Double.parseDouble(n.getString(0));
  }

  public float getFloat() {
    return (float) value;
  }

  public double getDouble() {
    return value;
  }
}
