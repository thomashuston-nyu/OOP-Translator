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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.FileReader;
import java.io.FileWriter;
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
 * @version 2.0
 */
public class Translator extends Tool {
  
  // The output directory
  private static final String OUTPUT_DIR = "output/";

  // Make the runtime consoles available to all the pcp classes
  public static Printer console;
  public static Printer errConsole;

  // The path to the main file
  private String classpath;

  // The main file
  private File main;


  // =========================== Constructors =======================

  /**
   * Creates a new translator.
   */
  public Translator() {
    // Make the runtime available globally
    console = runtime.console();
    errConsole = runtime.errConsole();
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
    return "2.0";
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
        c.setMain();
        resolve(main, c);
        
        // Write the translated C++ code to files
        try {
          Set<String> keys = JavaPackage.getJavaPackageList();
          for (String key : keys) {
            JavaPackage.getJavaPackage(key).orderFiles();
            writeHeader(JavaPackage.getJavaPackage(key));
            writeBody(JavaPackage.getJavaPackage(key));
          }
        } catch (IOException i) {
          runtime.errConsole().p("Error writing file: ").pln(i.toString()).flush();
        }
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
    // If this is the first file being read, mark it as the main file
    if (main == null)
      main = file;

    // Do not allow files with $ in the name as it may conflict with our naming scheme
    if (file.getName().contains("$"))
      throw new ParseException("Filenames may not contain a $: " + file.getName());

    // Parse the file
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
    if (null != JavaFile.getJavaFile(file.getAbsolutePath())) 
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
    if (null != JavaFile.getJavaFile(file.getAbsolutePath())) 
      return;

    // Add the file to the hash
    String filePath = file.getAbsolutePath();
    JavaFile.addFile(filePath, c);

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
    for (JavaClass cd : c.getClasses()) {
      if (cd.hasParent()) {
        String extCls = cd.getExtension().getType();
        String ext = cd.getExtension().getPath();
        String extpath;
        // Check the current file
        for (JavaClass cls : cd.getFile().getClasses()) {
          if (cls.getName().equals(extCls)) {
            cd.setParent(cls);
            return;
          }
        }
        // If the superclass path is given explicitly, use that path
        int index = ext.lastIndexOf("/");
        if (-1 < index) {
          extpath = classpath + ext;
          if (null != JavaFile.getJavaFile(extpath)) {
            cd.setParent(JavaFile.getJavaFile(extpath).getPublicClass());
            return;
          }
        } else {
          // Check the package
          if (null != pkg) {
            if (pkg.getPath().equals(""))
              extpath = classpath + ext;
            else
              extpath = classpath + pkg.getPath() + "/" + ext;
            if (null != JavaFile.getJavaFile(extpath)) {
              cd.setParent(JavaFile.getJavaFile(extpath).getPublicClass());
              return;
            }
          }
          // Check the imports
          for (JavaPackage i : imp) {
            extpath = classpath + i.getPath();
            if (null != JavaFile.getJavaFile(extpath)) {
              cd.setParent(JavaFile.getJavaFile(extpath).getPublicClass());
              return;
            }
          }
        }                                         
        runtime.errConsole().p("Superclass not found: ").p(ext).pln().flush();
      }
    }
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
    * @return <code>True</code> if it is a Java file;
    * <code>false</code> otherwise.
    */
    public boolean accept(File dir, String name) {
      return name.endsWith(".java");
    }

  }


  // ======================= Translation Methods ====================

  /**
   * Creates a new file and an output stream in which
   * to write to it.
   *
   * @param name The name of the file.
   *
   * @return The output stream.
   *
   * @throws IOException Signals an I/O error.
   */
  public BufferedWriter createFile(String name) throws IOException {
    File file = new File(OUTPUT_DIR + name);
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    return new BufferedWriter(new FileWriter(file));
  }

  /**
   * Writes the C++ body for the specified package
   * to a file.
   *
   * @param pkg The package.
   *
   * @throws IOException Signals an I/O error.
   */
  public void writeBody(JavaPackage pkg) throws IOException {
    // Create the cc file
    BufferedWriter output = createFile(pkg.getFilename() + ".cc");

    // Translate the body of the package
    Printer printer = new Printer(output);
    pkg.translate(printer);

    // Save the translated code into the file
    output.flush();
    output.close();
  }

  /**
   * Writes the C++ header for the specified package
   * to a file.
   *
   * @param pkg The package.
   *
   * @throws IOException Signals an I/O error.
   */
  public void writeHeader(JavaPackage pkg) throws IOException {
    // Create the header file
    BufferedWriter output = createFile(pkg.getFilename() + ".h");

    // Translate the header of the package
    Printer printer = new Printer(output);
    pkg.translateHeader(printer);

    // Save the translated code into the file
    output.flush();
    output.close();
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
