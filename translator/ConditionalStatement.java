/**
 * Expression Statement Statement/null
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ConditionalStatement extends Statement {
  
  private Expression expression;
  private Statement ifStatement;
  private Statement elseStatement;

  public ConditionalStatement(GNode n) {
    ifStatement = null;
    elseStatement = null;
    visit(n);
  }

  public void visitExpression(GNode n) {
    expression = new Expression(n);
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
}
