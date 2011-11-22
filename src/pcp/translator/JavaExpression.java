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
import java.util.Map;
import java.util.Set;

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
 * @version 1.2
 */
public class JavaExpression extends Visitor implements Translatable {

  private JavaExpression e;
  private JavaStatement s;
  private JavaType type;
  private GNode node;


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
    this.node = n;
    dispatch(n);
  }
  

  // ============================ Get Methods =======================

  /**
   * Gets the statement the expression is in.
   *
   * @return The statement.
   */
  public JavaStatement getStatement() {
    return s;
  }

  /**
   * Gets the resulting type of the expression.
   *
   * @return The type.
   */
  public JavaType getType() {
    if (null == type)
      e.determineType();
    return type;
  }

  /**
   * Checks if the expression has the specified name.
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
   * Checks if the type of the expression has been set.
   *
   * @return <code>True</code> if the type is set;
   * <code>false</code> otherwise.
   */
  public boolean hasType() {
    return null != type;
  }


  // ============================ Set Methods =======================

  /**
   * Determines the resulting type of the expression.
   */
  public void determineType() {}

  /**
   * Sets the resulting type of the expression.
   *
   * @param type The type.
   */
  public void setType(JavaType type) {
    this.type = type;
  }


  // =========================== Visit Methods ======================

  /**
   * Creates a new additive expression.
   *
   * @param n The additive expression node.
   */
  public void visitAdditiveExpression(GNode n) {
    e = new AdditiveExpression(n, this);
  }

  /**
   * Creates a new array initializer.
   *
   * @param n The array initializer node.
   */
  public void visitArrayInitializer(GNode n) {
    e = new ArrayInitializer(n, this);
  }

  /**
   * Creates a new basic cast expression.
   *
   * @param n The basic cast expression node.
   */
  public void visitBasicCastExpression(GNode n) {
    e = new BasicCastExpression(n, this);
  }
  
  /**
   * Creates a new bitwise and expression.
   *
   * @param n The bitwise and expression node.
   */
  public void visitBitwiseAndExpression(GNode n) {
    e = new Expression(n, "&", this);
  }

  /**
   * Creates a new bitwise negation expression.
   *
   * @param n The bitwise negation expression node.
   */
  public void visitBitwiseNegationExpression(GNode n) {
    e = new Expression(n, "~", this);
  }

  /**
   * Creates a new bitwise or expression.
   *
   * @param n The bitwise or expression node.
   */
  public void visitBitwiseOrExpression(GNode n) {
    e = new Expression(n, "|", this);
  }

  /**
   * Creates a new bitwise xor expression.
   *
   * @param n The bitwise xor expression node.
   */
  public void visitBitwiseXorExpression(GNode n) {
    e = new Expression(n, "^", this);
  }

  /**
   * Creates a new boolean literal.
   * 
   * @param n The boolean literal node.
   */
  public void visitBooleanLiteral(GNode n) {
    e = new Literal(n, this);
  }
  
  /**
   * Creates a new call expression.
   *
   * @param n The call expression node.
   */
  public void visitCallExpression(GNode n) {
    e = new CallExpression(n, this);
  }

  /**
   * Creates a new cast expression.
   *
   * @param n The cast expression node.
   */
  public void visitCastExpression(GNode n) {
    e = new CastExpression(n, this);
  }

  /**
   * Creates a new character literal
   *
   * @param n The character literal node.
   */
  public void visitCharacterLiteral(GNode n) {
    e = new Literal(n, this);
  }

  /**
   * Creates a new class literal expression.
   *
   * @param n The class literal expression node.
   */
  public void visitClassLiteralExpression(GNode n) {
    e = new ClassLiteralExpression(n, this);
  }

  /**
   * Creates a new conditional expression.
   *
   * @param n The conditional expression node.
   */
  public void visitConditionalExpression(GNode n) {
    e = new ConditionalExpression(n, this);
  }
  
  /**
   * Creates a new equality expression.
   *
   * @param n The equality expression node.
   */
  public void visitEqualityExpression(GNode n) {
    e = new Expression(n, this);
  }

  /**
   * Creates a new expression.
   *
   * @param n The expression node.
   */
  public void visitExpression(GNode n) {
    e = new Expression(n, this);
  }

  /**
   * Creates a new floating point literal
   *
   * @param n The floating point literal node.
   */
  public void visitFloatingPointLiteral(GNode n) {
    e = new Literal(n, this);
  }

  /**
   * Creates a new instanceof expression.
   *
   * @param n The instance of expression node.
   */
  public void visitInstanceOfExpression(GNode n) {
    e = new InstanceOfExpression(n, this);
  }

  /**
   * Creates a new integer literal
   *
   * @param n The integer literal node.
   */
  public void visitIntegerLiteral(GNode n) {
    e = new Literal(n, this);
  }

  /**
   * Creates a new logical and expression.
   *
   * @param n The logical and expression node.
   */
  public void visitLogicalAndExpression(GNode n) {
    e = new Expression(n, "&&", this);
  }

  /**
   * Creates a new logical negation expression.
   *
   * @param n The logical negation expression node.
   */
  public void visitLogicalNegationExpression(GNode n) {
    e = new Expression(n, "!", this);
  }

  /**
   * Creates a new logical or expression.
   *
   * @param n The logical or expression node.
   */
  public void visitLogicalOrExpression(GNode n) {
    e = new Expression(n, "||", this);
  }

  /**
   * Creates a new multiplicative expression.
   *
   * @param n The multiplicative expression node.
   */
  public void visitMultiplicativeExpression(GNode n) {
    e = new MultiplicativeExpression(n, this);
  }
  
  /**
   * Creates a new new array expression.
   *
   * @param n The new array expression node.
   */
  public void visitNewArrayExpression(GNode n) {
    e = new NewArrayExpression(n, this);
  }
  
  /**
   * Creates a new new class expression.
   *
   * @param n The new class expression node.
   */
  public void visitNewClassExpression(GNode n) {
    e = new NewClassExpression(n, this);
  }

  /**
   * Creats a new null literal.
   *
   * @param n The new null literal node.
   */
  public void visitNullLiteral(GNode n) {
    e = new Literal(n, this);
  }
  
  /**
   * Creates a new postfix expression.
   *
   * @param n The postfix expression node.
   */
  public void visitPostfixExpression(GNode n) {
    e = new UnaryExpression(n, this);
  }

  /**
   * Creates a new primary identifier.
   *
   * @param n The primary identifier node.
   */
  public void visitPrimaryIdentifier(GNode n) {
    e = new PrimaryIdentifier(n, this);
  }

  /**
   * Creates a new relational expression.
   *
   * @param n The relational expression node.
   */
  public void visitRelationalExpression(GNode n) {
    e = new Expression(n, this);
  }

  /**
   * Creates a new selection expression.
   *
   * @param n The selection expression node.
   */
  public void visitSelectionExpression(GNode n) {
    e = new SelectionExpression(n, this);
  }

  /**
   * Creates a new shift expression.
   *
   * @param n The shift expression node.
   */
  public void visitShiftExpression(GNode n) {
    e = new Expression(n, this);
  }

  /**
   * Creates a new string literal.
   *
   * @param n The string literal node.
   */
  public void visitStringLiteral(GNode n) {
    e = new Literal(n, this);
  }

  /**
   * Creates a new subscript expression.
   *
   * @param n The subscript expression node.
   */
  public void visitSubscriptExpression(GNode n) {
    e = new SubscriptExpression(n, this);
  }

  /**
   * Creates a new this expression.
   *
   * @param n The this expression node.
   */
  public void visitThisExpression(GNode n) {
    // Nothing to do
  }

  /**
   * Creates a new unary expression.
   *
   * @param n The unary expression node.
   */
  public void visitUnaryExpression(GNode n) {
    e = new UnaryExpression(n, this);
  }

  /**
   * Creats a new variable initializer.
   *
   * @param n The variable initializer.
   */
  public void visitVariableInitializer(GNode n) {
    e = new VariableInitializer(n, this);
  }


  // ========================== Nested Classes ======================

  /**
   * An additive expression 
   * (for example, <code>"s" + "o"</code>
   * or <code>1 + 3</code>).
   */
  private class AdditiveExpression extends JavaExpression {

    private JavaExpression left, right;
    private String operator;
    private boolean isString;
    private JavaExpression parent;

    /**
     * Creates a new additive expression.
     *
     * @param n The additive or multiplicative expression node.
     * @param parent The wrapper expression.
     */
    public AdditiveExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      left = new JavaExpression(n.getGeneric(0), parent.getStatement());
      operator = n.getString(1);
      right = new JavaExpression(n.getGeneric(2), parent.getStatement());
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      if (left.getType().hasType("String") || right.getType().hasType("String")) {
        isString = true;
        parent.setType(new JavaType("String"));
      } else if (left.getType().hasType("double") || right.getType().hasType("double")) {
        parent.setType(new JavaType("double"));
      } else if (left.getType().hasType("float") || right.getType().hasType("float")) {
        parent.setType(new JavaType("float"));
      } else if (left.getType().hasType("long") || right.getType().hasType("long")) {
        parent.setType(new JavaType("long"));
      } else {
        parent.setType(new JavaType("int"));
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
      determineType();
      if (!isString) {
        left.translate(out);
        out.p(" ").p(operator).p(" ");
        right.translate(out);
        return out;
      } else {
        out.pln("({");
        out.incr().indent().pln("std::ostringstream sout;");
        out.indent().p("sout << ");
        left.translate(out).p(" << ");
        right.translate(out).pln(";");
        out.indent().pln("String $s$ = new __String(sout.str());");
        out.indent().pln("$s$;");
        out.decr().indent().p("})");
        return out;
      }
    }

  }

  /**
   * An array initializer
   * (for example, <code>{5, 3, 4, 6, 7}</code>).
   */
  private class ArrayInitializer extends JavaExpression {

    private List<JavaExpression> variables;
    private JavaExpression parent;

    /**
     * Creates a new array initializer.
     *
     * @param n The array initializer node.
     * @param parent The wrapper expression.
     */
    public ArrayInitializer(GNode n, JavaExpression parent) {
      this.parent = parent;
      variables = new ArrayList<JavaExpression>();
      for (Object o : n) {
        variables.add(new JavaExpression((GNode)o, parent.getStatement()));
      }
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      JavaType type = new JavaType(variables.get(0).getType().getJavaType());
      type.setDimensions(1 + variables.get(0).getType().getDimensions());
      parent.setType(type);
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
      determineType();
      out.pln("({").incr();
      out.indent().p("__rt::Ptr<");
      parent.getType().translate(out).p(" > $a$ = new ");
      parent.getType().translate(out).p("(").p(variables.size()).pln(");");
      for (int i = 0; i < variables.size(); i++) {
        out.indent().p("(*$a$)[").p(i).p("] = ");
        variables.get(i).translate(out).pln(";");
      }
      out.indent().pln("$a$;");
      out.decr().indent().p("})");
      return out;
    }

  }

  /**
   * A basic cast expression
   * (for example, <code></code>).
   */
  private class BasicCastExpression extends JavaExpression {

    private JavaExpression e;
    private JavaType castType;
    private JavaExpression parent;

    /**
     * Creates a new basic cast expression.
     *
     * @param n The basic cast expression node.
     * @param parent The wrapper expression.
     */
    public BasicCastExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      castType = new JavaType(n.getGeneric(0));
      if (null != n.get(1))
        castType.setDimensions(n.getNode(1).size());
      e = new JavaExpression(n.getGeneric(2), parent.getStatement());
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(castType);
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
      determineType();
      out.p("__rt::java_cast<");
      castType.translate(out).p(">(");
      return e.translate(out).p(")");
    }

  }

  /**
   * A call expression
   * (for example, <code>System.out.println("test")</code>).
   */
  private class CallExpression extends JavaExpression {

    // The arguments being passed
    private List<JavaExpression> args;

    // The types of the method parameters
    private List<String> argTypes;

    // The name of the class this method is being called on
    private String classType;

    // The actual class this method is being called on
    private JavaClass cls;

    // Checks if the method is being called from inside a constructor
    // and whether or not this is a print call
    private boolean isConstructor, isPrint;

    // The method being called
    private JavaMethod method;

    // The mangled name of the method
    private String name;

    // The variable or class on which the method is called
    private List<String> ref;

    // The expression wrapper object that created this instance
    private JavaExpression parent;

    /**
     * Creates a new call expression.
     *
     * @param n The call expression node.
     * @param parent The wrapper expression.
     */
    public CallExpression(GNode n, JavaExpression parent) {
      // Keep track of the parent expression
      this.parent = parent;

      // Check if this expression is in the constructor
      isConstructor = parent.getStatement().getScope().hasName("JavaConstructor");

      // Determine the variable or class that is making the call
      if (null != n.get(0)) {
        ref = new ArrayList<String>();
        int depth = 0;
        GNode temp = n.getGeneric(0);
        if (temp.hasName("SelectionExpression")) {
          while (temp.hasName("SelectionExpression")) {
            depth++;
            temp = temp.getGeneric(0);
          }   
          for (int i = depth - 1; i >= 0; i--) {
            temp = n.getGeneric(0);
            int j = 0;
            while (j++ < i)
              temp = temp.getGeneric(0);
            if (temp.getNode(0).hasName("PrimaryIdentifier"))
              ref.add(temp.getNode(0).getString(0));
            ref.add(temp.getString(1));
          }
        } else {
          ref.add(n.getNode(0).getString(0));
        }
        if (1 == ref.size() && parent.getStatement().getScope().isInScope("$" + ref.get(0)))
          parent.getStatement().addObject("$" + ref.get(0));
      }

      // Get the arguments passed to the method
      args = new ArrayList<JavaExpression>();
      for (Object o : n.getNode(3)) {     
        GNode node = (GNode)o;
        args.add(new JavaExpression(node, parent.getStatement()));
      }

      // Check if this is a print expression
      if (null != ref && 2 == ref.size() && ref.get(0).equals("System") && ref.get(1).equals("out")
          && (n.getString(2).equals("print") || n.getString(2).equals("println")))
        isPrint = true;

      // Raw method name
      name = n.getString(2);
    }

    /**
     * Determines the class on which the method is being called.
     */
    public void determineClass() {
      // If we are calling this method on some variable, figure out what type it is
      if (null != ref) {
        if (1 == ref.size()) {
          // If it's a variable, figure out what type it is 
          if (parent.getStatement().getScope().isInScope("$" + ref.get(0)))
            classType = parent.getStatement().getScope().getVariableType("$" + ref.get(0)).getType();
          // Otherwise using the static class name
          else
            classType = ref.get(0);
          JavaScope temp = parent.getStatement().getScope();
          while (!temp.hasName("JavaClass")) {
            temp = temp.getParentScope();
          }
          JavaClass cls = (JavaClass)temp;
          // Check the current package
          JavaPackage pkg = cls.getFile().getPackage();
          if (null != JavaClass.getJavaClass(pkg.getPath() + classType)) {
            this.cls = JavaClass.getJavaClass(pkg.getPath() + classType);
            return;
          }
          // Then check the imported packages
          Set<JavaPackage> imports = cls.getFile().getImports();
          for (JavaPackage imp : imports) {
            if (null != JavaClass.getJavaClass(imp.getPath() + classType)) {
              this.cls = JavaClass.getJavaClass(imp.getPath() + classType);
              return;
            }
          }
        // Either a qualified class name or a static variable in a class
        } else {
          // First, check if this is a fully qualified class name
          StringBuilder temp = new StringBuilder();
          for (int i = 0; i < ref.size() - 1; i++) {
            temp.append(ref.get(i));
            if (i < ref.size() - 2)
              temp.append("/");
          }
          String full = temp.toString();
          if (Global.packages.containsKey(full)) {
            JavaPackage pkg = Global.packages.get(full);
            List<JavaFile> files = pkg.getFiles();
            for (JavaFile f : files) {
              if (f.getPublicClass().getName().equals(ref.get(ref.size() - 1))) {
                this.cls = f.getPublicClass();
                classType = ref.get(ref.size() - 1);
                return;
              }
            }
          }
          // Next, check if this is a fully qualified class name with a static variable
          if (2 < ref.size()) {
            temp = new StringBuilder();
            for (int i = 0; i < ref.size() - 2; i++) {
              temp.append(ref.get(i));
              if (i < ref.size() - 3)
                temp.append("/");
            }
            String partial = temp.toString();
            if (Global.packages.containsKey(partial)) {
              JavaPackage pkg = Global.packages.get(partial);
              List<JavaFile> files = pkg.getFiles();
              for (JavaFile f : files) {
                if (f.getPublicClass().getName().equals(ref.get(ref.size() - 2))) {
                  JavaClass varClass = f.getPublicClass();
                  if (varClass.isInScope("$" + ref.get(ref.size() - 1))) {
                    JavaType varType = varClass.getVariableType("$" + ref.get(ref.size() - 1));
                    if (0 == varType.getDimensions() && !varType.isPrimitive()) {
                      classType = varType.getClassType();
                      this.cls = JavaClass.getJavaClass(partial + "/" + classType);
                      return;
                    }
                  }
                }
              }
            }
          }
          // Finally, check if this is an unqualified class name with a static variable
        }
      // Otherwise we are calling this method from the current class
      } else {
        JavaScope temp = parent.getStatement().getScope();
        while (!temp.hasName("JavaClass")) {
          temp = temp.getParentScope();
        }
        this.cls = (JavaClass)temp;
        classType = this.cls.getName();
      }
    }

    /**
     * Locates the closest available method and performs name mangling
     * for method overloading.
     */
    public void determineMethod() {
      // If this is a print call, there's no method to locate
      if (isPrint)
        return;

      // Used for performing a breadth-first search
      int level = 0, maxLevel = 0;

      // Keep track of the types of the arguments passed
      List<String> newArgTypes = new ArrayList<String>();

      // Get the class hierarchy
      Map<String, String> hierarchy = JavaType.getHierarchy();

      // Get the types of the arguments and the depth of the type furthest from Object
      for (JavaExpression e : args) {
        if (!e.getType().isPrimitive() && 0 == e.getType().getDimensions()) {
          String type = e.getType().getClassType();
          int depth = 0;
          while (null != hierarchy.get(type)) {
            depth++;
            type = hierarchy.get(type);
          }
          if (depth > maxLevel)
            maxLevel = depth;
          newArgTypes.add(e.getType().getClassType());
        } else {
          newArgTypes.add(null);
        }
      }

      // Loop until a match is found or we have tried every combination
      while (true) {
        // If there are no arguments, simply locate use the original method name
        if (0 == newArgTypes.size()) {
          if (null != cls)
            method = cls.getMethod(name);
          return;
        }

        // The outer loop specifies the argument to climb one level higher in the hierarchy
        for (int i = 0; i < newArgTypes.size(); i++) {
          // Create the mangled name
          StringBuilder s = new StringBuilder();
          s.append(name);
          argTypes = new ArrayList<String>();

          // The middle loop adds the argument types to the name in order
          for (int j = 0; j < newArgTypes.size(); j++) {
            // If this is a primitive type, we can't climb up the hierarchy so simply append it
            if (null == newArgTypes.get(j)) {
              String t = args.get(j).getType().getMangledType();
              s.append("$" + t);
              argTypes.add(t);
            // Otherwise climb the tree as specified by the level
            } else {
              // Start out with the original type of the argument
              String type = newArgTypes.get(j);
              // Climb the tree level - 1 times
              for (int k = 1; k < level; k++) {
                if (null == hierarchy.get(type))
                  return;
                type = hierarchy.get(type);
              }
              // If this is the argument specified by the outer loop, climb an extra level
              if (0 < level && i == j && null != hierarchy.get(type))
                type = hierarchy.get(type);
              s.append("$" + type);
              argTypes.add(type);
            }
          }
          String temp = s.toString();

          // Special cases for methods defined in java_lang
          if (temp.equals("equals$Object") ||
              temp.equals("charAt$int32_t")) {
            name = temp;
            return;
          }
          // Check if the specified method exists
          if (null != cls.getMethod(temp)) {
            name = temp;
            method = cls.getMethod(temp);
            return;
          }
        }

        // If the method was not found, continue up another level in the hierarchy
        level++;
        // Return if we have already reached the top of the hierarchy for all arguments
        if (level > maxLevel)
          return;
      }
    }
    
    /**
     * Determines the return type of the method call.
     */
    public void determineType() {
      if (parent.hasType())
        return;

      // Locate the exact method being called before continuing
      determineClass();
      determineMethod();

      // Special cases for methods defined in java_lang
      if (null == method) {
        if (name.equals("hashCode") || name.equals("length")) {
          parent.setType(new JavaType("int"));
          return;
        } else if (name.equals("equals$Object") || name.equals("isPrimitive") ||
            name.equals("isArray")) {
          parent.setType(new JavaType("boolean"));
          return;
        } else if (name.equals("getClass") || name.equals("getComponentType") ||
            name.equals("getSuperclass")) {
          parent.setType(new JavaType("Class"));
          return;
        } else if (name.equals("toString") || name.equals("getName")) {
          parent.setType(new JavaType("String"));
          return;
        } else if (name.equals("charAt$int32_t")) {
          parent.setType(new JavaType("char"));
          return;
        }
      }

      // If found, simply use the return type of the method being called
      if (null != method) {
        parent.setType(method.getReturnType());
      }

      // If this method is being called on a variable or class
      if (null != ref) {
        // If this is a print statement the resulting expression has no value
        if (2 == ref.size() && ref.get(0).equals("System") && ref.get(1).equals("out")
            && (name.equals("print") || name.equals("println"))) {
          parent.setType(new JavaType("void"));
        } else {
          // TODO: reference to some explicit package and class
          String path = "";
          int refsize = ref.size();
          for (int i = 0; i < refsize - 1; i++) {
            if (0 < i)
              path += "/";
            path += ref.get(i);
          }
          path += ".java";
          JavaFile f;
          if (Global.files.containsKey(path))
            f = Global.files.get(path);
        }
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
      // Determine the resulting type before continuing
      determineType();
      if (null != ref) {
        // Special case for print statements
        if (isPrint) {
          if ((name.equals("print") && args.size() > 0) || name.equals("println"))
            out.p("std::cout");
          // Inject each argument into the output stream
          int argsize = args.size();
          for (int i = 0; i < argsize; i++) {
            out.p(" << ");
            if (args.get(i).getType().getType().equals("float") ||
                args.get(i).getType().getType().equals("double")) {
              out.pln("({").incr();
              out.indent().pln("std::ostringstream sout;");
              out.indent().p("if (modf(");
              args.get(i).translate(out).pln(", new double) == 0)");
              out.indentMore().p("sout << ");
              args.get(i).translate(out).pln(" << \".0\";");
              out.indent().pln("else");
              out.indentMore().p("sout << ");
              args.get(i).translate(out).pln(";");
              out.indent().pln("String $s$ = new __String(sout.str());");
              out.indent().pln("$s$;");
              out.decr().indent().p("})");
            } else if (args.get(i).getType().getType().equals("bool")) {
              out.p("(");
              args.get(i).translate(out).p(" ? \"true\" : \"false\")");
            } else if (args.get(i).getType().getType().equals("unsigned char")) {
              out.p("(int16_t)");
              args.get(i).translate(out);
            } else {
              args.get(i).translate(out);
              // If the argument is an object, call toString()
              if ((0 == args.get(i).getType().getDimensions() && 
                  !args.get(i).getType().isPrimitive() &&
                  !args.get(i).getType().getClassType().equals("String")) ||
                  args.get(i).getType().isArray()) {
                out.p("->__vptr->toString(");
                args.get(i).translate(out);
                out.p(")");
              }
            }
          }
          // Append a newline if necessary
          if (name.equals("println"))
            out.p(" << std::endl");
        } else {
          String namespace = "";
          int refsize = ref.size();
          if (1 < refsize) {
            for (int i = 0; i < refsize; i++) {
              if (0 < i)
                namespace += "::";
              namespace += ref.get(i);
            }
          } else {
            namespace = "$" + ref.get(0);
          }
          if (null == method || !method.isStatic()) {
            out.p(namespace).p("->");
            if (null == method || method.isVirtual())
              out.p("__vptr->");
          } else {
            out.p("__").p(method.getClassFrom().getName()).p("::");
          }
          out.p(name).p("(");
          if (null == method || !method.isStatic())
            out.p(namespace);
          int argsize = args.size();
          for (int i = 0; i < argsize; i++) {
            out.p(", ");
            args.get(i).translate(out);
          }
          out.p(")");
        }
      } else {
        if (null == ref && null != method && method.isStatic())
          out.p("__").p(method.getClassFrom().getName()).p("::");
        else if (!isConstructor)
          out.p("__this->");
        if (null == method || method.isVirtual())
          out.p("__vptr->");
        out.p(name).p("(");
        if (null == method || !method.isStatic()) {
          out.p("__this");
          if (0 < args.size());
            out.p(", ");
        }
        int size = args.size();
        for (int i = 0; i < size; i++) {
          args.get(i).translate(out);
          if (i < size - 1)
            out.p(", ");
        }
        out.p(")");
      }
      // TODO: calling methods from this class, static or instance
      return out;
    }
  }

  /**
   * A cast expression
   * (for example, <code>(Node)o</code>).
   */
  private class CastExpression extends JavaExpression {

    private JavaType type;
    private JavaExpression parent;
     
    /**
     * Creates a new cast expression.
     *
     * @param n The cast expression node.
     * @param parent The wrapper expression.
     */
    public CastExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      type = new JavaType(n.getGeneric(0));
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(type);
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
      determineType();
      return out.p("__rt::java_cast<").p(type.getType()).p(">");
    }

  }

  /**
   * A class literal expression
   * (for example, <code></code>).
   */
  private class ClassLiteralExpression extends JavaExpression {

    private JavaType type;
    private JavaExpression parent;

    /**
     * Creates a new class literal expression.
     *
     * @param n The class literal expression node.
     * @param parent The wrapper expression.
     */
    public ClassLiteralExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      type = new JavaType(n.getGeneric(0));
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(type);
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
      determineType();
      // TODO: figure out what a class literal expression is
      return out;
    }

  }

  /**
   * A conditional expression
   * (for example, <code>x &lt; 5 ? 1 : 2</code>).
   */
  private class ConditionalExpression extends JavaExpression {

    private JavaExpression test, ifTrue, ifFalse, parent;

    /**
     * Creates a new conditional expression.
     *
     * @param n The conditional expression node.
     * @param parent The wrapper expression.
     */
    public ConditionalExpression(GNode n, JavaExpression parent) {
      test = new JavaExpression(n.getGeneric(0), parent.getStatement());
      ifTrue = new JavaExpression(n.getGeneric(1), parent.getStatement());
      ifFalse = new JavaExpression(n.getGeneric(2), parent.getStatement());
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(ifTrue.getType());
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
      determineType();
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

    private GNode n;
    private JavaExpression left, right, parent;
    private String operator;

    /**
     * Creates a new expression or equality expression.
     *
     * @param n The expression node.
     * @param parent The wrapper expression.
     */
    public Expression(GNode n, JavaExpression parent) {
      this.n = n;
      this.parent = parent;
      left = new JavaExpression(n.getGeneric(0), parent.getStatement());
      operator = n.getString(1);
      right = new JavaExpression(n.getGeneric(2), parent.getStatement());
    }

    /**
     * Creates a new bitwise or logical expression.
     *
     * @param n The expression node.
     * @param operator The operator.
     */
    public Expression(GNode n, String operator, JavaExpression parent) {
      this.n = n;
      this.parent = parent;
      left = new JavaExpression(n.getGeneric(0), parent.getStatement());
      this.operator = operator;
      if (!operator.equals("~") && !operator.equals("!"))
        right = new JavaExpression(n.getGeneric(1), parent.getStatement()); 
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      if (n.hasName("Expression")) {
        parent.setType(left.getType());
      } else if (n.hasName("EqualityExpression") || n.hasName("RelationalExpression") ||
          n.getName().startsWith("Logical")) {
        parent.setType(new JavaType("boolean"));
      } else if (n.hasName("ShiftExpression")) {
        parent.setType(left.getType());
      } else if (n.getName().startsWith("Bitwise")) {
        if (left.getType().hasType("long") || (right != null && right.getType().hasType("long")))
          parent.setType(new JavaType("long"));
        else
          parent.setType(new JavaType("int"));
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
      determineType();
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

    private JavaExpression object, parent;
    private JavaType type;
     
    /**
     * Creates a new instance of expression.
     *
     * @param n The instance of expression node.
     * @param parent The wrapper expression.
     */
    public InstanceOfExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      object = new JavaExpression(n.getGeneric(0), parent.getStatement());
      type = new JavaType(n.getGeneric(1));
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(new JavaType("boolean"));
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
      determineType();
      out.pln("({");
      out.incr().indent().p("Class k = __").p(type.getType()).pln("::__class();");
      out.indent().p("k->__vptr->isInstance(k, ");
      object.translate(out).pln(");");
      out.decr().indent().p("})");
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
    private GNode n;
    private JavaExpression parent;

    /**
     * Creates a new literal.
     *
     * @param n The literal node.
     * @param parent The wrapper expression.
     */
    public Literal(GNode n, JavaExpression parent) {
      this.n = n;
      this.parent = parent;
      if (n.size() == 0)
        isNull = true;
      else {
        value = n.getString(0);
      }
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      if (n.hasName("BooleanLiteral")) {
        parent.setType(new JavaType("boolean"));
      } else if (n.hasName("CharacterLiteral")) {
        parent.setType(new JavaType("char"));
      } else if (n.hasName("FloatingPointLiteral")) {
        if (value.charAt(value.length() - 1) == 'F' || value.charAt(value.length() - 1) == 'f')
          parent.setType(new JavaType("float"));
        else
          parent.setType(new JavaType("double"));
      } else if (n.hasName("IntegerLiteral")) {
        if (value.charAt(value.length() - 1) == 'L' || value.charAt(value.length() - 1) == 'l')
          parent.setType(new JavaType("long"));
        else
          parent.setType(new JavaType("int"));
      } else if (n.hasName("StringLiteral")) {
        isString = true;
        parent.setType(new JavaType("String"));
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
      determineType();
      if (isNull)
        return out.p("__rt::null()");
      else if (isString)
        return out.p("__rt::literal(").p(value).p(")");
      else
        return out.p(value);
    }

  }

  /**
   * A multiplicative expression
   * (for example, <code>2 * 5</code>).
   */
  private class MultiplicativeExpression extends JavaExpression {

    private JavaExpression left, right, parent;
    private String operator;

    /**
     * Creates a new multiplicative expression.
     *
     * @param n The multiplicative expression node.
     * @param parent The wrapper expression.
     */
    public MultiplicativeExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      left = new JavaExpression(n.getGeneric(0), parent.getStatement());
      right = new JavaExpression(n.getGeneric(2), parent.getStatement());
      operator = n.getString(1);
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      if (left.getType().hasType("double") || right.getType().hasType("double"))
        parent.setType(new JavaType("double"));
      else if (left.getType().hasType("float") || right.getType().hasType("float"))
        parent.setType(new JavaType("float"));
      else if (left.getType().hasType("long") || right.getType().hasType("long"))
        parent.setType(new JavaType("long"));
      else
        parent.setType(new JavaType("int"));
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
      determineType();
      left.translate(out);
      out.p(" ").p(operator).p(" ");
      right.translate(out);
      return out;
    }

  }

  /**
   * A new array expression
   * (for example, <code>new String[5]</code>).
   */
  private class NewArrayExpression extends JavaExpression {

    private List<JavaExpression> dimensions;
    private JavaType type;
    private JavaStatement statement;
    private JavaExpression parent;

    /**
     * Creates a new new array expression.
     *
     * @param n The new array expression node.
     * @param parent The wrapper expression.
     */
    public NewArrayExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      statement = parent.getStatement();
      type = new JavaType(n.getGeneric(0));
      dimensions = new ArrayList<JavaExpression>();
      for (Object o : n.getNode(1)) {
        if (null == o)
          continue;
        dimensions.add(new JavaExpression((GNode)o, parent.getStatement()));
      }
      type.setDimensions(dimensions.size());
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(type);
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
      determineType();
      if (1 < dimensions.size()) {
        out.pln("({").incr();
        for (int i = 0; i < dimensions.size(); i++) {
          out.indent().p("__rt::Ptr<");
          type.setDimensions(dimensions.size() - i);
          type.translate(out).p(" > $a").p(i).p("$ = new ");
          type.translate(out).p("(");
          dimensions.get(i).translate(out).pln(");");
          if (i < dimensions.size() - 1) {
            out.indent().p("for (int32_t $i").p(i).p("$ = 0; $i").p(i).p("$ < ");
            dimensions.get(i).translate(out).p("; $i").p(i).pln("$++) {").incr();
          }
        }
        for (int i = dimensions.size() - 1; i > 0; i--) {
          out.indent().p("(*$a").p(i-1).p("$)[$i").p(i-1).p("$] = $a").p(i).pln("$;");
          out.decr().indent().pln("}");
        }
        out.indent().pln("$a0$;");
        out.decr().indent().p("})");
      } else {
        out.p("new ");
        type.translate(out).p("(");
        dimensions.get(0).translate(out).p(")");
      }
      return out;
    }

  }

  /**
   * A new class expression
   * (for example, <code>new Object()</code>).
   */
  private class NewClassExpression extends JavaExpression {

    private List<JavaExpression> arguments;
    private JavaType type;
    private JavaExpression parent;

    /**
     * Creates a new new class expression.
     *
     * @param n The new class expression node.
     * @param parent The wrapper expression.
     */
    public NewClassExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      type = new JavaType(n.getGeneric(2));
      arguments = new ArrayList<JavaExpression>();
      for (Object o : n.getNode(3)) {
        if (null == o)
          continue;
        arguments.add(new JavaExpression((GNode)o, parent.getStatement()));
      }
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(type);
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
      determineType();
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

    private boolean isClassVar;
    private String name;
    private JavaExpression parent;

    /**
     * Creates a new primary identifier.
     *
     * @param n The primary identifier node.
     * @param parent The wrapper expression.
     */
    public PrimaryIdentifier(GNode n, JavaExpression parent) {
      this.parent = parent;
      name = "$" + n.getString(0);
      if (parent.getStatement().getScope().isInScope(name)) {
        if (!parent.getStatement().getScope().getVariableType(name).isPrimitive())
          parent.getStatement().addObject(name);
        if (parent.getStatement().getScope().getVariableScope(name).hasName("JavaClass")) 
          isClassVar = true;
      }
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      if (parent.getStatement().getScope().isInScope(name))
        parent.setType(parent.getStatement().getScope().getVariableType(name));
      else
        parent.setType(new JavaType("void"));
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
      determineType();
      if (isClassVar)
        out.p("__this->");
      return out.p(name);
    }

  }

  /**
   * A selection expression
   * (for example, <code>System.out</code>).
   */
  private class SelectionExpression extends JavaExpression {

    private JavaExpression identifier, parent;
    private String selection, first;

    /**
     * Creates a new selection expression.
     *
     * @param n The selection expression node.
     */
    public SelectionExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      if (!n.getNode(0).hasName("ThisExpression")) {
        if (n.getNode(0).hasName("PrimaryIdentifier"))
          first = n.getNode(0).getString(0);
        else
          identifier = new JavaExpression(n.getGeneric(0), parent.getStatement());
      }
      selection = n.getString(1);
      if (parent.getStatement().getScope().isInScope("$" + selection))
        selection = "$" + selection;
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(new JavaType("void"));
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
      determineType();
      if (null == identifier) {
        if (null != first && parent.getStatement().getScope().isInScope("$" + first))
          return out.p("$").p(first).p("->").p(selection);
        else
          return out.p("__this->").p(selection);
      } else {
        return identifier.translate(out).p("::").p(selection);
      }
    }

  }

  /**
   * A subscript expression
   * (for example, <code>a[1]</code>).
   */
  private class SubscriptExpression extends JavaExpression {

    private JavaExpression variable, parent;
    private List<JavaExpression> indices;

    /**
     * Creates a new subscript expression.
     *
     * @param n The subscript expression node.
     * @param parent The wrapper expression.
     */
    public SubscriptExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      variable = new JavaExpression(n.getGeneric(0), parent.getStatement());
      indices = new ArrayList<JavaExpression>();
      for (int i = 1; i < n.size(); i++)
        indices.add(new JavaExpression(n.getGeneric(i), parent.getStatement()));
      if (n.getNode(0).hasName("PrimaryIdentifier") &&
          parent.getStatement().getScope().isInScope("$" + n.getNode(0).getString(0)))
        parent.getStatement().addObject(n.getNode(0).getString(0));
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(variable.getType().getArrayType());
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
      determineType();
      for (JavaExpression e : indices)
        out.p("(*");
      variable.translate(out);
      for (JavaExpression e : indices) {
        out.p(")[");
        e.translate(out).p("]");
      }
      return out;
    }

  }

  /**
   * A postfix expression
   * (for example, <code>x++</code>),
   * or a unary expression
   * (for example, <code>++x</code>).
   */
  private class UnaryExpression extends JavaExpression {

    private JavaExpression identifier, parent;
    private String pre, post;

    /**
     * Creates a new postfix or unary expression.
     *
     * @param n The postfix or unary expression node.
     * @param parent The wrapper expression.
     */
    public UnaryExpression(GNode n, JavaExpression parent) {
      this.parent = parent;
      if (n.get(0) instanceof String) {
        pre = n.getString(0);
        identifier = new JavaExpression(n.getGeneric(1), parent.getStatement());
      } else {
        identifier = new JavaExpression(n.getGeneric(0), parent.getStatement());
        post = n.getString(1);
      }
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(identifier.getType());
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
      determineType();
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

    private JavaExpression parent;

    /**
    * Creats a new variable initializer.
    *
    * @param n The variable initializer.
    */
    public VariableInitializer(GNode n, JavaExpression parent) {
      this.parent = parent;
    }

    /**
     * Determines the resulting type of the expression.
     */
    public void determineType() {
      if (parent.hasType())
        return;
      parent.setType(new JavaType("void"));// TODO: fix this
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
      determineType();
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
