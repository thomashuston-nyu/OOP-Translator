/**
 * Type Type*
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Implementation extends TranslationVisitor {

  private List<Type> implementation;
  
  public Implementation(GNode n) {
    implementation = new ArrayList<Type>();
    visit(n);
  }
  
  public void visitType(GNode n) {
    implementation.add(new Type(n));
  }

}
