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

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

/**
 * A wrapper around the various types of Java expressions;
 * used to avoid repetitive visit methods.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 1.1
 */
public class JavaExpression extends Visitor implements Translatable {

  private JavaExpression e;
  private JavaStatement s;


  // =========================== Constructors =======================

  /**
   * Empty constructor for subclass use only.
   */
  protected JavaExpression() {}
     
  /**
   * Dispatches on the specified expression node.
   *
   * @param n The expression node.
   */
  public JavaExpression(GNode n, JavaStatement s) {
    this.s = s;
    dispatch(n);
  }


  // =========================== Visit Methods ======================

  /**
   * Creates a new additive expression.
   *
   * @param n The additive expression node.
   */
  public void visitAdditiveExpression(GNode n) {
    e = new ArithmeticExpression(n, this.s);
  }

  /**
   * Creates a new array initializer.
   *
   * @param n The array initializer node.
   */
  public void visitArrayInitializer(GNode n) {
    e = new ArrayInitializer(n, this.s);
  }

  /**
   * Creates a new basic cast expression.
   *
   * @param n The basic cast expression node.
   */
  public void visitBasicCastExpression(GNode n) {
    e = new BasicCastExpression(n,this.s);
  }
  
  /**
   * Creates a new bitwise and expression.
   *
   * @param n The bitwise and expression node.
   */
  public void visitBitwiseAndExpression(GNode n) {
    e = new Expression(n,"&", this.s);
  }

  /**
   * Creates a new bitwise negation expression.
   *
   * @param n The bitwise negation expression node.
   */
  public void visitBitwiseNegationExpression(GNode n) {
    e = new Expression(n,"~",this.s);
  }

  /**
   * Creates a new bitwise or expression.
   *
   * @param n The bitwise or expression node.
   */
  public void visitBitwiseOrExpression(GNode n) {
    e = new Expression(n,"|",this.s);
  }

  /**
   * Creates a new bitwise xor expression.
   *
   * @param n The bitwise xor expression node.
   */
  public void visitBitwiseXorExpression(GNode n) {
    e = new Expression(n,"^",this.s);
  }

  /**
   * Creates a new boolean literal
   * 
   * @param n The boolean literal node.
   */
  public void visitBooleanLiteral(GNode n) {
    e = new Literal(n, this.s);
  }
  
  /**
   * Creates a new call expression.
   *
   * @param n The call expression node.
   */
  public void visitCallExpression(GNode n) {
    e = new CallExpression(n, this.s);
  }

  /**
   * Creates a new cast expression.
   *
   * @param n The cast expression node.
   */
  public void visitCastExpression(GNode n) {
    e = new CastExpression(n, this.s);
  }

  /**
   * Creates a new character literal
   *
   * @param n The character literal node.
   */
  public void visitCharacterLiteral(GNode n) {
    e = new Literal(n, this.s);
  }

  /**
   * Creates a new class literal expression.
   *
   * @param n The class literal expression node.
   */
  public void visitClassLiteralExpression(GNode n) {
    e = new ClassLiteralExpression(n, this.s);
  }

  /**
   * Creates a new conditional expression.
   *
   * @param n The conditional expression node.
   */
  public void visitConditionalExpression(GNode n) {
    e = new ConditionalExpression(n, this.s);
  }
  
  /**
   * Creates a new equality expression.
   *
   * @param n The equality expression node.
   */
  public void visitEqualityExpression(GNode n) {
    e = new Expression(n, this.s);
  }

  /**
   * Creates a new expression.
   *
   * @param n The expression node.
   */
  public void visitExpression(GNode n) {
    e = new Expression(n, this.s);
  }

  /**
   * Creates a new floating point literal
   *
   * @param n The floating point literal node.
   */
  public void visitFloatingPointLiteral(GNode n) {
    e = new Literal(n, this.s);
  }

  /**
   * Creates a new instanceof expression.
   *
   * @param n The instance of expression node.
   */
  public void visitInstanceOfExpression(GNode n) {
    e = new InstanceOfExpression(n, this.s);
  }

