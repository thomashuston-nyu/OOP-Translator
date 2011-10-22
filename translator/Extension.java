/**
 * Type Type*
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Extension extends TranslationVisitor {

  private Type extension;
  
  public Extension(GNode n) {
    visit(n);
  }
  
  public void visitType(GNode n) {
    this.extension = new Type(n);
  }

}