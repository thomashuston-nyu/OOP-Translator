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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @version 1.4
 */
public class JavaStatement extends Visitor implements Translatable {

  private GNode node;
  private Set<String> objects;
  private Map<String, JavaExpression> stores;
  private JavaScope parent;
  private JavaStatement s;


  // =========================== Constructors =======================

  /**
   * Empty constructor for subclass use only.
   */
  public JavaStatement() {
    objects = new HashSet<String>();
    stores = new HashMap<String, JavaExpression>();
  }

  /**
   * Dispatches on the specified statement node
   * and sets the class the statement is in.
   *
   * @param n The statement node.
   * @param parent The scope the statement is in.
   */
  public JavaStatement(GNode n, JavaScope parent) {
    this.node = n;
    this.parent = parent;
    objects = new HashSet<String>();
    stores = new HashMap<String, JavaExpression>();
    dispatch(n);
  }


  // ============================ Get Methods =======================
  
  /**
   * Checks if the statement has the specified name.
   *
   * @param name The name.
   *
   * @return <code>True</code> if it has the specified name;
   * <code>false</code> otherwise.
   */
  public boolean hasName(String name) {
    return node.hasName(name);
  }

  /**
   * Gets the scope the statement is in.
   *
   * @return The variable scope.
   */
  public JavaScope getScope() {
    return parent;
  }


  // ============================ Set Methods =======================
  
  /**
   * Adds an object to the not null on.
   *
   * @param obj The object.
   */
  public void addObject(String obj) {
    if (null != obj)
      objects.add(obj);
  }
  
  /**
   * Adds an array store expression to check.
   *
   * @param array The name of the array.
   * @param exp The expression to store.
   */
  public void addStore(String array, JavaExpression exp) {
    stores.put(array, exp);
  }

  /**
   * Makes sure to compile a full list of variables to check
   * not null on.
   */
  public void checkNotNull() {}


  // =========================== Visit Methods ======================
  
  /**
   * Creates a new block declaration.
   *
   * @param n The block declaration node.
   */
  public void visitBlockDeclaration(GNode n) {
    s = new BlockDeclaration(n, this);
  }

  /**
   * Creates a new break statement.
   *
   * @param n The break statement node.
   */
  public void visitBreakStatement(GNode n) {
    s = new BreakStatement(n, this);
  }
  
  /**
   * Creates a new conditional statement.
   *
   * @param n The conditional statement node.
   */
  public void visitConditionalStatement(GNode n) {
    s = new ConditionalStatement(n, this);
  }
  
  /**
   * Creates a new continue statement.
   *
   * @param n The continue statement node.
   */
  public void visitContinueStatement(GNode n) {
    s = new ContinueStatement(n, this);
  }
  
  /**
   * Creates a new do-while statement.
   *
   * @param n The do-while statement node.
   */
  public void visitDoWhileStatement(GNode n) {
    s = new DoWhileStatement(n, this);
  }

  /**
   * Sets the statement to null.
   *
   * @param n The empty statement node.
   */
  public void visitEmptyStatement(GNode n) {
  }
  
  /**
   * Creates a new expression statement.
   *
   * @param n The expression statement node.
   */
  public void visitExpressionStatement(GNode n) {
    s = new ExpressionStatement(n, this);
    if (n.getNode(0).hasName("Expression") && 
        n.getNode(0).getNode(0).hasName("SubscriptExpression")) {
      if ((!n.getNode(0).getNode(2).getName().endsWith("Literal") ||
          n.getNode(0).getNode(2).hasName("StringLiteral")) &&      
          !n.getNode(0).getNode(2).getName().startsWith("New")) {
        GNode node = n.getNode(0).getGeneric(0);
        if (node.getNode(0).hasName("SubscriptExpression")) {
          List<String> indices = new ArrayList<String>();
          while (node.getNode(0).hasName("SubscriptExpression")) {
            if (node.getNode(0).getNode(1).hasName("IntegerLiteral"))
              indices.add(0, node.getNode(0).getNode(1).getString(0));
            else
              indices.add(0, "$" + node.getNode(0).getNode(1).getString(0));
            node = node.getGeneric(0);
          }
          StringBuilder name = new StringBuilder();
          for (String i : indices) {
            name.append("(*");
          }
          name.append("$" + node.getNode(0).getString(0));
          for (String i : indices) {
            name.append(")[" + i + "]");
          }
          if (parent.isInScope("$" + node.getNode(0).getString(0)) &&
              null != parent.getVariableType("$" + node.getNode(0).getString(0)).getClassType())
            stores.put(name.toString(),
                new JavaExpression(n.getNode(0).getGeneric(2), this));
        } else {
          if (parent.isInScope("$" + node.getNode(0).getString(0)) &&
              null != parent.getVariableType("$" + node.getNode(0).getString(0)).getClassType())
            stores.put("$" + node.getNode(0).getString(0),
                new JavaExpression(n.getNode(0).getGeneric(2), this));
        }
      }
    }
  }

