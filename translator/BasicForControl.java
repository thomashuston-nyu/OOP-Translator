/**
 * (VariableModifiers Type Declarators Expression? ExpressionList?)/
 * (null null ExpressionList? Expression? ExpressionList?)
 */
package translator;

import java.util.List;
import java.util.ArrayList;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class BasicForControl extends TranslationVisitor implements Translatable {
  
  private Modifiers modifiers;
  private Type type;
  private Declarators declarators;
  private Expression expression;
  private List<ExpressionList> expressionLists;

  public BasicForControl(GNode n) {
    expressionLists = new ArrayList<ExpressionList>();
    visit(n);
  }
  
  public void visitModifiers(GNode n) {
    modifiers = new Modifiers(n);
  }

  public void visitType(GNode n) {
    type = new Type(n);
  }

  public void visitDeclarators(GNode n) {
    declarators = new Declarators(n);
  }

  // expressions
  public void visitAdditiveExpression(GNode n) {
    expression = new AdditiveExpression(n);
  }

  public void visitCallExpression(GNode n) {
    expression = new CallExpression(n);
  }

  public void visitConditionalExpression(GNode n) {
    expression = new ConditionalExpression(n);
  }

  public void visitEqualityExpression(GNode n) {
    expression = new EqualityExpression(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }

  public void visitExpressionList(GNode n) {
    expressionLists.add(new ExpressionList(n));
  }

  public void visitMultiplicativeExpression(GNode n) {
    expression = new MultiplicativeExpression(n);
  }

  public void visitPrimaryIdentifier(GNode n) {
    expression = new PrimaryIdentifier(n);
  }

  public void visitPostfixExpression(GNode n) {
    expression = new PostfixExpression(n);
  }

  public void visitRelationalExpression(GNode n) {
    expression = new RelationalExpression(n);
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    s.append(type.getCC(indent, className, variables) + " " +  declarators.getCC(indent, className, variables) + "; ");
    s.append("; ");
    s.append("");
    return s.toString();
  }

}
