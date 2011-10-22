/**
 * SymbolCharacters
 */
package translator;

import xtc.tree.GNode;

public class Symbol {
  private String symbolCharacters;

  public Symbol(GNode n) {
    symbolCharacters = n.getString(0);
  }
}
