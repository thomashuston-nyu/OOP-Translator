/**
 * Expression Expression*
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ExpressionList extends TranslationVisitor implements Iterable<Expression> {

  private List<Expression> expressions;

  public ExpressionList(GNode n) {
    expressions = new ArrayList<Expression>();
    visit(n);
  }

  // expressions
  public void visitAdditiveExpression(GNode n) {
    expressions.add(new AdditiveExpression(n));
  }

  public void visitBasicCastExpression(GNode n) {
    expressions.add(new BasicCastExpression(n));
  }
  
  public void visitBitwiseAndExpression(GNode n) {
    expressions.add(new BitwiseAndExpression(n));
  }

  public void visitBitwiseNegationExpression(GNode n) {
    expressions.add(new BitwiseNegationExpression(n));
  }

  public void visitBitwiseOrExpression(GNode n) {
    expressions.add(new BitwiseOrExpression(n));
  }

  public void visitBitwiseXorExpression(GNode n) {
    expressions.add(new BitwiseXorExpression(n));
  }

  public void visitCallExpression(GNode n) {
    expressions.add(new CallExpression(n));
  }

  public void visitCastExpression(GNode n) {
    expressions.add(new CastExpression(n));
  }

  public void visitClassLiteralExpression(GNode n) {
    expressions.add(new ClassLiteralExpression(n));
  }

  public void visitConditionalExpression(GNode n) {
    expressions.add(new ConditionalExpression(n));
  }
  
  public void visitEqualityExpression(GNode n) {
    expressions.add(new EqualityExpression(n));
  }

  public void visitExpression(GNode n) {
    expressions.add(new Expression(n));
  }

  public void visitInstanceOfExpression(GNode n) {
    expressions.add(new InstanceOfExpression(n));
  }

  public void visitLogicalAndExpression(GNode n) {
    expressions.add(new LogicalAndExpression(n));
  }

  public void visitLogicalNegationExpression(GNode n) {
    expressions.add(new LogicalNegationExpression(n));
  }

  public void visitLogicalOrExpression(GNode n) {
    expressions.add(new LogicalOrExpression(n));
  }

  public void visitMultiplicativeExpression(GNode n) {
    expressions.add(new MultiplicativeExpression(n));
  }
  
  public void visitNewArrayExpression(GNode n) {
    expressions.add(new NewArrayExpression(n));
  }
  
  public void visitNewClassExpression(GNode n) {
    expressions.add(new NewClassExpression(n));
  }
  
  public void visitPostfixExpression(GNode n) {
    expressions.add(new PostfixExpression(n));
  }

  public void visitPrimaryIdentifier(GNode n) {
    expressions.add(new PrimaryIdentifier(n));
  }

  public void visitRelationalExpression(GNode n) {
    expressions.add(new RelationalExpression(n));
  }

  public void visitSelectionExpression(GNode n) {
    expressions.add(new SelectionExpression(n));
  }

  public void visitShiftExpression(GNode n) {
    expressions.add(new ShiftExpression(n));
  }

  public void visitThisExpression(GNode n) {
    expressions.add(new ThisExpression(n));
  }

  public void visitUnaryExpression(GNode n) {
    expressions.add(new UnaryExpression(n));
  }

  // helpers
  public Expression get(int index) {
    return expressions.get(index);
  }

  // iterable
  public Iterator<Expression> iterator() {
    Iterator<Expression> it = expressions.iterator();
    return it;
  }

}
