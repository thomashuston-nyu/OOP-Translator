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
import java.util.HashSet;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

/**
 * A wrapper around the various types of Java statements;
 * used to avoid repetitive visit methods.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 1.1
 */
public class JavaStatement extends Visitor implements Translatable {

  private JavaStatement s;


  // =========================== Constructors =======================

  /**
   * Empty constructor for subclass use only.
   */
  protected JavaStatement() {}

  /**
   * Dispatches on the specified statement node.
   */
  public JavaStatement(GNode n) {
    Global.objects.put(this, new HashSet<String>());
    dispatch(n);
  }


  // =========================== Visit Methods ======================
  
  /**
   * Creates a new block.
   *
   * @param n The block node.
   */
  public void visitBlock(GNode n) {
    s = new Block(n,this);
  }

  /**
   * Creates a new block declaration.
   *
   * @param n The block declaration node.
   */
  public void visitBlockDeclaration(GNode n) {
    s = new BlockDeclaration(n,this);
  }

  /**
   * Creates a new break statement.
   *
   * @param n The break statement node.
   */
  public void visitBreakStatement(GNode n) {
    s = new BreakStatement(n,this);
  }
  
  /**
   * Creates a new conditional statement.
   *
   * @param n The conditional statement node.
   */
  public void visitConditionalStatement(GNode n) {
    s = new ConditionalStatement(n,this);
  }
  
  /**
   * Creates a new continue statement.
   *
   * @param n The continue statement node.
   */
  public void visitContinueStatement(GNode n) {
    s = new ContinueStatement(n,this);
  }
  
  /**
   * Creates a new do-while statement.
   *
   * @param n The do-while statement node.
   */
  public void visitDoWhileStatement(GNode n) {
    s = new DoWhileStatement(n,this);
  }

  /**
   * Sets the statement to null.
   *
   * @param n The empty statement node.
   */
  public void visitEmptyStatement(GNode n) {
    s = null;
  }
  
  /**
   * Creates a new expression statement.
   *
   * @param n The expression statement node.
   */
  public void visitExpressionStatement(GNode n) {
    s = new ExpressionStatement(n,this);
  }

  /**
   * Creates a new field.
   *
   * @param n The field declaration node.
   */
  public void visitFieldDeclaration(GNode n) {
    s = new JavaField(n);
  }
  
  /**
   * Creates a new for statement.
   *
   * @param n The for statement node.
   */
  public void visitForStatement(GNode n) {
    s = new ForStatement(n,this);
  }
  
  /**
   * Creates a new return statement.
   *
   * @param n The return statement node.
   */
  public void visitReturnStatement(GNode n) {
    s = new ReturnStatement(n,this);
  }
  
  /**
   * Creates a new switch statement.
   *
   * @param n The switch statement node.
   */
  public void visitSwitchStatement(GNode n) {
    s = new SwitchStatement(n,this);
  }

  /**
   * Creates a new throw statement.
   *
   * @param n The throw statement node.
   */
  public void visitThrowStatement(GNode n) {
    s = new ThrowStatement(n,this);
  }
  
  /**
   * Creates a new try-catch-finally statement.
   *
   * @param n The try-catch-finally statement node.
   */
  public void visitTryCatchFinallyStatement(GNode n) {
    s = new TryCatchFinallyStatement(n,this);
  }

  /**
   * Creates a new while statement.
   *
   * @param n The while statement node.
   */
  public void visitWhileStatement(GNode n) {
    s = new WhileStatement(n,this);
  }


  // ========================== Nested Classes ======================

  /**
   * A block.
   */
  private class Block extends JavaStatement {
    
    private List<JavaStatement> statements;

    /**
     * Creates a new block.
     *
     * @param n The block node.
     */
    public Block(GNode n, JavaStatement parent) {
      statements = new ArrayList<JavaStatement>();
      for (Object o : n) {
        statements.add(new JavaStatement((GNode)o));
      }
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      for (JavaStatement s : statements)
        s.translate(out);
      return out;
    }

  }

  /**
   * A block declaration.
   */
  private class BlockDeclaration extends JavaStatement {

    /**
     * Creates a new block declaration.
     *
     * @param n The block declaration node.
     */
    public BlockDeclaration(GNode n, JavaStatement parent) {
      // TODO: translate block declarations
    }

    /**
     * Translates the statement and adds it 
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

  /**
   * A break statement.
   */
  private class BreakStatement extends JavaStatement {

    /**
     * Creates a new break statement.
     *
     * @param n The break statement node.
     */
    public BreakStatement(GNode n, JavaStatement parent) {}

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      return out.indent().pln("break;");
    }

  }

  /**
   * A conditional statement.
   */
  private class ConditionalStatement extends JavaStatement {

    private JavaExpression e;
    private JavaStatement ifStatement;
    private JavaStatement elseStatement;

