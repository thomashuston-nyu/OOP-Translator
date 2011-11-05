/**
 * HexLiteral/
 * OctalLiteral/
 * DecimalLiteral
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class IntegerLiteral extends Literal implements Translatable {

  private String value;

  public IntegerLiteral(GNode n) {
    value = n.getString(0);
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return value;
  }
  
  public String getPrintCC() {
    return "int_to_string(" + value + ")";
  }

}
