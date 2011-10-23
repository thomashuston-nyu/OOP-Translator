/**
 * Identifier
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PrimaryIdentifier extends Expression implements Translatable {

  public String identifier;

  public PrimaryIdentifier(GNode n) {
    identifier = n.getString(0);
  }
  
  public String getCC(String className, int indent) {
    return identifier;
  }
  
}
