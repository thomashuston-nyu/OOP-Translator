/**
 * PostfixExpression 
 * (null/
 * ((Identifier Arguments @CallExpression)/
 * ("super":Word Arguments @CallExpression))/
 * @SuperExpression/
 * Identifier @SelectionExpression/
 * Expression @SubscriptExpression/
 * "++":Symbol/
 * "--":Symbol/
 * (null (PrimitiveType/QualifiedIdentifier) Arguments ClassBody? @NewClassExpression))/
 * yyValue:PrimaryExpression
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PostfixExpression extends Expression implements Translatable {

  private PrimaryIdentifier primaryIdentifier;
  private String name;

  public PostfixExpression(GNode n) {
    name = n.getString(1);
    visit(n);
  }

  public void visitPrimaryIdentifier(GNode n) {
    primaryIdentifier = new PrimaryIdentifier(n);
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return primaryIdentifier.getCC(indent, className, variables) + name;
  }

}