  /**
   * Creates a new field.
   *
   * @param n The field declaration node.
   */
  public void visitFieldDeclaration(GNode n) {
    s = new JavaField(n, this, parent);
  }
  
  /**
   * Creates a new for statement.
   *
   * @param n The for statement node.
   */
  public void visitForStatement(GNode n) {
    s = new ForStatement(n, this);
  }
  
  /**
   * Creates a new return statement.
   *
   * @param n The return statement node.
   */
  public void visitReturnStatement(GNode n) {
    s = new ReturnStatement(n, this);
  }
  
  /**
   * Creates a new switch statement.
   *
   * @param n The switch statement node.
   */
  public void visitSwitchStatement(GNode n) {
    s = new SwitchStatement(n, this);
  }

  /**
   * Creates a new throw statement.
   *
   * @param n The throw statement node.
   */
  public void visitThrowStatement(GNode n) {
    s = new ThrowStatement(n, this);
  }
  
  /**
   * Creates a new try-catch statement.
   *
   * @param n The try-catch statement node.
   */
  public void visitTryCatchFinallyStatement(GNode n) {
    s = new TryCatchStatement(n, this);
  }

  /**
   * Creates a new while statement.
   *
   * @param n The while statement node.
   */
  public void visitWhileStatement(GNode n) {
    s = new WhileStatement(n, this);
  }


  // ========================== Nested Classes ======================

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
    private JavaBlock ifStatement;
    private JavaBlock elseStatement;

    /**
     * Creates a new conditional statement.
     *
     * @param n The conditional statement node.
     */
    public ConditionalStatement(GNode n, JavaStatement parent) {
      e = new JavaExpression(n.getGeneric(0), parent);
      ifStatement = new JavaBlock(n.getGeneric(1), parent.getScope());
      if (null != n.get(2))
        elseStatement = new JavaBlock(n.getGeneric(2), parent.getScope());
    }
    
