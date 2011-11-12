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
 * @version 1.1
 */
public class Global {

  public static Map<JavaClass, JavaFile> classes = new HashMap<JavaClass, JavaFile>();
  public static Map<String, JavaFile> files = new HashMap<String, JavaFile>();
  public static Map<JavaFile, Set<JavaFile>> imports = new HashMap<JavaFile, Set<JavaFile>>();
  public static Map<String, Set<JavaFile>> packages = new HashMap<String, Set<JavaFile>>();
  public static Map<JavaStatement, Set<String>> objects = new HashMap<JavaStatement, Set<String>>();
  public static Runtime runtime;

}
