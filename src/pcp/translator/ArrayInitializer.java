/**
 * (VariableInitializer VariableInitializer*)/
 * ()
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ArrayInitializer extends TranslationVisitor {

  private List<VariableInitializer> variables;
  
  public ArrayInitializer(GNode n) {
    variables = new ArrayList<VariableInitializer>();
    visit(n);
  }
  
  public int size() {
    return variables.size();
  }
  
  public void visitVariableInitializer(GNode n) {
    variables.add(new VariableInitializer(n));
  }

}
