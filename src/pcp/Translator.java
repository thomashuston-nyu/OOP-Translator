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
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import pcp.translator.*;

import xtc.lang.JavaFiveParser;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * A translator from (a subset of) Java to (a subset of) C++.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * @version 1.0
 */
public class Translator extends xtc.util.Tool {
  
  private HashMap<String, CompilationUnit> dependencies;
  private HashMap<CompilationUnit, List<CompilationUnit>> requires;
  private List<CompilationUnit> order;
  private HashMap<CompilationUnit, Boolean> printed;
  private String path;
  private String output;
  
  /** Create a new translator. */
  public Translator(String path, String output) {
    this.path = path;
    this.output = output;
  }
  
  /** Get the program name. */
  public String getName() {
    return "pcp - The Producer of C++ Programs";
  }
  
  /** Get the group name. */
  public String getCopy() {
    return "(C) 2011 Nabil Hassein, Thomas Huston, Mike Morreale, Marta Wilgan";
  }
  
  /** Get the version. */
  public String getVersion() {
    return "1.0";
  }
  
  /** Initialize the program. */
  public void init() {
    super.init();
    runtime.
    bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
    bool("translateJava", "translateJava", false, "Translate Java to C++.");
  }
  
  /** Parse a Java file and construct the AST. */
  public Node parse(Reader in, File file) throws IOException, ParseException {
    JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (Node)parser.value(result);
  }
  
  /** Write a C++ String to an output file. */
  public void createFile(String s, String extension) throws IOException {
    File file = new File(output + "." + extension);
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    BufferedWriter buff = new BufferedWriter(new FileWriter(file));
    buff.write(s);
    buff.flush();
    buff.close();
  }
  
  /** Generate the C++ header file code. */
  public String createHeader(CompilationUnit main) {
    StringBuffer s = new StringBuffer();
    s.append("#pragma once\n\n");
    s.append("#include <iostream>\n#include <sstream>\n\n");
    s.append("#include \"java_lang/java_lang.h\"\n\n");
    s.append("using namespace java::lang;\n\n");
    s.append(getHeader(main));
    return s.toString();
  }
  
  /**
   * Recursive method to ensure that all classes required
   * by the compilation unit are printed before it.
   */
  public String getHeader(CompilationUnit c) {
    StringBuilder s = new StringBuilder();
    List<CompilationUnit> depends = requires.get(c);
    for (CompilationUnit u : depends) {
      if (printed.containsKey(u) && printed.get(u))
        continue;
      else
        s.append(getHeader(u));
    }
    printed.put(c, true);
    s.append(c.getHeader(0) + "\n");
    return s.toString();
  }
  
  /** Generate the C++ main file code. */
  public String createCC(CompilationUnit main) {
    StringBuffer s = new StringBuffer();
    s.append("#include \"" + output + ".h\"\n\n");
    s.append(getCC(main));
    return s.toString();
  }
  
  /**
   * Recursive method to ensure that all classes required
   * by the compilation unit are printed before it.
   */
  public String getCC(CompilationUnit c) {
    StringBuilder s = new StringBuilder();
    List<CompilationUnit> depends = requires.get(c);
    for (CompilationUnit u : depends) {
      if (printed.containsKey(u) && printed.get(u))
        continue;
      else
        s.append(getCC(u));
    }
    printed.put(c, true);
    s.append(c.getCC(0, null, null) + "\n");
    return s.toString();
  }
  
  /**
   * Find all Java files required by the source file, and
   * parse them as compilation units.
   */
  public void resolveDependencies(CompilationUnit cu, List<Declaration> declarations) 
    throws IOException, ParseException {
    List<String> dirs = new ArrayList<String>();
    for (Declaration d : declarations) {
      if (d instanceof PackageDeclaration) {
        QualifiedIdentifier pack = ((PackageDeclaration)d).getPackage();
        for (String s : pack) {
          dirs.add(path + s + "/");
        }
      } else if (d instanceof ImportDeclaration) {
        QualifiedIdentifier im = ((ImportDeclaration)d).getImport();
        String dirpath = path;
        int size = im.size();
        for (int i = 0; i < size; i++) {
          String s = im.get(i);
          if (i < size - 1)
            dirpath += s + "/";
        }
        dirs.add(dirpath);
      }
    }
    List<CompilationUnit> lcu = new ArrayList<CompilationUnit>();
    for (String dir : dirs) {
      File p = new File(dir);
      if (p.isDirectory()) {
        File[] pFiles = p.listFiles();
        for (File f : pFiles) {
          if (!f.isDirectory() && !dependencies.containsKey(f.getPath()) && f.getAbsolutePath().indexOf(".java") > -1) {
            CompilationUnit c = parseFile(f.getAbsolutePath());
            Extension ex = c.getExtension();
            String parentClass = "";
            if (ex != null)
              parentClass = ex.getType();
            if (!cu.getName().equals(parentClass))
              lcu.add(c);
            dependencies.put(f.getPath(), c);
            try {
              resolveDependencies(c, c.getDependencies());
            } catch (IOException e) {
              runtime.console().p("Error resolving dependencies. Files missing.").pln().flush();
            } catch (ParseException e) {
              runtime.console().p("Error parsing dependencies. Invalid files.").pln().flush();
            }
          }
        }
      }
    }
    requires.put(cu, lcu);
  }
  
  /** Parse a file. */
  public CompilationUnit parseFile(String f) throws IOException, ParseException {
    File file = new File(f);
    Reader in = runtime.getReader(file);
    JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return new CompilationUnit((GNode)parser.value(result));
  }
  
  /** Process the arguments passed by the user. */
  public void process(Node node) {
    // Print out the Java AST
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }
    
    // Translate the Java to C++
    if (runtime.test("translateJava")) {
      CompilationUnit unit = new CompilationUnit((GNode)node);
      dependencies = new HashMap<String, CompilationUnit>();
      requires = new HashMap<CompilationUnit, List<CompilationUnit>>();
      try {
        resolveDependencies(unit, unit.getDependencies());
      } catch (IOException e) {
        runtime.console().p("Error resolving dependencies. Files missing.").pln().flush();
      } catch (ParseException e) {
        runtime.console().p("Error parsing dependencies. Invalid files.").pln().flush();
      }
      Set<String> files = dependencies.keySet();
      for (String file : files) {
        CompilationUnit c = dependencies.get(file);
        Extension e = c.getExtension();
        int index = 0;
        if (e != null) {
          String parentPath = path + 
            c.getPackage().getPackage().getCC(0, null, null) +
            "/" + e.getType() + ".java";
          c.setParent(dependencies.get(parentPath).getPublic());
        }
      }
      try {
        printed = new HashMap<CompilationUnit, Boolean>();
        String header = createHeader(unit);
        createFile(header, "h");
        printed = new HashMap<CompilationUnit, Boolean>();
        String cc = createCC(unit);
        createFile(cc, "cc");
      } catch (IOException e) {}
    }
  }
  
  /** Run the translator with the specified command line arguments. */
  public static void main(String[] args) {
    String path, output;
    if (args.length < 2) {
      System.out.println("Error: No file or command passed");
      System.exit(1);
    }
    int index1 = args[1].lastIndexOf("/");
    int index2 = args[1].lastIndexOf(".");
    if (index1 > -1) {
      path = args[1].substring(0,index1+1);
      output = args[1].substring(index1+1,index2);
    } else {
      path = ".";
      output = args[1].substring(0,index2);
    }
    new Translator(path, output).run(args);
  }
}
