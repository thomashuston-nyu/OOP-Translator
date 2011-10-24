/**
 * FloatingPointString
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class FloatingPointLiteral extends Expression implements Translatable {
  
  private String value;

  public FloatingPointLiteral(GNode n) {
    value = n.getString(0);
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return value;
  }

}
