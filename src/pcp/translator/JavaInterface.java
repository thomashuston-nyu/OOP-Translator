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
 * An interface declaration.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class JavaInterface extends Declaration {
  
  private ClassBody body;
  private ClassReference extension;
  private boolean isAbstract;
  private String name;
  
  /**
   * Constructs the interface.
   *
   * @param n The interface node.
   */
  public JavaInterface(GNode n) {
    for (Object o : n.getNode(0)) {
      if (((String)o).equals("abstract"))
        isAbstract = true;
    }
    name = n.getString(1);
    if (n.getNode(3).hasName("Extension")) {
      extension = new ClassReference(n.getGeneric(3));
      body = new ClassBody(n.getGeneric(4));
    } else {
      body = new ClassBody(n.getGeneric(3));
    }
  }

}
