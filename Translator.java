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
 * @author Mike Morreale, Thomas Huston, Nabil Hassein, Susana Delgadillo, Marta Wilgan
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
        private File file;
        
        public void visitClassDeclaration(GNode n) {
          writeToFile(n); 
        }
        
        public void visitMethodDeclaration(GNode n) {
          writeToFile(n);
        }
        
        public void visit(Node n) {
          for (Object o : n) {
            if (o instanceof Node) {
              dispatch((Node)o);
            }
          }
        }
        
        
        // Method parsing methods
        
        private String[] getModifiers(Node n) {
          Node modifierArray = n.getNode(0);
          int size = modifierArray.size();
          if (size == 0)
            return null;
          String[] modifiers = new String[size];
          for (int i = 0; i < size; i++) {
            modifiers[i] = modifierArray.getNode(i).getString(0);
          }
          return modifiers;
        }
        
        private String getName(Node n) {
          return n.getString(3);
        }
        
        private String getReturnType(Node n) {
          Node returnType = n.getNode(2);
          if (returnType.getName() == "VoidType") {
            return "void";
          } else if (returnType.getName() == "PrimitiveType") {
            return returnType.getNode(0).getString(0);
          } else {
            String obj = returnType.getNode(0).getString(0);
            if (returnType.getNode(1) != null && returnType.getNode(1).getString(0) == "[") {
              obj += "[]";
            }
            return obj;
          }
        }
        
        private String getScope(String[] modifiers) {
          if (modifiers != null) {
            for (String s : modifiers) {
              if (s == "public" || s == "private" || s == "protected") return s;
            }
          }
          return "public";
        }
        
        private boolean isFinal(String[] modifiers) {
          if (modifiers != null) {
            for (String s : modifiers) {
              if (s == "final") return true;
            }
          }
          return false;
        }
        
        private boolean isStatic(String[] modifiers) {
          if (modifiers != null) {
            for (String s : modifiers) {
              if (s == "static") return true;
            }
          }
          return false;
        }
        
        
        // C++ output methods
        
        public void createFile() {
          file = new File("./Test.cc");
          try {
            if (file.exists())
              file.delete();
            file.createNewFile();
          } catch (IOException e) {}
        }
        
        public void writeToFile(GNode n) {
          if (file == null)
            createFile();
          BufferedWriter bufferedWriter = null;
          try {
            bufferedWriter = new BufferedWriter(new FileWriter("./Test.cc", true));
            String name = n.getName().toString();
            if (name.equals("ClassDeclaration")) {
              bufferedWriter.write("class " + n.getString(1) + " {\n");
              bufferedWriter.flush();
              visit(n);
              bufferedWriter.write("}\n");
            } else if (name.equals("MethodDeclaration")) {
              String[] modifiers = getModifiers(n);
              String scope = getScope(modifiers);
              boolean isStatic = isStatic(modifiers);
              boolean isFinal = isFinal(modifiers);
              String returnType = getReturnType(n);
              String method = getName(n);
              bufferedWriter.write("  " + scope + ":\n    " + (isFinal ? "const " : "") + returnType + " " + method + "() {\n");
              bufferedWriter.flush();
              visit(n);
              bufferedWriter.write("    }\n");
            }
          } catch (IOException e) {
          } finally {
            try {
              if (bufferedWriter != null) {
                bufferedWriter.flush();
                bufferedWriter.close();
              }
            } catch (IOException e) {}
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
