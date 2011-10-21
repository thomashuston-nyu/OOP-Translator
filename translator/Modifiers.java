package translator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * Parses an xtc Modifiers node.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Marta Magdalena
 * @author Mike Morreale
 *
 * @version 0.1
 */
public class Modifiers extends TranslationVisitor implements Iterable<String> {

  private List<String> modifiers;
  
  /**
   * Constructs the Modifiers.
   * 
   * @param n the Modifiers node.
   */
  public Modifiers(GNode n) {
    modifiers = new ArrayList<String>();
    visit(n);
  }
  
  /**
   * Gets the modifier at the specified index.
   *
   * @param index the index of the modifier to retrieve.
   *
   * @return the modifier at the specific index.
   */
  public String get(int index) {
    return modifiers.get(index);
  }
  
  /**
   * Gets an iterator over the modifiers.
   *
   * @return the iterator.
   */
  public Iterator<String> iterator() {
    Iterator<String> it = modifiers.iterator();
    return it;
  }
  
  /**
   * Gets the number of modifiers.
   *
   * @return the number of modifiers.
   */
  public int size() {
    return modifiers.size();
  }
  
  /**
   * Visits a Modifier node and adds it
   * to the modifiers list.
   *
   * @param n the Modifier node to visit.
   */
  public void visitModifier(GNode n) {
    modifiers.add(n.getString(0));
  }

}