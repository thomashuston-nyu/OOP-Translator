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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xtc.util.Runtime;

/**
 * A static class to store globally available data;
 * eliminates need to pass lots of data up and down the tree.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 *
 * @version 1.3
 */
public class Global {

  // Maps file paths to the corresponding JavaFile objects
  public static Map<String, JavaFile> files = new HashMap<String, JavaFile>();

  // Maps package paths to the corresponding JavaPackage objects
  public static Map<String, JavaPackage> packages = new HashMap<String, JavaPackage>();

  // The runtime
  public static Runtime runtime;

  /**
   * Converts the specified variable name to a valid
   * C++ variable name.
   *
   * @return The valid variable name.
   */
  public static String getValidName(String name) {
    boolean isValid = true;
    Set<String> keys = JavaType.primitives.keySet();
    for (String key : keys) {
      if (name == JavaType.primitives.get(key)) {
        isValid = false;
        break;
      }
    }
    if (isValid)
      return name;
    else
      return "__" + name;
  }

}
