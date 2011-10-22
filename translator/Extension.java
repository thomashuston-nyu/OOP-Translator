/**
 * Type Type*
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Extension extends TranslationVisitor {

  private List<Type> extension;
  
  public Extension(GNode n) {
    extension = new ArrayList<Type>();
    visit(n);
  }
  
  public void visitType(GNode n) {
    extension.add(new Type(n));
  }

}