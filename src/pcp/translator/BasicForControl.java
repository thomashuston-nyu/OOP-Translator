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
  
  private Declarators declarators;
  private Expression expression;
  private ExpressionList expressions;
  private Modifiers modifiers;
  private Type type;

  public BasicForControl(GNode n) {
    visit(n);
  }
  
  public void visitDeclarators(GNode n) {
    declarators = new Declarators(n);
  }

  public void visitExpressionList(GNode n) {
    expressions = new ExpressionList(n);
  }

  public void visitModifiers(GNode n) {
    modifiers = new Modifiers(n);
  }

  public void visitType(GNode n) {
    type = new Type(n);
  }

  // expressions
  public void visitAdditiveExpression(GNode n) {
    expression = new AdditiveExpression(n);
  }

  public void visitBasicCastExpression(GNode n) {
    expression = new BasicCastExpression(n);
  }
  
  public void visitBitwiseAndExpression(GNode n) {
    expression = new BitwiseAndExpression(n);
  }

  public void visitBitwiseNegationExpression(GNode n) {
    expression = new BitwiseNegationExpression(n);
  }

  public void visitBitwiseOrExpression(GNode n) {
    expression = new BitwiseOrExpression(n);
  }

  public void visitBitwiseXorExpression(GNode n) {
    expression = new BitwiseXorExpression(n);
  }

  public void visitCallExpression(GNode n) {
    expression = new CallExpression(n);
  }

  public void visitCastExpression(GNode n) {
    expression = new CastExpression(n);
  }

  public void visitClassLiteralExpression(GNode n) {
    expression = new ClassLiteralExpression(n);
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

  public void visitInstanceOfExpression(GNode n) {
    expression = new InstanceOfExpression(n);
  }

  public void visitLogicalAndExpression(GNode n) {
    expression = new LogicalAndExpression(n);
  }

  public void visitLogicalNegationExpression(GNode n) {
    expression = new LogicalNegationExpression(n);
  }

  public void visitLogicalOrExpression(GNode n) {
    expression = new LogicalOrExpression(n);
  }

  public void visitMultiplicativeExpression(GNode n) {
    expression = new MultiplicativeExpression(n);
  }
  
  public void visitNewArrayExpression(GNode n) {
    expression = new NewArrayExpression(n);
  }
  
  public void visitNewClassExpression(GNode n) {
    expression = new NewClassExpression(n);
  }
  
  public void visitPostfixExpression(GNode n) {
    expression = new PostfixExpression(n);
  }

  public void visitPrimaryIdentifier(GNode n) {
    expression = new PrimaryIdentifier(n);
  }

  public void visitRelationalExpression(GNode n) {
    expression = new RelationalExpression(n);
  }

  public void visitSelectionExpression(GNode n) {
    expression = new SelectionExpression(n);
  }

  public void visitShiftExpression(GNode n) {
    expression = new ShiftExpression(n);
  }

  public void visitThisExpression(GNode n) {
    expression = new ThisExpression(n);
  }

  public void visitUnaryExpression(GNode n) {
    expression = new UnaryExpression(n);
  }

  // translation
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    Variable v = new Variable(type.getType(), declarators.get(0).getName());
    variables.add(v);
    s.append(type.getCC(indent, className, variables) + " " +  declarators.getCC(indent, className, variables) + "; ");
    s.append(expression.getCC(indent, className, variables) + "; ");
    for (Expression expression : expressions)
      s.append(expression.getCC(indent, className, variables));
    return s.toString();
  }

}
