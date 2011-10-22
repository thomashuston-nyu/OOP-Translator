/**
 * Identifier Dimensions? (ArrayInitializer/Expression)?
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Declarator extends TranslationVisitor {
  
  private String name;
  
  public Declarator(GNode n) {
    name = n.getString(0);
    visit(n);
  }
  
  
  
}