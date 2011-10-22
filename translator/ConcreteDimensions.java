/**
 * ConcreteDimension+
 */
package translator;

import java.util.List;
import java.util.ArrayList;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ConcreteDimensions extends TranslationVisitor {
  private List<ConcreteDimension> concreteDimensions;

  public ConcreteDimensions(GNode n) {
    concreteDimensions = new ArrayList<ConcreteDimension>();
    visit(n);
  }

  public void visitConcreteDimension(GNode n) {
    concreteDimensions.add(n);
  }
}
