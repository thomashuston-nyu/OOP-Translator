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
import java.util.HashSet;
import java.util.LinkedHashMap;
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
 *
 * @version 1.1
 */
public class Translator extends Tool {
  
  // Keeps track of which files have been printed 
  private Map<JavaFile, Boolean> printed;
  private String classpath;
  private File main;


  // =========================== Constructors =======================

  /**
   * Creates a new translator.
   */
  public Translator() {
    // Make the runtime available globally
    Global.runtime = runtime;
  }


  // ============================ Get Methods =======================

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
    return "1.1";
  }


  // ====================== Initialization Methods ==================
  
  /**
   * Initializes the program and declares command line options.
   */
  public void init() {
    super.init();
    runtime.
    bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
    bool("translateJava", "translateJava", false, "Translate Java to C++.");
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
        // Instantiate the hash
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

        // Print the header files to the console
        Set<String> keys = Global.packages.keySet();
        for (String key : keys) {
          printHeader(runtime.console(), Global.packages.get(key));
          runtime.console().pln();
          printBody(runtime.console(), Global.packages.get(key));
          runtime.console().pln();
        }
        runtime.console().flush();
      } catch (IOException i) {
        runtime.errConsole().p("Error reading file: ").p(main.getPath()).pln().flush();
      } catch (ParseException p) {
        runtime.errConsole().p("Error parsing file: ").p(main.getPath()).pln().flush();
      }
    }
  }


  // ========================== Parsing Methods =====================
  
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


  // =================== Dependency Resolution Methods ==============
  
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
    if (Global.files.containsKey(file.getAbsolutePath())) 
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
    if (Global.files.containsKey(file.getAbsolutePath())) 
      return;

    // Add the file to the hash
    String filePath = file.getAbsolutePath();
    Global.files.put(filePath, c);

    // Add the file to the packages hash and
    // resolve dependencies for classes in the package
    JavaPackage pkg = c.getPackage();
    String pkgpath = pkg.getPath();
    if (!pkgpath.equals("")) {
      File dir = new File(classpath + pkgpath);
      File[] files = dir.listFiles(new JavaFilter());
      for (File fi : files) {
        if (!fi.getAbsolutePath().equals(filePath))
          resolve(fi);
      }
    }

    // Resolve dependencies for imported classes
    Set<JavaPackage> imp = c.getImports();
    for (JavaPackage i : imp) {
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
    if (cd.hasParent()) {
      String ext = cd.getExtension().getPath();
      String extpath;
      boolean found = false;
      int index = ext.lastIndexOf("/");
      // If the superclass path is given explicitly, use that path
      if (-1 < index) {
        extpath = classpath + ext;
        if (Global.files.containsKey(extpath)) {
          cd.setParent(Global.files.get(extpath).getPublicClass());
          found = true;
        }
      } else {
        // Check the package
        if (null != pkg) {
          extpath = classpath + pkg.getPath() + "/" + ext;
          if (Global.files.containsKey(extpath)) {
            cd.setParent(Global.files.get(extpath).getPublicClass());
            found = true;
          }
        }
        // Check the imports
        if (!found) {
          for (JavaPackage i : imp) {
            extpath = classpath + i.getPath();
            if (Global.files.containsKey(extpath)) {
              cd.setParent(Global.files.get(extpath).getPublicClass());
              found = true;
              break;
            }
          }
        }
      }                                         
      if (!found)
        runtime.errConsole().p("Superclass not found: ").p(ext).pln().flush();
    }

    // Initialize printed to false for this compilation unit
    printed.put(c, false); 
  }


  // ========================== Nested Classes ======================

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


  // ======================= Translation Methods ====================

  /**
   * Writes the C++ header file for the specified package
   * to the output stream.
   *
   * @param out The output stream.
   * @param pkg The package.
   */
  public void printHeader(Printer out, JavaPackage pkg) {
    // Get the files in the package
    Set<JavaFile> files = pkg.getFiles();

    // Print the name of the current header file
    runtime.console().p("### HEADER: ").p(pkg.getFilename()).pln(".h ###").pln().flush();

    // Get any imports
    Set<JavaPackage> using = new HashSet<JavaPackage>();
    for (JavaFile file : files) {
      Set<JavaPackage> imports = file.getImports();
      for (JavaPackage i : imports) {
        if (null == pkg || !i.getPath().equals(pkg.getPath()))
          using.add(i);
      }
    }

    // Include any imported headers
    out.pln("#pragma once").pln();
    out.pln("#include <iostream>");
    out.pln("#include <sstream>").pln();
    out.pln("#include \"java_lang.h\"");
    for (JavaPackage i : using) {
      out.p("#include \"").p(i.getFilename()).pln(".h\"");
    }

    // Declare namespaces being used
    out.pln().pln("using namespace java::lang;");
    for (JavaPackage i : using) {
      out.p("using namespace ").p(i.getNamespace()).pln(";");
    }
    out.pln();

    // Add the namespace
    List<String> pkgparts = pkg.getPackage();
    for (String part : pkgparts) {
      out.indent().p("namespace ").p(part).pln(" {").incr();
    }

    // Declare the class structs
    for (JavaFile file : files) {
      for (JavaClass cls : file.getClasses()) {
        String className = cls.getName();
        out.indent().p("struct __").p(className).pln(";");
        out.indent().p("struct __").p(className).pln("_VT;");
        out.pln().indent().p("typedef __").p(className).p("* ")
          .p(className).pln(";").pln();
      }
    }

    // Print header structs
    for (JavaFile file : files) {
      printHeaderVTable(out, file);
      out.pln();
    } 

    // Close the namespace
    for (int i = 0; i < pkgparts.size(); i++) {
      out.decr().indent().pln("}");
    }

    // Flush the output
    out.flush();
  }

  /**
  * Recursively writes the vtables to the output stream in order
  * based on their dependencies.
  *
  * @param c The compilation unit to print.
  */
  public void printHeaderVTable(Printer out, JavaFile c) {
    // Don't print out the structs twice
    if (printed.get(c))
      return;

    // Make sure that all dependencies have been printed first
    if (c.getPublicClass().hasParent()) {
      JavaFile d = c.getPublicClass().getParent().getFile();
    	if (null != c.getPackage() && null != d.getPackage()) {
    		if (!c.getPackage().equals(d.getPackage()))
    			return;
    		if (!printed.get(d)) {
    			printHeaderVTable(out, d);
          out.pln();
        }
    	} else if (null != c.getPackage() || null != d.getPackage()) {
    		return;
    	} else {
    		if (!printed.get(d)) {
    			printHeaderVTable(out, d);
    			out.pln();
        }
    	}
    }

    // Print the structs
    for (JavaClass cls : c.getClasses()) {
      cls.translateHeader(out);
    }

    // Mark this file as printed
    printed.put(c, true);
  }

  /**
   * Writes the body of the specified package to the output stream.
   *
   * @param out The output stream.
   * @param pkg The package.
   */
  public void printBody(Printer out, JavaPackage pkg) {
    // Get the files in the package
    Set<JavaFile> files = pkg.getFiles();

    // Print the name of the current cc file
    runtime.console().p("### BODY: ").p(pkg.getFilename()).pln(".cc ###").pln().flush();

    // Include the header file
    out.p("#include \"").p(pkg.getFilename()).pln(".h\"").pln();

    // Add the namespace
    List<String> pkgparts = pkg.getPackage();
    for (String part : pkgparts) {
      out.indent().p("namespace ").p(part).pln(" {").incr();
    }

    // Print all the files in the package
    boolean isMain = false;
    for (JavaFile f : pkg.getFiles()) {
      if (Global.files.get(classpath + main.getName()) == f)
        isMain = true;
      for (JavaClass cls : f.getClasses()) {
        cls.translate(out);
        out.pln();
      }
    }

    // Close the namespace
    for (int i = 0; i < pkgparts.size(); i++) {
      out.decr().indent().pln("}");
    }

    // If this package contains the main file, print the main method here
    if (isMain) {
      out.pln("int main(int argc, char *argv[]) {").incr();
      out.indent().pln("__rt::Array<String>* args;");
      out.indent().pln("args = new __rt::Array<String>(argc);");
      out.indent().pln("for (int i = 0; i < argc; i++) {").incr();
      out.indent().pln("(*args)[i] = __rt::literal(argv[i]);");
      out.decr().indent().pln("}");
      out.indent();
      if (!pkg.getNamespace().equals(""))
        out.p(pkg.getNamespace()).p("::");
      out.p("__").p(Global.files.get(classpath + main.getName()).getPublicClass().getName()).pln("::main(args);");
      out.decr().pln("}");
    }
  }


  // ========================== Main Method =========================

  /**
   * Runs the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    new Translator().run(args);
  }
}
