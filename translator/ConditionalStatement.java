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
    ifStatement = null;
    elseStatement = null;
    visit(n);
  }
  
  public void visitAssertStatement(GNode n) {
    setStatement(new AssertStatement(n));
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

  public void visitEqualityExpression(GNode n) {
    expression = new EqualityExpression(n);
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public void visitExpressionStatement(GNode n) {
    setStatement(new ExpressionStatement(n));
  }
  
  public void visitForStatement(GNode n) {
    setStatement(new ForStatement(n));
  }

  public void visitPrimaryIdentifier(GNode n) {
    expression = new PrimaryIdentifier(n);
  }

  public void visitRelationalExpression(GNode n) {
    expression = new RelationalExpression(n);
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

  private void setStatement(Statement s) {    
    if (ifStatement == null) {
      ifStatement = s;
    } else {
      elseStatement = s;
    }
  }
  
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
