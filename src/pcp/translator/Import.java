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
 * An import declaration.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class Import extends Declaration {

  private boolean star;
  private List<String> imp;
  
  /**
   * Constructs the import.
   * 
   * @param n the import declaration node.
   */
  public Import(GNode n) {
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
