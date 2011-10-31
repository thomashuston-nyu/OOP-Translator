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
 * An extension.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class Extension extends Declaration {

  private List<String> extension;
  
  /**
   * Creates the extension.
   *
   * @param n The AST node.
   */
  public Extension(GNode n) {
    extension = new ArrayList<String>();
    for (Object o : n.getGeneric(0).getGeneric(0)) {
      extension.add((String)o);
    }
  }

  /**
   * Gets the extension path.
   *
   * @return The path.
   */
  public String getPath() {
    StringBuilder s = new StringBuilder();
    int size = extension.size();
    for (int i = 0; i < size; i++) {
      s.append(extension.get(i));
      if (i < size - 1)
        s.append("/");
    }
    s.append(".java");
    return s.toString();
  }

}
