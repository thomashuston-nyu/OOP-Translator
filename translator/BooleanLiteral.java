/**
 * "true":Word/
 * "false":Word
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class BooleanLiteral extends Literal implements Translatable {
  
  private String value;

  public BooleanLiteral(GNode n) {
    if (n.getString(0).equals("true"))
      value = "true";
    else
      value = "false";
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return value;
  }
  
  public String getPrintCC() {
    return "bool_to_string(" + value + ")";
  }

}
