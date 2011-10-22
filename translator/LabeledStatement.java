/**
 * Identifier Statement
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class LabeledStatement extends TranslationVisitor {
  private String identifier;
  private Statement statement;

  public LabeledStatement(GNode n) {
    identifier = n.getString(0);
    visit(n);
  }

  public void visitBreakStatement(GNode n) {
    statement = new BreakStatement(n);
  }
  
  public void visitConditionalStatement(GNode n) {
    statement = new ConditionalStatement(n);
  }
  
  public void visitContinueStatement(GNode n) {
    statement = new ContinueStatement(n);
  }
  
  public void visitDoWhileStatement(GNode n) {
    statement = new DoWhileStatement(n);
  }
  
  public void visitExpressionStatement(GNode n) {
    statement = new ExpressionStatement(n);
  }
  
  public void visitForStatement(GNode n) {
    statement = new ForStatement(n);
  }
  
  public void visitReturnStatement(GNode n) {
    statement = new ReturnStatement(n);
  }
  
  public void visitSwitchStatement(GNode n) {
    statement = new SwitchStatement(n);
  }
  
  public void visitThrowStatement(GNode n) {
    statement = new ThrowStatement(n);
  }
  
  public void visitTryCatchFinallyStatement(GNode n) {
    statement = new TryCatchFinallyStatement(n);
  }
  
  public void visitWhileStatement(GNode n) {
    statement = new WhileStatement(n);
  }
}
