/*
 * pcp - The Producer of C++ Programs
 * Copyright (C) 2011 Nabil Hassein, Thomas Huston, Mike Morreale, Marta Wilgan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pcp.translator;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * The path to a package; operates on
 * both <code>PackageDeclaration</code> and
 * <code>ImportDeclaration</code> nodes as
 * we need all classes from a package even if
 * the Java file only explicitly imports one.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 1.1
 */
public class JavaPackage {
  
  private List<String> pkg;
  private String filename, namespace, path;
  
  /**
   * Constructs the package using a GNode.
   * 
   * @param n The package or import declaration node.
   */
  public JavaPackage(GNode n) {
    pkg = new ArrayList<String>();
    int size;
    if (n.hasName("ImportDeclaration")) {
      if (null == n.get(2))
        size = n.getNode(1).size() - 1;
      else
        size = n.getNode(1).size();
    } else {
      size = n.getNode(1).size();
    }
    for (int i = 0; i < size; i++) {
      pkg.add(n.getNode(1).getString(i));
    }
  }

  /**
   * Creates the package using a list.
   *
   * @param s The package path as a list.
   */
  public JavaPackage(List<String> s) {
    pkg = s;
  }


  // ============================ Get Methods =======================

  /**
   * Gets the package as a filename.
   *
   * @return The filename.
   */
  public String getFilename() {
    if (null == filename)
      filename = getString("_");
    return filename;
  }

  /**
   * Gets the package as a namespace.
   *
   * @return The namespace.
   */
  public String getNamespace() {
    if (null == namespace)
      namespace = getString("::");
    return namespace;
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
    if (null == path)
      path = getString("/");
    return path;
  }

  /**
   * Creates a string using the specified separator.
   *
   * @param sep The separator.
   *
   * @return The package string.
   */
  public String getString(String sep) {
    StringBuilder p = new StringBuilder();
    int size = pkg.size();
    for (int i = 0; i < size; i++) {
      p.append(pkg.get(i));
      if (i < size - 1)
        p.append(sep);
    }
    return p.toString();
  }


  // =========================== Other Methods ======================

  /**
   * Checks if the path of two packages is the same.
   *
   * @return <code>True</code> if the path is the same;
   * <code>false</code> otherwise.
   */
  public boolean equals(JavaPackage o) {
    return o.getPath().equals(getPath());
  }

}
