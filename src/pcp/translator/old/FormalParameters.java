/**
 * FormalParameter FormalParameter*
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class FormalParameters extends TranslationVisitor implements Iterable<FormalParameter> {
  
  private List<FormalParameter> parameters;
  
  public FormalParameters(GNode n) {
    parameters = new ArrayList<FormalParameter>();
    visit(n);
  }
  
  public FormalParameter get(int index) {
    return parameters.get(index);
  }

  public String getParameters() {
    StringBuilder s = new StringBuilder();
    int size = parameters.size();
    for (int i = 0; i < size; i++) {
      s.append(parameters.get(i).getParameter());
      if (i < size - 1) {
        s.append(", ");
      }
    }
    return s.toString();
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
