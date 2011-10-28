/**
 * (BitwiseAndExpression EqualityExpression)/
 * yyValue:EqualityExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BitwiseAndExpression extends Expression implements Translatable {

  private BitwiseAndExpression bitwiseAndExpression;
  private EqualityExpression equalityExpression;

  public BitwiseAndExpression(GNode n) {
    visit(n);
  }

  public void visitBitwiseAndExpression(GNode n) {
    bitwiseAndExpression = new BitwiseAndExpression(n);
  }

  public void visitEqualityExpression(GNode n) {
    equalityExpression = new EqualityExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