  /**
   * Creates a new integer literal
   *
   * @param n The integer literal node.
   */
  public void visitIntegerLiteral(GNode n) {
    e = new Literal(n, this.s);
  }

  /**
   * Creates a new logical and expression.
   *
   * @param n The logical and expression node.
   */
  public void visitLogicalAndExpression(GNode n) {
    e = new Expression(n,"&&",this.s);
  }

  /**
   * Creates a new logical negation expression.
   *
   * @param n The logical negation expression node.
   */
  public void visitLogicalNegationExpression(GNode n) {
    e = new Expression(n,"!",this.s);
  }

  /**
   * Creates a new logical or expression.
   *
   * @param n The logical or expression node.
   */
  public void visitLogicalOrExpression(GNode n) {
    e = new Expression(n,"||",this.s);
  }

  /**
   * Creates a new multiplicative expression.
   *
   * @param n The multiplicative expression node.
   */
  public void visitMultiplicativeExpression(GNode n) {
    e = new ArithmeticExpression(n, this.s);
  }
  
  /**
   * Creates a new new array expression.
   *
   * @param n The new array expression node.
   */
  public void visitNewArrayExpression(GNode n) {
    e = new NewArrayExpression(n, this.s);
  }
  
  /**
   * Creates a new new class expression.
   *
   * @param n The new class expression node.
   */
  public void visitNewClassExpression(GNode n) {
    e = new NewClassExpression(n, this.s);
  }

  public void visitNullLiteral(GNode n) {
    e = new Literal(n, this.s);
  }
  
  /**
   * Creates a new postfix expression.
   *
   * @param n The postfix expression node.
   */
  public void visitPostfixExpression(GNode n) {
    e = new UnaryExpression(n, this.s);
  }

  /**
   * Creates a new primary identifier.
   *
   * @param n The primary identifier node.
   */
  public void visitPrimaryIdentifier(GNode n) {
    e = new PrimaryIdentifier(n, this.s);
  }

  /**
   * Creates a new relational expression.
   *
   * @param n The relational expression node.
   */
  public void visitRelationalExpression(GNode n) {
    e = new Expression(n, this.s);
  }

  /**
   * Creates a new selection expression.
   *
   * @param n The selection expression node.
   */
  public void visitSelectionExpression(GNode n) {
    e = new SelectionExpression(n, this.s);
  }

  /**
   * Creates a new shift expression.
   *
   * @param n The shift expression node.
   */
  public void visitShiftExpression(GNode n) {
    e = new Expression(n, this.s);
  }

  /**
   * Creates a new string literal.
   *
   * @param n The string literal node.
   */
  public void visitStringLiteral(GNode n) {
    e = new Literal(n, this.s);
  }

  /**
   * Creates a new subscript expression.
   *
   * @param n The subscript expression node.
   */
  public void visitSubscriptExpression(GNode n) {
    e = new SubscriptExpression(n, this.s);
  }

  /**
   * Creates a new this expression.
   *
   * @param n The this expression node.
   */
  public void visitThisExpression(GNode n) {
    e = new ThisExpression();
  }

  /**
   * Creates a new unary expression.
   *
   * @param n The unary expression node.
   */
  public void visitUnaryExpression(GNode n) {
    e = new UnaryExpression(n, this.s);
  }

  /**
   * Creats a new variable initializer.
   *
   * @param n The variable initializer.
   */
  public void visitVariableInitializer(GNode n) {
    e = new VariableInitializer(n, this.s);
  }


  // ========================== Nested Classes ======================

  /**
   * An additive expression 
   * (for example, <code>2 + 5</code>)
   * or multiplicative expression
   * (for example, <code>2 * 5</code>).
   */
  private class ArithmeticExpression extends JavaExpression {

    private JavaExpression left, right;
    private String operator;

    /**
     * Creates a new additive or multiplicative expression.
     *
     * @param n The additive or multiplicative expression node.
     */
    public ArithmeticExpression(GNode n, JavaStatement parent) {
      left = new JavaExpression(n.getGeneric(0), parent);
      right = new JavaExpression(n.getGeneric(2), parent);
      operator = n.getString(1);
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      left.translate(out);
      out.p(" ").p(operator).p(" ");
      right.translate(out);
      return out;
    }

  }

