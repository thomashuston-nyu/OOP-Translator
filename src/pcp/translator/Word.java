/**
 * WordCharacters
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;

public class Word {
  private String wordCharacters;

  public Word(GNode n) {
    wordCharacters = n.getString(0);
  }
}
