/**
 * BasicForControl Statement
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ForStatement extends Statement implements Translatable {
  
  private BasicForControl basicForControl;
  private Statement statement;

  public ForStatement(GNode n) {
    visit(n);
  }

  public void visitBasicForControl(GNode n) {
    basicForControl = new BasicForControl(n);
  }

  // statements
  public void visitAssertStatement(GNode n) {
    statement = new AssertStatement(n);
  }

  public void visitBlock(GNode n) {
    statement = new Block(n);
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
  
  public void visitSynchronizedStatement(GNode n) {
    statement = new SynchronizedStatement(n);    
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

  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    s.append(getIndent(indent) + "for (" + basicForControl.getCC(indent, className, variables) + ") {\n");
    s.append(statement.getCC(++indent, className, variables) + getIndent(--indent) + "}\n");
    return s.toString();
  }
  
}