  /**
   * An array initializer
   * (for example, <code>{5, 3, 4, 6, 7}</code>).
   */
  private class ArrayInitializer extends JavaExpression {

    private List<JavaExpression> variables;

    /**
     * Creates a new array initializer.
     *
     * @param n The array initializer node.
     */
    public ArrayInitializer(GNode n, JavaStatement parent) {
      variables = new ArrayList<JavaExpression>();
      for (Object o : n) {
        if (null == n)
          continue;
        variables.add(new JavaExpression((GNode)o, parent));
      }
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      // TODO: translate array initializers to C++
      return out;
    }

  }

  /**
   * A basic cast expression
   * (for example, <code></code>).
   */
  private class BasicCastExpression extends JavaExpression {

    private int dimensions;
    private JavaExpression e;
    private JavaType type;

    /**
     * Creates a new basic cast expression.
     *
     * @param n The basic cast expression node.
     */
    public BasicCastExpression(GNode n, JavaStatement parent) {
      type = new JavaType(n.getGeneric(0));
      if (n.getNode(1).hasName("Dimensions")) {
        dimensions = n.getNode(1).size();
        e = new JavaExpression(n.getGeneric(2), parent);
      } else {
        dimensions = 0;                       
        e = new JavaExpression(n.getGeneric(1), parent);
      }
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      // TODO: what is this?
      return out;
    }

  }

  /**
   * A call expression
   * (for example, <code>System.out.println("test")</code>).
   */
  private class CallExpression extends JavaExpression {

    private List<JavaExpression> args;
    private JavaExpression ref;
    private String name;
     
    /**
     * Creates a new call expression.
     *
     * @param n The call expression node.
     */
    public CallExpression(GNode n, JavaStatement parent) {
      if (null != n.get(0))
        ref = new JavaExpression(n.getGeneric(0), parent);
      name = n.getString(2);
      args = new ArrayList<JavaExpression>();
      for (Object o : n.getNode(3)) {
        if (null == o)
          continue;
        args.add(new JavaExpression((GNode)o, parent));
      }
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      if (null != ref) {
        if ((name.equals("print") || name.equals("println"))) {
          // TODO: C++ printing
        } else {
          // TODO: referencing static methods from other classes
        }
      } else {
        // TODO: calling methods from this class, static or instance
      }
      return out;
    }

  }

  /**
   * A cast expression
   * (for example, <code>(Node)o</code>).
   */
  private class CastExpression extends JavaExpression {

    private JavaType type;
     
    /**
     * Creates a new cast expression.
     *
     * @param n The cast expression node.
     */
    public CastExpression(GNode n, JavaStatement parent) {
      type = new JavaType(n.getGeneric(0));
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      return out.p("(").p(type.getType()).p(")");
    }

  }

  /**
   * A class literal expression
   * (for example, <code></code>).
   */
  private class ClassLiteralExpression extends JavaExpression {

    private JavaType type;

    /**
     * Creates a new class literal expression.
     *
     * @param n The class literal expression node.
     */
    public ClassLiteralExpression(GNode n, JavaStatement parent) {
      type = new JavaType(n.getGeneric(0));
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      // TODO: figure out what a class literal expression is
      return out;
    }

  }

  /**
   * A conditional expression
   * (for example, <code>x &lt; 5 ? 1 : 2</code>).
   */
  private class ConditionalExpression extends JavaExpression {

    private JavaExpression test, ifTrue, ifFalse;

    /**
     * Creates a new conditional expression.
     *
     * @param n The conditional expression node.
     */
    public ConditionalExpression(GNode n, JavaStatement parent) {
      test = new JavaExpression(n.getGeneric(0), parent);
      ifTrue = new JavaExpression(n.getGeneric(1), parent);
      ifFalse = new JavaExpression(n.getGeneric(2), parent);
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      test.translate(out).p(" ? ");
      ifTrue.translate(out).p(" : ");
      return ifFalse.translate(out);
    }

  }

