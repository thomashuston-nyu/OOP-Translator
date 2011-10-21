/**
 * Parent of:
 
 // Mike
 * ConditionalStatement
 * ForStatement
 * WhileStatement
 
 // Susana
 * DoWhileStatement
 * TryCatchFinallyStatement
 * SwitchStatement
 
 // Marta
 * ReturnStatement
 * ThrowStatement
 * BreakStatement
 
 // Thomas
 * Block
 * ContinueStatement
 * ExpressionStatement
 
 * LabeledStatement             // LOW PRIORITY
 * AssertStatement              // LOW PRIORITY
 * EmptyStatement               // NOTHING TO DO
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class Statement extends TranslationVisitor {
  
  private Block block;
  private BreakStatement breakStatement;
  private ConditionalStatement conditional;
  private ContinueStatement continueStatement;
  private DoWhileStatement doWhile;
  private ExpressionStatement exp;
  private ForStatement forStatement;
  private ReturnStatement returnStatement;
  private SwitchStatement switchStatement;
  private ThrowStatement throwStatement;
  private TryCatchFinallyStatement tryCatch;
  private WhileStatement whileStatement;
  
  public Statement(GNode n) {
    
  }
  
  public void visitBlock(GNode n) {
    block = new Block(n);
  }
  
  public void visitBreakStatement(GNode n) {
    breakStatement = new BreakStatement(n);
  }
  
  public void visitConditionalStatement(GNode n) {
    conditional = new ConditionalStatement(n);
  }
  
  public void visitContinueStatement(GNode n) {
    continueStatement = new ContinueStatement(n);
  }
  
  public void visitDoWhileStatement(GNode n) {
    doWhile = new DoWhileStatement(n);
  }
  
  public void visitExpressionStatement(GNode n) {
    exp = new ExpressionStatement(n);
  }
  
  public void visitForStatement(GNode n) {
    forStatement = new ForStatement(n);
  }
  
  public void visitReturnStatement(GNode n) {
    returnStatement = new ReturnStatement(n);
  }
  
  public void visitSwitchStatement(GNode n) {
    switchStatement = new SwitchStatement(n);
  }
  
  public void visitThrowStatement(GNode n) {
    throwStatement = new ThrowStatement(n);
  }
  
  public void visitTryCatchFinallyStatement(GNode n) {
    tryCatch = new TryCatchFinallyStatement(n);
  }
  
  public void visitWhileStatement(GNode n) {
    whileStatement = new WhileStatement(n);
  }
  
}