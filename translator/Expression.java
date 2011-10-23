/**
 * (ConditionalExpression AssignmentOperator Expression)/
 * yyValue:ConditionalExpression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Expression extends PrimaryExpression implements Translatable {
  
  private ConditionalExpression conditionalExpression;
  private String assignmentOperator;
  private Expression expression;
  
  public Expression() {
    
  }
  
  public Expression(GNode n) {
    visit(n);
  }

  public void visitConditionalExpression(GNode n) {
    System.out.println("visit conditional");
    conditionalExpression = new ConditionalExpression(n);
  }

  public void visitSymbol(GNode n) {
    assignmentOperator = n.getString(0);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public String getCC(String className, int indent) {
    return "";
  }
  
}
