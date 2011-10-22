/**
 * CharacterConstant
 */
package translator;

import xtc.tree.GNode;

public class CharacterLiteral extends PrimaryExpression {
  
  private String character;

  public CharacterLiteral(GNode n) {
    character = n.getString(0);
  }
  
}
