/**
 * Expression Statement Statement/null
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ConditionalStatement extends Statement implements Translatable {
  
  private Expression expression;
  private Statement ifStatement;
  private Statement elseStatement;

  public ConditionalStatement(GNode n) {
    visit(n);
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

  public void visitPrimaryExpression(GNode n) {
    expression = new PrimaryExpression(n);
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

  // literals
  public void visitBooleanLiteral(GNode n) {
    expression = new BooleanLiteral(n);
  }

  // statements
  public void visitAssertStatement(GNode n) {
    setStatement(new AssertStatement(n));
  }

  public void visitBlock(GNode n) {
    setStatement(new Block(n));
  }

  public void visitBreakStatement(GNode n) {
    setStatement(new BreakStatement(n));
  }
  
  public void visitConditionalStatement(GNode n) {
    setStatement(new ConditionalStatement(n));
  }
  
  public void visitContinueStatement(GNode n) {
    setStatement(new ContinueStatement(n));
  }
  
  public void visitDoWhileStatement(GNode n) {
    setStatement(new DoWhileStatement(n));
  }

  public void visitExpressionStatement(GNode n) {
    setStatement(new ExpressionStatement(n));
  }
  
  public void visitForStatement(GNode n) {
    setStatement(new ForStatement(n));
  }

  public void visitReturnStatement(GNode n) {
    setStatement(new ReturnStatement(n));
  }
  
  public void visitSwitchStatement(GNode n) {
    setStatement(new SwitchStatement(n));
  }
  
  public void visitSynchronizedStatement(GNode n) {
    setStatement(new SynchronizedStatement(n));
  }
  
  public void visitThrowStatement(GNode n) {
    setStatement(new ThrowStatement(n));
  }
  
  public void visitTryCatchFinallyStatement(GNode n) {
    setStatement(new TryCatchFinallyStatement(n));
  }
  
  public void visitWhileStatement(GNode n) {
    setStatement(new WhileStatement(n));
  }

  // helpers
  private void setStatement(Statement statement) {    
    if (ifStatement == null)
      ifStatement = statement;
    else
      elseStatement = statement;
  }

  // translation
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    s.append(getIndent(indent) + "if (" + expression.getCC(indent, className, variables) + ") {\n");
    s.append(ifStatement.getCC(++indent, className, variables) + getIndent(--indent) + "}");
    if (elseStatement != null)
      s.append(" else {\n" + elseStatement.getCC(++indent, className, variables) + getIndent(--indent) + "}");
    s.append("\n");
    return s.toString();
  }
  
}
