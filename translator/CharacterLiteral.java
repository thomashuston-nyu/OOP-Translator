/**
 * CharacterConstant
 */
package translator;

import xtc.tree.GNode;

public class CharacterLiteral {
  private char character;

  public CharacterLiteral(GNode n) {
    character = n.getString(0);
  }
}
