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
 * @author Mike Morreale, Thomas Huston, Nabil Hassein, Marta Wilgan
 * @version $Revision$
 */
public class Translator extends xtc.util.Tool {
  /** Create a new translator. */
  public Translator() {
    // Nothing to do.
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
    JavaFiveParser parser = new   JavaFiveParser(in, file.toString(), (int)file.length());
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
  
  public void process(Node node) {
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }
    if (runtime.test("translateJava")) {
      CompilationUnit unit = new CompilationUnit((GNode)node);
      try {
        createFile(unit.getHeader(0), "h");
        createFile(unit.getCC("",0), "cc");
      } catch (IOException e) {}
    }
  }
  
  /**
   * Run the translator with the specified command line arguments.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    new Translator().run(args);
  }
}
