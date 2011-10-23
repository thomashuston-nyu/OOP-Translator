/**
 * StringConstant
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class StringLiteral extends Expression implements Translatable {
  
  private String string;
  
  public StringLiteral(GNode n) {
    string = n.getString(0);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return string;
  }
  
}
