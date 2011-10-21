/**
 * FormalParameter FormalParameter*
 */
package translator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class FormalParameters extends TranslationVisitor implements Iterable<FormalParameter> {
  
  private List<FormalParameter> parameters;
  
  public FormalParameters(GNode n) {
    parameters = new ArrayList<FormalParameter>();
    visit(n);
  }
  
  public FormalParameter get(int index) {
    return paremeters.get(index);
  }
  
  public Iterator<FormalParameter> iterator() {
    Iterator<FormalParameter> it = parameters.iterator();
    return it;
  }
  
  public int size() {
    return parameters.size();
  }
  
  public void visitFormalParameter(GNode n) {
    parameters.add(new FormalParameter(n));
  }
  
}