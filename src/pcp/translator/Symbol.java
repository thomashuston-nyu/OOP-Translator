/**
 * SymbolCharacters
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class Symbol {
  private String symbolCharacters;

  public Symbol(GNode n) {
    symbolCharacters = n.getString(0);
  }
}
