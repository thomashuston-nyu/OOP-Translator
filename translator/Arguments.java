/**
 * (Expression Expression*)/
 * ()
 */
package translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class Arguments extends TranslationVisitor implements Translatable {
  
  private List<Expression> expressions;

  public Arguments(GNode n) {
    expressions = new ArrayList<Expression>();
    visit(n);
  }
  
  public int size() {
    return expressions.size();
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

  // literals
  public void visitBooleanLiteral(GNode n) {
    expressions.add(new BooleanLiteral(n));
  }

  public void visitCharacterLiteral(GNode n) {
    expressions.add(new CharacterLiteral(n));
  }

  public void visitFloatingPointLiteral(GNode n) {
    expressions.add(new FloatingPointLiteral(n));
  }

  public void visitIntegerLiteral(GNode n) {
    expressions.add(new IntegerLiteral(n));
  }

  public void visitStringLiteral(GNode n) {
    expressions.add(new StringLiteral(n));
  }

  // translation
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    int size = expressions.size();
    for (int i = 0; i < size; i++) {
      s.append(expressions.get(i).getCC(indent, className, variables));
      if (i < size - 1)
        s.append(", ");
    }
    return s.toString();
  }
  
  public String getStringCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    int size = expressions.size();
    for (int i = 0; i < 1; i++) {
      s.append(expressions.get(i).getCC(indent, className, variables));
    }
    return s.toString();
  }
  
  public String getPrintCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    if (expressions.size() > 0) {
      Expression e = expressions.get(0);
      if (e instanceof PrimaryIdentifier) {
        String var = ((PrimaryIdentifier)e).getName();
        boolean found = false;
        for (Variable v : variables) {
          if (v.name.equals(var)) {
            found = true;
            if (v.type.equals("bool"))
              s.append("bool_to_string(" + var + ")");
            else if (v.type.equals("char"))
              s.append("char_to_string(" + var + ")");
            else if (v.type.equals("double"))
              s.append("double_to_string(" + var + ")");
            else if (v.type.equals("float"))
              s.append("float_to_string(" + var + ")");
            else if (v.type.equals("int32_t"))
              s.append("int_to_string(" + var + ")");
            else
              s.append(var + "->__vptr->toString(" + var + ")->data");
            break;
          }
        }
        if (!found) {
          s.append("int_to_string(__this->" + var + ")");
        }
      } else if (e instanceof Literal) {
        s.append(((Literal)e).getPrintCC());
      }
    }
    return s.toString();
  }
  
}
