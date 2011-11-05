/**
 * "byte":Word/
 * "short":Word/
 * "char":Word/
 * "int":Word/
 * "long":Word/
 * "float":Word/
 * "double":Word/
 * "boolean":Word
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PrimitiveType extends TranslationVisitor {
  private String type;

  public PrimitiveType(GNode n) {
    type = n.getString(0);
    if (type.equals("int"))
      type = "int32_t";
    else if (type.equals("boolean"))
      type = "bool";
  }
  
  public String getType() {
    return type;
  }
  
}
