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
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PrimitiveType extends TranslationVisitor {
  private String type;

  public PrimitiveType(GNode n) {
    type = n.getString(0);
  }
}