  /**
   * An expression 
   * (for example, <code>x = y</code>),
   * an equality expression
   * (for example, <code>x == y</code>),
   * a relational expression,
   * (for example, <code>x &lt;= y</code>),
   * a shift expression
   * (for example, <code>x &lt;&lt; 5</code>),
   * a bitwise expression
   * (for example, <code>5 &amp; 8</code>,
   * <code>~5</code>, <code>5 | 8</code>,
   * <code>5 ^ 8</code>)
   * or a logical expression
   * (for example, <code>x &amp;&amp; y</code>,
   * <code>!x</code>, <code>x || y</code>).
   */
  private class Expression extends JavaExpression {

    private JavaExpression left, right;
    private String operator;

    /**
     * Creates a new expression or equality expression.
     *
     * @param n The expression node.
     */
    public Expression(GNode n, JavaStatement parent) {
      left = new JavaExpression(n.getGeneric(0), parent);
      operator = n.getString(1);
      right = new JavaExpression(n.getGeneric(2), parent);
    }

    /**
     * Creates a new bitwise or logical expression.
     *
     * @param n The expression node.
     * @param operator The operator.
     */
    public Expression(GNode n, String operator, JavaStatement parent) {
      left = new JavaExpression(n.getGeneric(0), parent);
      this.operator = operator;
      if (!operator.equals("~") && !operator.equals("!"))
        right = new JavaExpression(n.getGeneric(1), parent);
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      if (right != null) {
        left.translate(out).p(" ").p(operator).p(" ");
        return right.translate(out);
      } else {
        out.p(operator);
        return left.translate(out);
      }
    }

  }

  /**
   * An instance of expression
   * (for example, <code>x instanceof String</code>).
   */
  private class InstanceOfExpression extends JavaExpression {

    private JavaExpression object;
    private JavaType type;
     
    /**
     * Creates a new instance of expression.
     *
     * @param n The instance of expression node.
     */
    public InstanceOfExpression(GNode n, JavaStatement parent) {
      object = new JavaExpression(n.getGeneric(0), parent);
      type = new JavaType(n.getGeneric(1));
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      // TODO: translate instanceof into C++
      return out;
    }

  }

  /**
   * A boolean, character, floating point,
   * integer, or string literal
   * (for example, <code>true</code> or
   * <code>'a'</code> or <code>5.0</code>
   * or <code>5</code> or <code>"test"</code>).
   */
  private class Literal extends JavaExpression {

    private String value;
    private boolean isString;
    private boolean isNull;

    /**
     * Creates a new literal.
     *
     * @param n The literal node.
     */
    public Literal(GNode n, JavaStatement parent) {
      if (n.size() == 0)
        isNull = true;
      else
        value = n.getString(0);
      if (n.hasName("StringLiteral"))
        isString = true;
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      if (isNull)
        return out.p("__rt::null()");
      else if (isString)
        return out.p("__rt::literal(").p(value).p(")");
      else
        return out.p(value);
    }

  }

  /**
   * A new array expression
   * (for example, <code>new String[5]</code>).
   */
  private class NewArrayExpression extends JavaExpression {

    private List<JavaExpression> dimensions;
    private JavaType type;

    /**
     * Creates a new new array expression.
     *
     * @param n The new array expression node.
     */
    public NewArrayExpression(GNode n, JavaStatement parent) {
      type = new JavaType(n.getGeneric(0));
      dimensions = new ArrayList<JavaExpression>();
      for (Object o : n.getNode(1)) {
        if (null == o)
          continue;
        dimensions.add(new JavaExpression((GNode)o, parent));
      }
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      if (dimensions.size() == 1) {
        out.p("new __rt::Array<");
        type.translate(out).p(">(");
        return dimensions.get(0).translate(out).p(")");
      } else {
        // TODO: handle multi-dimensional arrays
        return out;
      }
    }

  }

  /**
   * A new class expression
   * (for example, <code>new Object()</code>).
   */
  private class NewClassExpression extends JavaExpression {

    private List<JavaExpression> arguments;
    private JavaType type;

