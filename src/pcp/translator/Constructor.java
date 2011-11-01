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

import xtc.tree.GNode;
import xtc.tree.Visitor;

/**
 * A class constructor.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class Constructor extends Declaration {
  
  private Block body;
  private ThrowsClause exception;
  private String name;
  private FormalParameters parameters;
  private Visibility visibility;
  
  /**
   * Creates the constructor.
   *
   * @param n The constructor declaration node.
   */
  public Constructor(GNode n) {
    // Determine the visibility
    visibility = Visibility.PUBLIC;
    for (Object o : n.getNode(0)) {
      String m = ((GNode)o).getString(0);
      if (m.equals("private"))
        visibility = Visibility.PRIVATE;
      else if (m.equals("protected"))
        visibility = Visibility.PROTECTED;
    }

    // Get the name of the constructor
    name = n.getString(2);

    // Get the parameters
    parameters = new FormalParameters(n.getGeneric(3));

    // Get the throws clause and constructor body
    if (n.getNode(4).hasName("ThrowsClause")) {
      exception = new ThrowsClause(n.getGeneric(4));
      body = new Block(n.getGeneric(5));
    } else {
      body = new Block(n.getGeneric(4));
    }
  }

}
