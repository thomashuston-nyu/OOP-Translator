/**
 * Expression null Identifier Arguments
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CallExpression extends Expression {
  
  private Expression expression;
  private String identifier;
  private Arguments arguments;

  public CallExpression(GNode n) {
    identifier = n.getString(2);
    visit(n);
  }

  public void visitArguments(GNode n) {
    arguments = new Arguments(n);
  }
  
  public void visitPrimaryIdentifier(GNode n) {
    expression = new PrimaryIdentifier(n);
  }
  
  public void visitSelectionExpression(GNode n) {
    expression = new SelectionExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    if ((identifier.equals("println") || identifier.equals("print")) && expression != null &&
        ((SelectionExpression)expression).getName().equals("System.out")) {
      s.append(identifier + "(" + arguments.getPrintCC(indent, className, variables) + ")");
    } else if (expression != null && expression instanceof PrimaryIdentifier) {
      for (Variable v : variables) {
        String current = ((PrimaryIdentifier)expression).getName();
        if (v.name.equals(current)) {
          if (!v.type.equals("bool") &&
              !v.type.equals("char") &&
              !v.type.equals("double") &&
              !v.type.equals("float") &&
              !v.type.equals("int32_t")) {
            s.append(current + "->__vptr->" + identifier + "(" + current);
            if (arguments.size() > 0)
              s.append(", " + arguments.getCC(indent, className, variables));
            s.append(")");
          }
          break;
        }
      }
    } else {
      s.append("__this->__vptr->" + identifier + "(__this");
      if (arguments.size() > 0)
        s.append(", " + arguments.getCC(indent, className, variables));
      s.append(")");
    }
    return s.toString();
  }
  
}
