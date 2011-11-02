/**
 * Expression (FieldDeclaration/ClassDeclaration/InterfaceDeclaration/Statement)*
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CaseClause extends TranslationVisitor {

  private Expression expression;
  private List<JavaField> fields;
  private List<JavaInterface> interfaceDec;
  private List<Statement> statement;
  
  public CaseClause(GNode n) {
    fields = new ArrayList<JavaField>();
    interfaceDec = new ArrayList<JavaInterface>();
    statement = new ArrayList<Statement>();
    visit(n);
  }
  
  public void visitAssertStatement(GNode n) {
    statement.add(new AssertStatement(n));
  }

  public void visitBreakStatement(GNode n) {
    statement.add(new BreakStatement(n));
  }
  
  public void visitConditionalStatement(GNode n) {
    statement.add(new ConditionalStatement(n));
  }
  
  public void visitContinueStatement(GNode n) {
    statement.add(new ContinueStatement(n));
  }
  
  public void visitDoWhileStatement(GNode n) {
    statement.add(new DoWhileStatement(n));
  }
  
  public void visitExpression(GNode n) {
    expression = new Expression(n);
  }
  
  public void visitExpressionStatement(GNode n) {
    statement.add(new ExpressionStatement(n));
  }
  
  public void visitFieldDeclaration(GNode n) {
    fields.add(new JavaField(n));
  }
  
  public void visitForStatement(GNode n) {
    statement.add(new ForStatement(n));
  }
  
  public void visitInterfaceDeclaration(GNode n) {
    interfaceDec.add(new JavaInterface(n));
  }
  
  public void visitReturnStatement(GNode n) {
    statement.add(new ReturnStatement(n));
  }
  
  public void visitSwitchStatement(GNode n) {
    statement.add(new SwitchStatement(n));
  }

  public void visitSynchronizedStatement(GNode n) {
    statement.add(new SynchronizedStatement(n));
  }
  
  public void visitThrowStatement(GNode n) {
    statement.add(new ThrowStatement(n));
  }
  
  public void visitTryCatchFinallyStatement(GNode n) {
    statement.add(new TryCatchFinallyStatement(n));
  }
  
  public void visitWhileStatement(GNode n) {
    statement.add(new WhileStatement(n));
  }

}
