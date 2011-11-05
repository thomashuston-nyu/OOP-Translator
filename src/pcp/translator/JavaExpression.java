/*
 * pcp - The Producer of C++ Programs
 * Copyright (C) 2011 Nabil Hassein, Thomas Huston, Mike Morreale, Marta Wilgan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pcp.translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

/**
 * A Java class.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class JavaExpression extends Visitor implements Translatable {

  private JavaExpression e;

  public JavaExpression() {}

  public JavaExpression(GNode n) {
    dispatch(n);
  }

  /*
  public void visitAdditiveExpression(GNode n) {
    e = new AdditiveExpression(n);
  }

  public void visitBasicCastExpression(GNode n) {
    e = new BasicCastExpression(n);
  }
  
  public void visitBitwiseAndExpression(GNode n) {
    e = new BitwiseAndExpression(n);
  }

  public void visitBitwiseNegationExpression(GNode n) {
    e = new BitwiseNegationExpression(n);
  }

  public void visitBitwiseOrExpression(GNode n) {
    e = new BitwiseOrExpression(n);
  }

  public void visitBitwiseXorExpression(GNode n) {
    e = new BitwiseXorExpression(n);
  }

  public void visitCallExpression(GNode n) {
    e = new CallExpression(n);
  }

  public void visitCastExpression(GNode n) {
    e = new CastExpression(n);
  }

  public void visitClassLiteralExpression(GNode n) {
    e = new ClassLiteralExpression(n);
  }

  public void visitConditionalExpression(GNode n) {
    e = new ConditionalExpression(n);
  }
  
  public void visitEqualityExpression(GNode n) {
    e = new EqualityExpression(n);
  }

  public void visitExpression(GNode n) {
    e = new Expression(n);
  }

  public void visitInstanceOfExpression(GNode n) {
    e = new InstanceOfExpression(n);
  }

  public void visitLogicalAndExpression(GNode n) {
    e = new LogicalAndExpression(n);
  }

  public void visitLogicalNegationExpression(GNode n) {
    e = new LogicalNegationExpression(n);
  }

  public void visitLogicalOrExpression(GNode n) {
    e = new LogicalOrExpression(n);
  }

  public void visitMultiplicativeExpression(GNode n) {
    e = new MultiplicativeExpression(n);
  }
  
  public void visitNewArrayExpression(GNode n) {
    e = new NewArrayExpression(n);
  }
  
  public void visitNewClassExpression(GNode n) {
    e = new NewClassExpression(n);
  }
  
  public void visitPostfixExpression(GNode n) {
    e = new PostfixExpression(n);
  }

  public void visitPrimaryIdentifier(GNode n) {
    e = new PrimaryIdentifier(n);
  }

  public void visitRelationalExpression(GNode n) {
    e = new RelationalExpression(n);
  }

  public void visitSelectionExpression(GNode n) {
    e = new SelectionExpression(n);
  }

  public void visitShiftExpression(GNode n) {
    e = new ShiftExpression(n);
  }

  public void visitThisExpression(GNode n) {
    e = new ThisExpression(n);
  }

  public void visitUnaryExpression(GNode n) {
    e = new UnaryExpression(n);
  }
  */

  private class Literal extends JavaExpression {

    String value;

    public Literal(GNode n) {
      value = n.getString(0);
    }

    public Printer translate(Printer out) {
      return out.p(value);
    }

  }

  public Printer translate(Printer out) {
    return e.translate(out);
  }

}
