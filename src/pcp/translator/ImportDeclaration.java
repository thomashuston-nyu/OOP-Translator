package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class ImportDeclaration extends Declaration {

  private boolean star;
  private List<String> imp;
  
  /**
   * Constructs the ImportDeclaration.
   * 
   * @param n the ImportDeclaration node.
   */
  public ImportDeclaration(GNode n) {
    imp = new ArrayList<String>();
    GNode name = n.getGeneric(1);
    for (Object o : name) {
      imp.add((String)o);
    }
    star = null != n.get(2);
  }

  /**
   * Gets the import.
   *
   * @return The import.
   */
  public List<String> getImport() {
    return imp;
  }

  /**
   * Gets the path to the import.
   *
   * @return The path.
   */
  public String getPath() {
    StringBuilder s = new StringBuilder();
    int size = imp.size();
    for (int i = 0; i < size; i++) {
      s.append(imp.get(i));
      if (i < size - 1)
        s.append("/");
    }
    if (!star)
      s.append(".java");
    return s.toString();
  }
  
}
