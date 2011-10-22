/**
 * Parent of:
 * Literal
 * CallExpression
 * ClassLiteralExpression
 * ThisCallExpression
 * ThisExpression
 * SuperCallExpression
 * SuperExpression
 * PrimaryIdentifier
 * NewClassExpression
 * NewArrayExpression
 * Expression
 */
package translator;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PrimaryExpression extends TranslationVisitor {
  
  public PrimaryExpression(GNode n) {
    
  }

}