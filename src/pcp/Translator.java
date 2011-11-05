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
package pcp;

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

import pcp.translator.*;

import xtc.lang.JavaFiveParser;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Printer;
import xtc.tree.Visitor;

import xtc.util.Tool;

/**
 * A translator from (a subset of) Java to (a subset of) C++.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class Translator extends Tool {
  
  private Map<String, JavaFile> files;
  private Map<JavaFile, JavaFile> requires;
  private Map<JavaFile, Boolean> printed;
  private String classpath;
  private File main;

  /** 
   * Creates a new translator.
   */
  public Translator() {
    // Nothing to do
  }

  /**
   * Gets the program name.
   *
   * @return The name.
   */  
  public String getName() {
    return "pcp - The Producer of C++ Programs";
  }
  
  /**
   * Gets the group copyright. 
   *
   * @return The copyright.
   */
  public String getCopy() {
    return "Copyright (C) 2011 Nabil Hassein, Thomas Huston, Mike Morreale, Marta Wilgan";
  }
  
  /**
   * Gets the version.
   * 
   * @return The version.
   */
  public String getVersion() {
    return "1.0";
  }
  
  /**
   * Initializes the program. Declares command line options.
   */
  public void init() {
    super.init();
    runtime.
    bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
    bool("translateJava", "translateJava", false, "Translate Java to C++.");
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
  public Node parse(File file) throws IOException, ParseException {
    Reader in = new FileReader(file);
    return parse(in, file);
  }

  /**
   * Parses the specified file.
   *
   * @param in The input stream for the file.
   * @param file The corresponding file.
   *
   * @return The AST corresponding to the file's contents, 
   * or null if no tree has been generated.
   *
   * @throws IOException Signals an I/O error.
   * @throws ParseException Signals a parse error.
   */
  public Node parse(Reader in, File file) throws IOException, ParseException {
    if (main == null)
      main = file;
    JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (Node)parser.value(result);
  }

  /**
   * Constructs the C++ header file.
   *
   * @param out The output stream.
   */
  public void printHeader(Printer out) {
    // Print includes
    out.pln("#pragma once").pln();
    out.pln("#include <iostream>");
    out.pln("#include <sstream>").pln();
    out.pln("#include \"java/lang/java_lang.h\"").pln();
    out.pln("using namespace java::lang;").pln();

    // Declare classes
    Set<JavaFile> keys = requires.keySet();
    for (JavaFile key : keys) {
      String className = key.getPublicClass().getName();
      List<String> pack = null;
      if (null != key.getPackage())
        pack = key.getPackage().getPackage();
      if (null != pack) {
        for (String p : pack) {
          out.indent().p("namespace ").p(p).pln(" {").incr();
        }
      }
      out.indent().p("struct __").p(className).pln(";");
      out.indent().p("struct __").p(className).pln("_VT;").pln();
      out.indent().p("typedef __").p(className).p("* ").p(className).pln(";");
      if (null != pack) {
        for (int i = 0; i < pack.size(); i++) {
          out.decr().indent().pln("}");
        }
      }
      out.pln();
    }

    // Print header structs
    for (JavaFile key : keys) {
      printHeaderVTable(out, key);
    }

    // Flush the output
    out.flush();
  }

  /**
  * Recursively prints the VTables in order based
  * on their dependencies.
  *
  * @param c The compilation unit to print.
  */
  public void printHeaderVTable(Printer out, JavaFile c) {
    // Don't print out the structs twice
    if (printed.get(c))
      return;

    // Make sure that all dependencies have been printed first
    JavaFile d = requires.get(c);
    if (null != d && !printed.get(d))
      printHeaderVTable(out, d);

    // Print the namespace
    List<String> pack = null;
    if (null != c.getPackage())
      pack = c.getPackage().getPackage();
    if (null != pack) {
      for (String p : pack) {
        out.indent().p("namespace ").p(p).pln(" {").incr();
      }
    }
    
    // Print the structs
    String className = c.getPublicClass().getName();

    // Class struct
    out.indent().p("struct __").p(className).pln(" {").incr();
    out.indent().p("__").p(className).pln("_VT* __vptr;").pln();
    out.indent().p("__").p(className).pln("();").pln();
    out.indent().pln("static Class __class();").pln();
    out.indent().p("static __").p(className).pln("_VT __vtable;").decr();
    out.indent().pln("};").pln();
    
    // VTable struct
    out.indent().p("struct __").p(className).pln("_VT {").incr();
    out.indent().pln("Class __isa;").pln();
    out.indent().p("__").p(className).pln("_VT()");
    out.indent().p(": __isa(__").p(className).pln("::__class())").decr();
    out.indent().pln("};");

    // Close the namespace
    if (null != pack) {
      for (int i = 0; i < pack.size(); i++) {
        out.decr().indent().pln("}");
      }
    }

    out.pln();

    // Mark this compilation unit as printed
    printed.put(c, true);
  }
  
  /**
   * Processes the specified AST node.
   *
   * @param node The node.
   */
  public void process(Node node) {
    // Prints out the Java AST
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }
    // Translates Java to C++
    if (runtime.test("translateJava")) {
      try {
        // Instantiate the hashes
        files = new HashMap<String, JavaFile>();
        requires = new HashMap<JavaFile, JavaFile>();
        printed = new HashMap<JavaFile, Boolean>();

        // Find the classpath for the program
        JavaFile c = new JavaFile((GNode)node);
        JavaPackage pkg = c.getPackage();
        String absPath = main.getAbsolutePath();
        absPath = absPath.substring(0, absPath.lastIndexOf("/")+1);
        if (null != pkg) {
          String pkgPath = pkg.getPath();
          int index = absPath.lastIndexOf(pkgPath);
          if (0 > index)
            runtime.errConsole().p("Package name does not match directory: ").p(pkg.getPath())
              .pln().flush();
          classpath = absPath.substring(0, index);
        } else {
          classpath = absPath; 
        }

        // Resolve dependencies
        resolve(main, c);

        // Print out the classes in order of dependence
        // runtime.console().pln("##### HEADER #####").pln().flush();
        // printHeader(runtime.console());
        c.translate(runtime.console());
        runtime.console().flush();
      } catch (IOException i) {
        runtime.errConsole().p("Error reading file: ").p(main.getPath()).pln().flush();
      } catch (ParseException p) {
        runtime.errConsole().p("Error parsing file: ").p(main.getPath()).pln().flush();
      }
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
    resolve(file, new JavaFile((GNode)parse(file)));
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
  public void resolve(File file, JavaFile c) throws IOException, ParseException {
    if (files.containsKey(file.getAbsolutePath())) 
      return;

    // Add the file to the hash
    String filePath = file.getAbsolutePath();
    files.put(filePath, c);

    // Resolve dependencies for classes in the package
    JavaPackage pkg = c.getPackage();
    if (null != pkg) {
      File dir = new File(classpath + pkg.getPath());
      File[] files = dir.listFiles(new JavaFilter());
      for (File fi : files) {
        if (!fi.getAbsolutePath().equals(filePath)) 
          resolve(fi);
      }
    }

    // Resolve dependencies for imported classes
    List<JavaImport> imp = c.getImports();
    for (JavaImport i : imp) {
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
        runtime.errConsole().p("Error reading imported file: ").p(f.getAbsolutePath()).pln().flush();
      }
    }

    // Set the superclass
    JavaClass cd = c.getPublicClass();
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
          requires.put(c, files.get(extpath));
          found = true;
        }
      } else {
        // Check the package
        if (null != pkg) {
          extpath = classpath + pkg.getPath() + "/" + ext;
          if (files.containsKey(extpath)) {
            cd.setParent(files.get(extpath).getPublicClass());
            requires.put(c, files.get(extpath));
            found = true;
          }
        }
        // Check the imported packages and classes
        if (!found) {
          for (JavaImport i : imp) {
            String importpath = i.getPath();
            if (importpath.endsWith(ext)) {
              extpath = classpath + importpath;
              if (files.containsKey(extpath)) {
                cd.setParent(files.get(extpath).getPublicClass());
                requires.put(c, files.get(extpath));
                found = true;
                break;
              }
            } else if (!importpath.endsWith(".java")) {
              extpath = classpath + importpath + "/" + ext;
              if (files.containsKey(extpath)) {
                cd.setParent(files.get(extpath).getPublicClass());
                requires.put(c, files.get(extpath));
                found = true;
                break;
              }
            }
          }
        }
      }                                         
      if (!found)
        runtime.errConsole().p("Superclass not found: ").p(ext).pln().flush();
    } else {
      requires.put(c, null);
    }

    // Initialize printed to false for this compilation unit
    printed.put(c, false); 
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

  /**
   * Runs the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    new Translator().run(args);
  }
}
