/**
 * (Expression Expression*)/
 * ()
 */
package translator;

import java.util.List;
import java.util.ArrayList;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Arguments extends TranslationVisitor implements Translatable {
  
  private List<Expression> expressions;

  public Arguments(GNode n) {
    expressions = new ArrayList<Expression>();
    visit(n);
  }

  public void visitExpression(GNode n) {
    expressions.add(new Expression(n));
  }
  
  public void visitPrimaryIdentifier(GNode n) {
    expressions.add(new PrimaryIdentifier(n));
  }
  
  public String getCC(String className, int indent) {
    StringBuilder s = new StringBuilder();
    int size = expressions.size();
    for (int i = 0; i < size; i++) {
      s.append(expressions.get(i).getCC(className,indent));
      if (i < size - 1)
        s.append(", ");
    }
    return s.toString();
  }
  
}
