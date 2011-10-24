package translator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import xtc.tree.GNode;
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
public class QualifiedIdentifier implements Iterable<String>, Translatable {
  
  private List<String> identifiers;
  
  /**
   * Constructs the QualifiedIdentifier.
   * 
   * @param n the QualifiedIdentifier node.
   */
  public QualifiedIdentifier(GNode n) {
    identifiers = new ArrayList<String>();
    int size = n.size();
    for (int i = 0; i < size; i++) {
      identifiers.add(n.getString(i));
    }
  }
  
  /**
   * Gets the identifier at the specified index.
   *
   * @param index the index of the identifier to retrieve.
   *
   * @return the identifier at the specific index.
   */
  public String get(int index) {
    int size = identifiers.size();
    if (index >= 0 && index < size)
      return identifiers.get(index);
    else
      return null;
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
  
  public String getType() {
    return "";
  }
  
  public String getCC(int indent, String className, List<Variable> variables) {
    StringBuilder s = new StringBuilder();
    int size = identifiers.size();
    for (int i = 0; i < size; i++) {
      s.append(identifiers.get(i));
      if (i < size - 1)
        s.append("::");
    }
    return s.toString();
  }
  
}