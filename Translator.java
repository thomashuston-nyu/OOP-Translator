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

import xtc.parser.ParseException;
import xtc.parser.Result;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

import xtc.lang.JavaFiveParser;

/**
 * A translator from (a subset of) Java to (a subset of) C++.
 *
 * @author Robert Grimm
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
  
  public Node parse(Reader in, File file) throws IOException, ParseException {
    JavaFiveParser parser =
    new JavaFiveParser(in, file.toString(), (int)file.length());
    Result result = parser.pCompilationUnit(0);
    return (Node)parser.value(result);
  }
  
  public void process(Node node) {
    if (runtime.test("printJavaAST")) {
      runtime.console().format(node).pln().flush();
    }
    
    if (runtime.test("translateJava")) {
      new Visitor() {
        
        public void visitClassDeclaration(GNode n) {
          visit(n);
          File file = new File("./Test.cc");
          if (!file.exists()) {
            try {
              file.createNewFile();
            } catch (IOException e) {}
          }
          int size = n.size();
          Object o;
              BufferedWriter bufferedWriter = null;
              try {
                bufferedWriter = new BufferedWriter(new FileWriter("./Test.cc"));
                for (int i = 0; i < size; i++) {
                  o = n.get(i);
                  if (o != null) {
                    runtime.console().p(n.get(i).toString()).pln().flush();
                    bufferedWriter.write(n.get(i).toString());
                  }
                }
              } catch (IOException e) {
              } finally {
                  try {
                    if (bufferedWriter != null) {
                      bufferedWriter.flush();
                      bufferedWriter.close();
                    }
                  } catch (IOException e) {
                  }
          }
        }
        
        public void visitMethodDeclaration(GNode n) {
          visit(n);
          int size = n.size();
          Object o;
          for (int i = 0; i < size; i++) {
            o = n.get(i);
            if (o != null)
              runtime.console().p(n.get(i).toString()).pln().flush();
            
          }
        }
        
        public void visit(Node n) {
          for (Object o : n) {
            if (o instanceof Node) {
              dispatch((Node)o);
//              runtime.console().p("v:\t" + ((Node)o).getName()).pln().flush();
            }
          }
        }
        
      }.dispatch(node);
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
