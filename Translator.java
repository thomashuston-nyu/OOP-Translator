/*
 * xtc - The eXTensible Compiler
 * Copyright (C) 2011 Robert Grimm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 * USA.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import translator.*;

import xtc.lang.JavaFiveParser;

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

/**
 * A translator from (a subset of) Java to (a subset of) C++.
 *
 * @author Mike Morreale, Thomas Huston, Nabil "Deep Class" Hassein, Marta Wilgan
 * @version $Revision$
 */
public class Translator extends xtc.util.Tool {
  
  private HashMap<String, CompilationUnit> dependencies;
  private List<CompilationUnit> order;
  private String path;
  
  /** Create a new translator. */
  public Translator(String path) {
    this.path = path;
  }
  
  public String getName() {
    return "Java to C++ Translator";
  }
  
  public String getCopy() {
    return "The Allan Gottlieb Fan Club";
  }
  
  public void init() {
    super.init();
    
    runtime.
    bool("printJavaAST", "printJavaAST", false, "Print Java AST.").
    bool("translateJava", "translateJava", false, "Translate Java to C++.");
  }
  
  /**
   * Parse a Java file and construct the AST.
   *
   * @return The CompilationUnit Node.
   */
  public Node parse(Reader in, File file) throws IOException, ParseException {
    JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (Node)parser.value(result);
  }
  
  /**
   * Write a C++ String to an output file.
   *
   * @param s The C++ code string.
   * @param extension The file extension (h or cc)
   */
  public void createFile(String s, String extension) throws IOException {
    File file = new File("../output/output." + extension);
    if (file.exists()) {
      file.delete();
    }
    file.createNewFile();
    BufferedWriter buff = new BufferedWriter(new FileWriter(file));
    buff.write(s);
    buff.flush();
    buff.close();
  }
  
  
  public String createHeader(CompilationUnit main) {
    StringBuffer s = new StringBuffer();
    s.append("#pragma once\n\n");
    s.append("#include <iostream>\n#include <sstream>\n\n");
    s.append("#include \"java_lang.h\"\n\n");
    s.append("using namespace java::lang;\n\n");
    for (CompilationUnit c : order) {
      s.append(c.getHeader(0) + "\n");
    }
    s.append(main.getHeader(0));
    return s.toString();
  }
  
  public String createCC(CompilationUnit main) {
    StringBuffer s = new StringBuffer();
    s.append("#include \"output.h\"\n\n");
    for (CompilationUnit c : order) {
      s.append(c.getCC(0, null, null) + "\n");
    }
    s.append(main.getCC(0, null, null));
    return s.toString();
  }
  
  
  public void resolveDependencies(List<Declaration> declarations) throws IOException, ParseException {
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
    for (String dir : dirs) {
      File p = new File(dir);
      if (p.isDirectory()) {
        File[] pFiles = p.listFiles();
        for (File f : pFiles) {
          if (!f.isDirectory() && !dependencies.containsKey(f.getPath())) {
            CompilationUnit c = parseFile(f.getPath());
            dependencies.put(f.getPath(), c);
            try {
              resolveDependencies(c.getDependencies());
            } catch (IOException e) {
              runtime.console().p("Error resolving dependencies. Files missing.").pln().flush();
            } catch (ParseException e) {
              runtime.console().p("Error parsing dependencies. Invalid files.").pln().flush();
            }
          }
        }
      }
    }
  }
  
  public CompilationUnit parseFile(String f) throws IOException, ParseException {
    File file = new File(path + f);
    Reader in = runtime.getReader(file);
    JavaFiveParser parser = new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return new CompilationUnit((GNode)parser.value(result));
  }
  
  public void process(Node node) {
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }
    if (runtime.test("translateJava")) {
      CompilationUnit unit = new CompilationUnit((GNode)node);
      dependencies = new HashMap<String, CompilationUnit>();
      order = new LinkedList<CompilationUnit>();
      try {
        resolveDependencies(unit.getDependencies());
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
          CompilationUnit requires = dependencies.get(parentPath);
          index = order.indexOf(requires);
          if (index < 0)
            index = 0;
        }
        order.add(index, c);
      }
      try {
        String header = createHeader(unit);
        createFile(header, "h");
        String cc = createCC(unit);
        createFile(cc, "cc");
      } catch (IOException e) {}
    }
  }
  
  /**
   * Run the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    String path;
    int index = args[1].lastIndexOf("/");
    if (index > -1)
      path = args[1].substring(0,index+1);
    else
      path = ".";
    new Translator(path).run(args);
  }
}
