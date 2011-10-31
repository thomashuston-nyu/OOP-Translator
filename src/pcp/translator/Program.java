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

import java.io.File;
import java.io.FilenameFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xtc.lang.JavaFiveParser;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;

/**
 * A complete Java program.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class Program {

  private Map<String, CompilationUnit> files;
  private String classpath;
  private File main;

  /**
   * Creates a new program.
   *
   * @param file The main Java file.
   * @param node The xtc AST of the Java file.
   *
   * @throws IOException Signals an I/O error.
   * @throws ParseException Signals a parse error.
   */
  public Program(File file, Node node) throws IOException, ParseException {
    // Instantiate the hash
    files = new HashMap<String, CompilationUnit>();

    // Set the main program file
    main = file;

    // Find the classpath for the program
    CompilationUnit c = new CompilationUnit((GNode)node);
    PackageDeclaration pkg = c.getPackage();
    String absPath = file.getAbsolutePath();
    absPath = absPath.substring(0, absPath.lastIndexOf("/")+1);
    if (null != pkg) {
      String pkgPath = pkg.getPath();
      int index = absPath.lastIndexOf(pkgPath);
      if (0 > index)
        throw new RuntimeException("Java file package name does not match directory");
      classpath = absPath.substring(0, index);
    } else {
      classpath = absPath; 
    }

    // Resolve dependencies
    resolve(file, c);
  }
  
  /**
   * Parses the specified file.
   *
   * @param file The corresponding file.
   *
   * @return The AST corresponding to the file's contents, 
   * or null if no tree has been generated.
   *
   * @throws IOException Signals an I/O error.
   * @throws ParseException Signals a parse error.
   */
  public GNode parse(File file) throws IOException, ParseException {
    Reader in = new FileReader(file);
    JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (GNode)parser.value(result);
  }

  /**
   * Recursively resolves dependencies for the specified class.
   *
   * @param file The file to resolve.
   * @param c The compilation unit for the file.
   *
   * @throws IOException Signals an I/O error.
   * @throws ParseException Signals a parse error.
   */
  public void resolve(File file, CompilationUnit c) throws IOException, ParseException {
    if (files.containsKey(file.getAbsolutePath())) 
      return;

    // Add the file to the hash
    String filePath = file.getAbsolutePath();
    files.put(filePath, c);

    // Resolve dependencies for classes in the package
    PackageDeclaration pkg = c.getPackage();
    if (null != pkg) {
      File dir = new File(classpath + pkg.getPath());
      File[] files = dir.listFiles(new JavaFilter());
      for (File fi : files) {
        if (!fi.getAbsolutePath().equals(filePath)) 
          resolve(fi);
      }
    }

    // Resolve dependencies for imported classes
    List<ImportDeclaration> imp = c.getImports();
    for (ImportDeclaration i : imp) {
      File f = new File(classpath + i.getPath());
      if (f.isFile()) {
        resolve(f);   
      } else if (f.isDirectory()) {
        File[] files = f.listFiles(new JavaFilter());
        for (File fi : files) {
          if (!fi.getAbsolutePath().equals(filePath)) 
            resolve(fi);
        }
      } else {
        throw new RuntimeException("Error reading imported file: " + f.getAbsolutePath());
      }
    }

    // Set the superclass
    ClassDeclaration cd = c.getPublicClass();
    if (cd.hasExtension()) {
      String ext = cd.getExtension().getPath();
      String extpath;
      boolean found = false;
      int index = ext.lastIndexOf("/");
      // If the superclass path is given explicitly, use that path
      if (-1 < index) {
        extpath = classpath + ext;
        if (files.containsKey(extpath)) {
          cd.setParent(files.get(extpath).getPublicClass());
          found = true;
        }
      } else {
        // Check the package
        if (null != pkg) {
          extpath = classpath + pkg.getPath() + "/" + ext;
          if (files.containsKey(extpath)) {
            cd.setParent(files.get(extpath).getPublicClass());
            found = true;
          }
        }
        // Check the imported packages and classes
        if (!found) {
          for (ImportDeclaration i : imp) {
            String importpath = i.getPath();
            if (importpath.endsWith(ext)) {
              extpath = classpath + importpath;
              if (files.containsKey(extpath)) {
                cd.setParent(files.get(extpath).getPublicClass());
                found = true;
                break;
              }
            } else if (!importpath.endsWith(".java")) {
              extpath = classpath + importpath + "/" + ext;
              if (files.containsKey(extpath)) {
                cd.setParent(files.get(extpath).getPublicClass());
                found = true;
                break;
              }
            }
          }
        }
      }                                         
      if (!found)
        throw new RuntimeException("Superclass not found: " + ext);
    }
  }
  
  /**
   * First parses the specified file and creates its
   * compilation unit, then resolves dependencies.
   *
   * @param file The file to resolve.
   *
   * @throws IOException Signals an I/O error.
   * @throws ParseException Signals a parse error.
   */
  public void resolve(File file) throws IOException, ParseException {
    if (files.containsKey(file.getAbsolutePath())) 
      return;
    resolve(file, new CompilationUnit(parse(file)));
  }

}

/**
 * A filter for Java files.
 */
class JavaFilter implements FilenameFilter {
  
  /**
   * Tests if the specified file is a Java file.
   *
   * @param dir The file directory.
   * @param name The name of the file.
   *
   * @return True if it is a Java file; false otherwise.
   */
  public boolean accept(File dir, String name) {
    return name.endsWith(".java");
  }

}
