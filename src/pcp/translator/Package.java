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

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * A package declaration.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class Package extends Declaration {
  
  private List<String> pkg;
  
  /**
   * Constructs the PackageDeclaration.
   * 
   * @param n The PackageDeclaration node.
   */
  public Package(GNode n) {
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