    /**
     * Creates a new conditional statement.
     *
     * @param n The conditional statement node.
     */
    public ConditionalStatement(GNode n, JavaStatement parent) {
      e = new JavaExpression(n.getGeneric(0), parent);
      ifStatement = new JavaStatement(n.getGeneric(1));
      if (null != n.get(2))
        elseStatement = new JavaStatement(n.getGeneric(2));
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent().p("if (");
      e.translate(out).pln(") {").incr();
      ifStatement.translate(out);
      if (null != elseStatement)
        out.decr().indent().pln("} else {").incr();
      out.decr().indent().pln("}");
      return out;
    }

  }

  /**
   * A continue statement.
   */
  private class ContinueStatement extends JavaStatement {

    /**
     * Creates a new continue statement.
     *
     * @param n The continue statement node.
     */
    public ContinueStatement(GNode n, JavaStatement parent) {}

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      return out.indent().pln("continue;");
    }

  }

  /**
   * A default statement.
   */
  private class DefaultStatement extends JavaStatement {

    /**
     * Creates a new default statement.
     *
     * @param n The default statement node.
     */
    public DefaultStatement(GNode n, JavaStatement parent) {}

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      return out.indent().pln("default:").incr();
    }

  }

  /**
   * A do while statement.
   */
  private class DoWhileStatement extends JavaStatement {

    private JavaExpression e;
    private JavaStatement s;

    /**
     * Creates a new do-while statement.
     *
     * @param n The do-while statement node.
     */
    public DoWhileStatement(GNode n, JavaStatement parent) {
      s = new JavaStatement(n.getGeneric(0));
      e = new JavaExpression(n.getGeneric(1), parent);
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent().pln("do {").incr();
      s.translate(out);
      out.decr().indent().p("} while (");
      e.translate(out);
      return out.pln(");");
    }

  }

  /**
   * An expression statement.
   */
  private class ExpressionStatement extends JavaStatement {

    private JavaExpression e;

    /**
     * Creates a new expression statement.
     *
     * @param n The expression statement node.
     */
    public ExpressionStatement(GNode n, JavaStatement parent) {
      e = new JavaExpression(n.getGeneric(0), parent);
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent();
      e.translate(out);
      return out.pln(";");
    }

  }

  /**
   * A for statement.
   */
  private class ForStatement extends JavaStatement {

    private JavaExpression condition;
    private List<Integer> dimensions;
    private boolean isFinal;
    private JavaStatement body;
    private JavaType type;
    private List<JavaExpression> updates;
    private List<JavaExpression> values;
    private List<String> variables;

    /**
     * Creates a new for statement.
     *
     * @param n The for statement node.
     */
    public ForStatement(GNode n, JavaStatement parent) {
      // Check if the variables are final
      if (null != n.getNode(0).get(0))
        isFinal = true;

      // Get the type of the variables
      if (null != n.getNode(0).get(1))
        type = new JavaType(n.getNode(0).getGeneric(1));

      // Get the names and values of the variables
      if (null != n.getNode(0).get(2)) {
        variables = new ArrayList<String>();
        values = new ArrayList<JavaExpression>();
        for (Object o : n.getNode(0).getNode(2)) {
          Node declarator = (Node)o;
          variables.add(declarator.getString(0));
          if (null != declarator.get(1))
            type.setDimensions(declarator.getNode(1).size());
          if (null == declarator.get(2))
            values.add(null);
          else
            values.add(new JavaExpression(declarator.getGeneric(2), parent));
        }
      }

      // Get the condition
      if (null != n.getNode(0).get(3))
        condition = new JavaExpression(n.getNode(0).getGeneric(3), parent);

      // Get the updates
      if (null != n.getNode(0).get(4)) {
        updates = new ArrayList<JavaExpression>();
        for (Object o : n.getNode(0).getNode(4)) {
          updates.add(new JavaExpression((GNode)o, parent));
        }
      }

      // Get the body of the foor loop
      body = new JavaStatement(n.getGeneric(1));
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent().p("for (");
      if (isFinal)
        out.p("const ");
      if (null != type)
        type.translate(out).p(" ");
      if (null != variables) {
        int size = variables.size();
        for (int i = 0; i < size; i++) {
          out.p(variables.get(i));
          // TODO: Handle C++ arrays
          // for (int j = 0; j < dimensions.get(i); j++)
          //   out.p("[]");
          if (null != values.get(i)) {
            out.p(" = ");
            values.get(i).translate(out);
          }
          if (i < size - 1)
            out.p(", ");
        }
      }
      out.p("; ");
      if (null != condition)
        condition.translate(out);
      out.p("; ");
      if (null != updates) {
        int size = updates.size();
        for (int i = 0; i < size; i++) {
          updates.get(i).translate(out);
          if (i < size - 1)
            out.p(", ");
        }
      }
      out.pln(") {").incr();
      body.translate(out).decr();
      return out.indent().pln("}");
    }

  }

  /**
   * A return statement.
   */
  private class ReturnStatement extends JavaStatement {

    private JavaExpression e;

    /**
     * Creates a new return statement.
     *
     * @param n The return statement node.
     */
    public ReturnStatement(GNode n, JavaStatement parent) {
      if (null != n.get(0))
        e = new JavaExpression(n.getGeneric(0), parent);
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent().p("return");
      if (null != e) {
        out.p(" ");
        e.translate(out);
      }
      return out.pln(";");
    }

  }

  /**
   * A switch statement.
   */
  private class SwitchStatement extends JavaStatement {

    private List<List<JavaStatement>> actions;
    private List<Boolean> breaks;
    private List<JavaExpression> cases;
    private int defaultAction;
    private JavaExpression variable;

    /**
     * Creates a new switch statement.
     *
     * @param n The switch statement node.
     */
    public SwitchStatement(GNode n, JavaStatement parent) {
      // Get the switch variable
      variable = new JavaExpression(n.getGeneric(0), parent);

      // Get the cases
      actions = new ArrayList<List<JavaStatement>>();
      breaks = new ArrayList<Boolean>();
      cases = new ArrayList<JavaExpression>();
      int size = n.size();
      for (int i = 1; i < size; i++) {
        Node node = n.getNode(i);
        if (node.hasName("DefaultClause") && 0 == node.size()) {
          defaultAction = i - 1;
          continue;
        } else if (node.hasName("DefaultClause")) {
          defaultAction = i - 1;
          cases.add(null);
        } else {
          cases.add(new JavaExpression(node.getGeneric(0), parent));
        }
        List<JavaStatement> caseActions = new ArrayList<JavaStatement>();
        int start = node.hasName("DefaultClause") ? 0 : 1;
        if (start < node.size()) {
          for (int j = start; j < node.size(); j++) {
            if (!node.getNode(j).hasName("BreakStatement"))
              caseActions.add(new JavaStatement(node.getGeneric(j)));
            else
              breaks.add(true);
          }
        }
        actions.add(caseActions);
        if (breaks.size() != actions.size())
          breaks.add(false);
      }
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent().p("switch(");
      variable.translate(out).pln(") {").incr();
      for (int i = 0; i < cases.size(); i++) {
        if (null != cases.get(i)) {
          out.indent().p("case ");
          cases.get(i).translate(out).pln(":");
        }
        if (null == cases.get(i) || defaultAction == i)
          out.indent().pln("default:");
        out.incr();
        for (JavaStatement action : actions.get(i)) {
          action.translate(out);
        }
        if (breaks.get(i))
          out.indent().pln("break;");
        out.decr();
      }
      return out.decr().indent().pln("}");
    }

  }

  /**
   * A throw statement.
   */
  private class ThrowStatement extends JavaStatement {

    private JavaExpression e;

    /**
     * Creates a new throw statement.
     *
     * @param n The throw statement node.
     */
    public ThrowStatement(GNode n, JavaStatement parent) {
      e = new JavaExpression(n.getGeneric(0), parent);
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent().p("throw ");
      e.translate(out);
      return out.pln(";");
    }

  }

  /**
   * A try catch finally statement.
   */
  private class TryCatchFinallyStatement extends JavaStatement {

    /**
     * Creates a new try-catch-finally statement.
     *
     * @param n The try-catch-finally statement node.
     */
    public TryCatchFinallyStatement(GNode n, JavaStatement parent) {
      // TODO: translate try-catch-finally statements
    }

    /**
     * Translates the statement and adds it 
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

  /**
   * A while statement.
   */
  private class WhileStatement extends JavaStatement {

    private JavaExpression e;
    private JavaStatement s;

    /**
     * Creates a new while statement.
     *
     * @param n The while statement node.
     */
    public WhileStatement(GNode n, JavaStatement parent) {
      e = new JavaExpression(n.getGeneric(0), parent);
      s = new JavaStatement(n.getGeneric(1));
    }

    /**
     * Translates the statement and adds it 
     * to the output stream.
     *
     * @param out The output stream.
     *
     * @return The output stream.
     */
    public Printer translate(Printer out) {
      out.indent().p("while (");
      e.translate(out).pln(") {").incr();
      return s.translate(out).decr().indent().pln("}");
    }

  }


  // ======================== Translation Methods ===================

  /**
   * Translates the statement and adds it 
   * to the output stream.
   *
   * @param out The output stream.
   *
   * @return The output stream.
   */
  public Printer translate(Printer out) {
    if (null == s)
      return out;
    for (String obj : Global.objects.get(this))
      out.indent().p("__rt::checkNotNull(").p(obj).pln(");");
    return s.translate(out);
  }

}
