/**
 * Identifier
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PrimaryIdentifier extends TranslationVisitor {
  public String identifier;

  public PrimaryIdentifier(GNode n) {
    identifer = n.getString(0);
    visit(n);
  }
}
