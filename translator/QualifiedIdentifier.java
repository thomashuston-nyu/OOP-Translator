package translator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * Parses an xtc QualifiedIdentifier node.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Marta Magdalena
 * @author Mike Morreale
 *
 * @version 0.1
 */
public class QualifiedIdentifier extends TranslationVisitor implements Iterable<String> {
  
  private List<String> identifiers;
  
  /**
   * Constructs the QualifiedIdentifier.
   * 
   * @param n the QualifiedIdentifier node.
   */
  public QualifiedIdentifier(GNode n) {
    identifiers = new ArrayList<String>();
    visit(n);
  }
  
  /**
   * Gets the identifier at the specified index.
   *
   * @param index the index of the identifier to retrieve.
   *
   * @return the identifier at the specific index.
   */
  public String get(int index) {
    return identifiers.get(index);
  }
  
  /**
   * Gets an iterator over the identifiers.
   *
   * @return the iterator.
   */
  public Iterator<String> iterator() {
    Iterator<String> it = identifiers.iterator();
    return it;
  }
  
  /**
   * Gets the number of identifiers.
   *
   * @return the number of identifiers.
   */
  public int size() {
    return identifiers.size();
  }
  
  /**
   * Visits an Identifier node and adds it
   * to the identifiers list.
   *
   * @param n the Identifier node to visit.
   */
  public void visitIdentifier(GNode n) {
    identifiers.add(n.getString(0));
  }
  
}