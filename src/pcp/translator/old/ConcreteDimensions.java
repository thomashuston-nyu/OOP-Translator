/**
 * ConcreteDimension+
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ConcreteDimensions extends TranslationVisitor {

  private List<ConcreteDimension> concreteDimensions;
  private Integer integer;

  public ConcreteDimensions(GNode n) {
    concreteDimensions = new ArrayList<ConcreteDimension>();
    integer = null;
    visit(n);
  }
  
  public void visitIntegerLiteral(GNode n) {
    integer = Integer.parseInt(n.getString(0));
  }

  public void visitConcreteDimension(GNode n) {
    concreteDimensions.add(new ConcreteDimension(n));
  }
  
  public String getDimensions() {
    if (integer != null) 
      return integer.toString();
    else
      return "";
  }

}
