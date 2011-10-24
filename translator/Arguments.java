/**
 * (Expression Expression*)/
 * ()
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Arguments extends TranslationVisitor implements Translatable {
  
  private List<Expression> expressions;

  public Arguments(GNode n) {
    expressions = new ArrayList<Expression>();
    visit(n);
  }
  
  public int size() {
    return expressions.size();
  }

  public void visitExpression(GNode n) {
    expressions.add(new Expression(n));
  }
  
  public void visitFloatingPointLiteral(GNode n) {
    expressions.add(new FloatingPointLiteral(n));
  }
  
  public void visitPrimaryIdentifier(GNode n) {
    expressions.add(new PrimaryIdentifier(n));
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    int size = expressions.size();
    for (int i = 0; i < size; i++) {
      s.append(expressions.get(i).getCC(indent, className, variables));
      if (i < size - 1)
        s.append(", ");
    }
    return s.toString();
  }
  
  public String getPrintCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    Expression e = expressions.get(0);
    if (e instanceof PrimaryIdentifier) {
      String var = ((PrimaryIdentifier)e).getName();
      for (Variable v : variables) {
        if (v.name.equals(var)) {
          if (v.type.equals("boolean"))
            s.append("print_bool(" + var + ")");
        }
      }
    }
    return s.toString();
  }
  
}