    /**
     * Makes sure to compile a full list of variables to check
     * not null on.
     */
    public void checkNotNull() {
      e.checkNotNull();
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
      ifStatement.translate(out).decr().indent().p("}");
      if (null != elseStatement) {
        out.p(" else ").pln("{").incr();
        elseStatement.translate(out).decr().indent().pln("}");
      } else {
        out.pln();
      }
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
    private JavaBlock s;

    /**
     * Creates a new do-while statement.
     *
     * @param n The do-while statement node.
     */
    public DoWhileStatement(GNode n, JavaStatement parent) {
      s = new JavaBlock(n.getGeneric(0), parent.getScope());
      e = new JavaExpression(n.getGeneric(1), parent);
    }

    /**
     * Makes sure to compile a full list of variables to check
     * not null on.
     */
    public void checkNotNull() {
      e.checkNotNull();
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

    private JavaExpression e, initializer;
    private boolean isThis, isSuper;

    /**
     * Creates a new expression statement.
     *
     * @param n The expression statement node.
     */
    public ExpressionStatement(GNode n, JavaStatement parent) {
      e = new JavaExpression(n.getGeneric(0), parent);
      if (n.getNode(0).hasName("CallExpression")) {
        if (n.getNode(0).getString(2).equals("this"))
          isThis = true;
        else if (n.getNode(0).getString(2).equals("super"))
          isSuper = true;
      }
    }

    /**
     * Makes sure to compile a full list of variables to check
     * not null on.
     */
    public void checkNotNull() {
      if (!isThis && !isSuper)
        e.checkNotNull();
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
      if (!isThis && !isSuper)
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
    private JavaBlock body;
    private JavaType type;
    private List<JavaExpression> updates;
    private List<JavaExpression> values;
    private List<String> vars;

    /**
     * Creates a new for statement.
     *
     * @param n The for statement node.
     */
    public ForStatement(GNode n, JavaStatement parent) {
      // Check if the variables are final
      if (n.getNode(0).getNode(0).size() > 0)
        isFinal = true;

      // Get the type of the variables
      if (null != n.getNode(0).get(1))
        type = new JavaType(n.getNode(0).getGeneric(1));

      // Get the names and values of the variables
      if (null != n.getNode(0).get(2)) {
        vars = new ArrayList<String>();
        values = new ArrayList<JavaExpression>();
        for (Object o : n.getNode(0).getNode(2)) {
          Node declarator = (Node)o;
          vars.add("$" + declarator.getString(0));
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

      // Get the body of the for loop
      body = new JavaBlock(n.getGeneric(1), parent.getScope());

      // Add any variables to the scope of the for loop
      for (String var : vars) {
        body.addVariable(var, type);
      }
    }

    /**
     * Makes sure to compile a full list of variables to check
     * not null on.
     */
    public void checkNotNull() {
      condition.checkNotNull();
      for (JavaExpression e : updates) {
        e.checkNotNull();
      }
      for (JavaExpression e : values) {
        e.checkNotNull();
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
      out.indent().p("for (");
      if (isFinal)
        out.p("const ");
      if (null != type)
        type.translate(out).p(" ");
      if (null != vars) {
        int size = vars.size();
        for (int i = 0; i < size; i++) {
          out.p(vars.get(i));
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
    private boolean isThis;

    /**
     * Creates a new return statement.
     *
     * @param n The return statement node.
     */
    public ReturnStatement(GNode n, JavaStatement parent) {
      if (null != n.get(0)) {
        if (n.getNode(0).hasName("ThisExpression"))
          isThis = true;
        else
          e = new JavaExpression(n.getGeneric(0), parent);
      }
    }

    /**
     * Makes sure to compile a full list of variables to check
     * not null on.
     */
    public void checkNotNull() {
      if (null != e && e.hasName("CallExpression"))
        e.checkNotNull();
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
      } else if (isThis) {
        if (parent.hasName("JavaConstructor"))
          out.p("con");
        else
          out.p(" __this");
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
              caseActions.add(new JavaStatement(node.getGeneric(j), parent.getScope()));
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

    private String exception;

    /**
     * Creates a new throw statement.
     *
     * @param n The throw statement node.
     */
    public ThrowStatement(GNode n, JavaStatement parent) {
      exception = n.getNode(0).getNode(2).getString(0);
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
      return out.indent().p("throw java::lang::").p(exception).pln("();");
    }

  }

  /**
   * A try-catch statement.
   */
  private class TryCatchStatement extends JavaStatement {

    JavaBlock tryBlock, catchBlock;
    String exception;

    /**
     * Creates a new try-catch statement.
     *
     * @param n The try-catch statement node.
     */
    public TryCatchStatement(GNode n, JavaStatement parent) {
      tryBlock = new JavaBlock(n.getGeneric(1), parent.getScope());
      if (null != n.get(2)) {
        exception = n.getNode(2).getNode(0).getNode(1).getNode(0).getString(0);
        catchBlock = new JavaBlock(n.getNode(2).getGeneric(1), parent.getScope());
      } else {
        exception = "Exception";
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
      out.indent().pln("try {").incr();
      tryBlock.translate(out);
      out.decr().indent().p("} catch (java::lang::").p(exception).p(" e) {");
      if (null != catchBlock) {
        out.pln().incr();
        catchBlock.translate(out);
        out.decr().indent().pln("}");
      } else {
        out.pln("}");
      }
      return out;
    }

  }

  /**
   * A while statement.
   */
  private class WhileStatement extends JavaStatement {

    private JavaExpression e;
    private JavaBlock s;

    /**
     * Creates a new while statement.
     *
     * @param n The while statement node.
     */
    public WhileStatement(GNode n, JavaStatement parent) {
      e = new JavaExpression(n.getGeneric(0), parent);
      s = new JavaBlock(n.getGeneric(1), parent.getScope());
    }

    /**
     * Makes sure to compile a full list of variables to check
     * not null on.
     */
    public void checkNotNull() {
      e.checkNotNull();
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
    s.checkNotNull();
    if (!parent.hasName("JavaClass") && !parent.hasName("JavaConstructor")) {
      for (String obj : objects) {
        out.indent().p("__rt::checkNotNull(").p(obj).pln(");");
      }
      Set<String> keys = stores.keySet();
      for (String key : keys) {
        out.indent().p("__rt::checkStore(").p(key).p(", ");
        stores.get(key).translate(out).pln(");");
      }
    }
    return s.translate(out);
  }

}
