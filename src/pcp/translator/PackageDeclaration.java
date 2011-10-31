package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class PackageDeclaration extends Declaration {
  
  private List<String> pkg;
  
  /**
   * Constructs the PackageDeclaration.
   * 
   * @param n The PackageDeclaration node.
   */
  public PackageDeclaration(GNode n) {
    GNode name = n.getGeneric(1);
    pkg = new ArrayList<String>();
    for (Object o : name) {
      pkg.add((String)o);
    }
  }
  
  /**
   * Gets the package identifier.
   *
   * @return The package.
   */
  public List<String> getPackage() {
    return pkg;
  }

  /**
   * Gets the package as a path.
   *
   * @return The package path.
   */
  public String getPath() {
    StringBuilder p = new StringBuilder();
    int size = pkg.size();
    for (int i = 0; i < size; i++) {
      p.append(pkg.get(i));
      if (i < size - 1)
        p.append("/");
    }
    return p.toString();
  }
  
}