    /**
     * Creates a new new class expression.
     *
     * @param n The new class expression node.
     */
    public NewClassExpression(GNode n, JavaStatement parent) {
      type = new JavaType(n.getGeneric(2));
      arguments = new ArrayList<JavaExpression>();
      for (Object o : n.getNode(3)) {
        if (null == o)
          continue;
        arguments.add(new JavaExpression((GNode)o, parent));
      }
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.p("new __");
      type.translate(out);
      out.p("(");
      int size = arguments.size();
      for (int i = 0; i < size; i++) {
        arguments.get(i).translate(out);
        if (i < size - 1)
          out.p(", ");
      }
      return out.p(")");
    }

  }

  /**
   * A variable
   * (for example, <code>x</code>).
   */
  private class PrimaryIdentifier extends JavaExpression {

    String name;

    public PrimaryIdentifier(GNode n, JavaStatement parent) {
      name = n.getString(0);
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      return out.p(name);
    }

  }

  /**
   * A selection expression
   * (for example, <code>System.out</code>).
   */
  private class SelectionExpression extends JavaExpression {

    private JavaExpression identifier;
    private String selection;

    /**
     * Creates a new selection expression.
     *
     * @param n The selection expression node.
     */
    public SelectionExpression(GNode n, JavaStatement parent) {
      identifier = new JavaExpression(n.getGeneric(0), parent);
      selection = n.getString(1);
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      if (identifier instanceof ThisExpression)
        return identifier.translate(out).p("->").p(selection);
      else
        return identifier.translate(out).p("::").p(selection);
    }

  }

  /**
   * A subscript expression
   * (for example, a[1])
   */
  private class SubscriptExpression extends JavaExpression {

    private JavaExpression variable;
    private List<Integer> indices;

    public SubscriptExpression(GNode n, JavaStatement parent) {
      variable = new JavaExpression(n.getGeneric(0), parent);
      indices = new ArrayList<Integer>();
      for (int i = 1; i < n.size(); i++) {
        indices.add(Integer.parseInt(n.getNode(i).getString(0)));
      }
      if (n.getNode(0).hasName("PrimaryIdentifier"))
        Global.objects.get(parent).add(n.getNode(0).getString(0));
    }

    public Printer translate(Printer out) {
      for (Integer i : indices)
        out.p("(*");
      variable.translate(out);
      for (Integer i : indices)
        out.p(")[").p(i).p("]");
      return out;
    }

  }

  /**
   * A this expression
   * (for example, <code>this</code>).
   */
  private class ThisExpression extends JavaExpression {

    /**
    * Creates a new this expression.
    */
    public ThisExpression() {}

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      return out.p("__this");
    }

  }

  /**
   * A postfix expression
   * (for example, <code>x++</code>),
   * or a unary expression
   * (for example, <code>++x</code>).
   */
  private class UnaryExpression extends JavaExpression {

    private JavaExpression identifier;
    private String pre, post;

    /**
     * Creates a new postfix or unary expression.
     *
     * @param n The postfix or unary expression node.
     */
    public UnaryExpression(GNode n, JavaStatement parent) {
      if (n.get(0) instanceof String) {
        pre = n.getString(0);
        identifier = new JavaExpression(n.getGeneric(1), parent);
      } else {
        identifier = new JavaExpression(n.getGeneric(0), parent);
        post = n.getString(1);
      }
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      if (pre != null) {
        out.p(pre);
        return identifier.translate(out);
      } else {
        return identifier.translate(out).p(post);
      }
    }

  }

  /**
   * A variable initializer
   * (for example, <code></code>).
   */
  private class VariableInitializer extends JavaExpression {

    /**
    * Creats a new variable initializer.
    *
    * @param n The variable initializer.
    */
    public VariableInitializer(GNode n, JavaStatement parent) {
      
    }

    /**
     * Translates the expression and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      return out;
    }

  }


  // ======================== Translation Methods ===================

  /**
    * Translates the expression and adds it 
    * to the output stream.
    *
    * @param out The output stream.
    *
    * @return The output stream.
    */
  public Printer translate(Printer out) {
    return e.translate(out);
  }

}
