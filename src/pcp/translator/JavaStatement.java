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
 * A Java class.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class JavaStatement extends Visitor implements Translatable {

  private JavaStatement s;

  public JavaStatement() {}

  public JavaStatement(GNode n) {
    dispatch(n);
  }
  
  public void visitBlock(GNode n) {
    s = new Block(n);
  }

  public void visitBlockDeclaration(GNode n) {
    s = new BlockDeclaration(n);
  }

  public void visitBreakStatement(GNode n) {
    s = new BreakStatement(n);
  }
  
  public void visitConditionalStatement(GNode n) {
    s = new ConditionalStatement(n);
  }
  
  public void visitContinueStatement(GNode n) {
    s = new ContinueStatement(n);
  }
  
  public void visitDoWhileStatement(GNode n) {
    s = new DoWhileStatement(n);
  }

  public void visitEmptyStatement(GNode n) {
    s = null;
  }
  
  public void visitExpressionStatement(GNode n) {
    s = new ExpressionStatement(n);
  }

  public void visitFieldDeclaration(GNode n) {
    s = new FieldDeclaration(n);
  }
  
  public void visitForStatement(GNode n) {
    s = new ForStatement(n);
  }
  
  public void visitReturnStatement(GNode n) {
    s = new ReturnStatement(n);
  }
  
  public void visitSwitchStatement(GNode n) {
    s = new SwitchStatement(n);
  }

  public void visitThrowStatement(GNode n) {
    s = new ThrowStatement(n);
  }
  
  public void visitTryCatchFinallyStatement(GNode n) {
    s = new TryCatchFinallyStatement(n);
  }

  public void visitWhileStatement(GNode n) {
    s = new WhileStatement(n);
  }

  private class Block extends JavaStatement {
    
    private List<JavaStatement> statements;

    public Block(GNode n) {
      statements = new ArrayList<JavaStatement>();
      for (Object o : n) {
        if (o == null)
          continue;
        statements.add(new JavaStatement((GNode)o));
      }
    }

    public Printer translate(Printer out) {
      for (JavaStatement s : statements)
        s.translate(out);
      return out;
    }

  }

  private class BlockDeclaration extends JavaStatement {

    public BlockDeclaration(GNode n) {}

    public Printer translate(Printer out) {
      return out;
    }

  }

  private class BreakStatement extends JavaStatement {

    public BreakStatement(GNode n) {}

    public Printer translate(Printer out) {
      return out.indent().pln("break;");
    }

  }

  private class ConditionalStatement extends JavaStatement {

    private JavaExpression e;
    private JavaStatement ifStatement;
    private JavaStatement elseStatement;

    public ConditionalStatement(GNode n) {
      e = new JavaExpression(n.getGeneric(0));
      ifStatement = new JavaStatement(n.getGeneric(1));
      if (n.get(2) != null)
        elseStatement = new JavaStatement(n.getGeneric(2));
    }

    public Printer translate(Printer out) {
      out.indent().p("if (");
      e.translate(out);
      out.pln(") {").incr();
      ifStatement.translate(out);
      if (elseStatement != null)
        out.decr().indent().pln("} else {").incr();
      out.decr().indent().pln("}");
      return out;
    }

  }

  private class ContinueStatement extends JavaStatement {

    public ContinueStatement(GNode n) {}

    public Printer translate(Printer out) {
      return out.indent().pln("continue;");
    }

  }

  private class DefaultStatement extends JavaStatement {

    public DefaultStatement(GNode n) {}

    public Printer translate(Printer out) {
      return out.indent().pln("default:");
    }

  }

  private class DoWhileStatement extends JavaStatement {

    private JavaExpression e;
    private JavaStatement s;

    public DoWhileStatement(GNode n) {
      s = new JavaStatement(n.getGeneric(0));
      e = new JavaExpression(n.getGeneric(1));
    }

    public Printer translate(Printer out) {
      out.indent().pln("do {").incr();
      s.translate(out);
      out.decr().indent().p("} while (");
      e.translate(out);
      out.pln(");");
      return out;
    }

  }

  private class ExpressionStatement extends JavaStatement {

    private JavaExpression e;

    public ExpressionStatement(GNode n) {
      e = new JavaExpression(n.getGeneric(0));
    }

    public Printer translate(Printer out) {
      out.indent();
      e.translate(out);
      return out.pln(";");
    }

  }

  private class FieldDeclaration extends JavaStatement { 

    private boolean isAbstract;
    private boolean isFinal;
    private boolean isStatic;
    private JavaType type;
    private Visibility v;
    private List<String> identifiers;
    private List<Integer> dimensions;
    private List<JavaExpression> values;

    public FieldDeclaration(GNode n) {
      identifiers = new ArrayList<String>();
      dimensions = new ArrayList<Integer>();
      values = new ArrayList<JavaExpression>();
      for (Object o : n.getNode(0)) {
        String m = ((Node)o).getString(0);
        if (m.equals("public"))
          v = Visibility.PUBLIC;
        else if (m.equals("private"))
          v = Visibility.PRIVATE;
        else if (m.equals("protected"))
          v = Visibility.PROTECTED;
        else if (m.equals("abstract"))
          isAbstract = true;
        else if (m.equals("final"))
          isFinal = true;
        else if (m.equals("static"))
          isStatic = true;
      }
      type = new JavaType(n.getGeneric(1));
      int typeDimensions = type.getDimensions();
      for (Object o : n.getNode(2)) {
        Node declarator = (Node)o;
        identifiers.add(declarator.getString(0));
        if (declarator.get(1) == null)
          dimensions.add(typeDimensions);
        else
          dimensions.add(declarator.getNode(1).size());
        if (declarator.get(2) == null)
          values.add(null);
        else
          values.add(new JavaExpression(declarator.getGeneric(2)));
      }
    }

    public Printer translate(Printer out) {
      out.indent();
      if (dimensions.size() > 0 && 0 < dimensions.get(0))
        out.p("__rt::Array<");
      type.translate(out);
      if (dimensions.size() > 0 && 0 < dimensions.get(0))
        out.p(">* ");
      else
        out.p(" ");
      int size = identifiers.size();
      for (int i = 0; i < size; i++) {
        out.p(identifiers.get(i));
        if (null != values.get(i)) {
          out.p(" = ");
          values.get(i).translate(out);
        }
        if (i < size - 1)
          out.p(", ");
        else
          out.p(";");
      }
      return out.pln();
    }

  }

  private class ForStatement extends JavaStatement {

    private boolean isFinal;
    private JavaType type;
    private List<String> identifiers;
    private List<Integer> dimensions;
    private List<JavaExpression> values;
    private JavaExpression condition;
    private List<JavaExpression> updates;
    private JavaStatement statement;

    public ForStatement(GNode n) {
      // Making the assumption that the only modifier that can appear is "final"
      if (n.getNode(0).get(0) != null)
        isFinal = true;
      if (n.getNode(0).get(1) != null)
        type = new JavaType(n.getNode(0).getGeneric(1));
      if (n.getNode(0).get(2) != null) {
        identifiers = new ArrayList<String>();
        dimensions = new ArrayList<Integer>();
        values = new ArrayList<JavaExpression>();
        for (Object o : n.getNode(0).getNode(2)) {
          Node declarator = (Node)o;
          identifiers.add(declarator.getString(0));
          if (declarator.get(1) == null)
            dimensions.add(0);
          else
            dimensions.add(declarator.getNode(1).size());
          if (declarator.get(2) == null)
            values.add(null);
          else
            values.add(new JavaExpression(declarator.getGeneric(2)));
        }
      }
      if (n.getNode(0).get(3) != null)
        condition = new JavaExpression(n.getNode(0).getGeneric(3));
      if (n.getNode(0).get(4) != null) {
        updates = new ArrayList<JavaExpression>();
        for (Object o : n.getNode(0).getNode(4)) {
          if (o == null)
            continue;
          updates.add(new JavaExpression((GNode)o));
        }
      }
      statement = new JavaStatement(n.getGeneric(1));
    }

    public Printer translate(Printer out) {
      out.indent().p("for (");
      if (isFinal)
        out.p("final ");
      if (type != null)
        type.translate(out).p(" ");
      if (identifiers != null && dimensions != null) {
        for (int i = 0; i < identifiers.size(); i++) {
          out.p(identifiers.get(i));
          // TODO: Handle C++ arrays
          // for (int j = 0; j < dimensions.get(i); j++)
          //   out.p("[]");
          if (values.get(i) != null) {
            out.p(" = ");
            values.get(i).translate(out);
          }
          if (i < identifiers.size() - 1)
            out.p(", ");
        }
      }
      out.p("; ");
      if (condition != null)
        condition.translate(out);
      out.p("; ");
      if (updates != null) {
        for (int i = 0; i < updates.size(); i++) {
          updates.get(i).translate(out);
          if (i < updates.size() - 1)
            out.p(", ");
        }
      }
      out.pln(") {").incr();
      statement.translate(out).decr();
      out.indent().pln("}");
      return out;
    }

  }

  private class ReturnStatement extends JavaStatement {

    private JavaExpression e;

    public ReturnStatement(GNode n) {
      if (n.get(0) != null)
        e = new JavaExpression(n.getGeneric(0));
    }

    public Printer translate(Printer out) {
      out.indent().p("return");
      if (e != null) {
        out.p(" ");
        e.translate(out);
      }
      return out.pln(";");
    }

  }

  private class SwitchStatement extends JavaStatement {

    public SwitchStatement(GNode n) {
      
    }

    public Printer translate(Printer out) {
      return out;
    }

  }

  private class ThrowStatement extends JavaStatement {

    private JavaExpression e;

    public ThrowStatement(GNode n) {
      e = new JavaExpression(n.getGeneric(0));
    }

    public Printer translate(Printer out) {
      out.indent().p("throw ");
      e.translate(out);
      return out.pln(";");
    }

  }

  private class TryCatchFinallyStatement extends JavaStatement {

    public TryCatchFinallyStatement(GNode n) {
      
    }

    public Printer translate(Printer out) {
      return out;
    }

  }

  private class WhileStatement extends JavaStatement {

    private JavaExpression e;
    private JavaStatement s;

    public WhileStatement(GNode n) {
      e = new JavaExpression(n.getGeneric(0));
      s = new JavaStatement(n.getGeneric(1));
    }

    public Printer translate(Printer out) {
      out.indent().p("while (");
      e.translate(out);
      out.pln(") {").incr();
      s.translate(out);
      out.decr().indent().pln("}");
      return out;
    }

  }

  public Printer translate(Printer out) {
    if (s == null)
      return out;
    return s.translate(out);
  }

}
