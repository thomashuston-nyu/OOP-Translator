/**
 * WordCharacters
 */
package translator;

import xtc.tree.GNode;

public class Word {
  private String wordCharacters;

  public Word(GNode n) {
    wordCharacters = n.getString(0);
  }
}
