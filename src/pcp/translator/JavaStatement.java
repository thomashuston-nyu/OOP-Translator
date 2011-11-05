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

  public void visitAssertStatement(GNode n) {
    
  }

  public void visitBlock(GNode n) {
    s = new Block(n);
  }

  public void visitBreakStatement(GNode n) {
    
  }
  
  public void visitConditionalStatement(GNode n) {
    
  }
  
  public void visitContinueStatement(GNode n) {
    
  }
  
  public void visitDoWhileStatement(GNode n) {
    
  }

  public void visitEmptyStatement(GNode n) {

  }
  
  public void visitExpressionStatement(GNode n) {
    
  }
  
  public void visitForStatement(GNode n) {
    
  }
  
  public void visitReturnStatement(GNode n) {
    
  }
  
  public void visitSwitchStatement(GNode n) {
    
  }
  
  public void visitSynchronizedStatement(GNode n) {
    
  }

  public void visitThrowStatement(GNode n) {
    
  }
  
  public void visitTryCatchFinallyStatement(GNode n) {
    
  }

  public void visitWhileStatement(GNode n) {
    s = new WhileStatement(n);
  }

  private class Block extends JavaStatement {
    
    private List<JavaStatement> statements;

    public Block(GNode n) {
      statements = new ArrayList<JavaStatement>();
      for (Object o : n) {
        if (null == o)
          continue;
        statements.add(new JavaStatement((GNode)o));
      }
    }

    public Printer translate(Printer out) {
      for (JavaStatement s : statements) {
        s.translate(out);
      }
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
      out.decr().pln("}");
      return out;
    }

  }

  public Printer translate(Printer out) {
    if (s == null) 
      return out;
    return s.translate(out);
  }

}
