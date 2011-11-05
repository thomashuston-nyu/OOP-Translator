/**
 * (BitwiseOrExpression BitwiseXorExpression)/
 * yyValue:BitwiseXorExpression
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BitwiseOrExpression extends Expression implements Translatable {

  private BitwiseOrExpression bitwiseOrExpression;
  private BitwiseXorExpression bitwiseXorExpression;

  public BitwiseOrExpression(GNode n) {
    visit(n);
  }

  public void visitBitwiseOrExpression(GNode n) {
    bitwiseOrExpression = new BitwiseOrExpression(n);
  }

  public void visitBitwiseXorExpression(GNode n) {
    bitwiseXorExpression = new BitwiseXorExpression(n);
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }

}
