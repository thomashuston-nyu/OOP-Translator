/**
 * FieldDeclaration/ClassDeclaration/InterfaceDeclaration/Statement
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Block extends Statement {

  private FieldDeclaration field;
  private InterfaceDeclaration inter;
  private Statement statement;
  
  public Block(GNode n) {
    field = null;
    inter = null;
    statement = null;
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
  
  public void visitFieldDeclaration(GNode n) {
    field = new FieldDeclaration(n);
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