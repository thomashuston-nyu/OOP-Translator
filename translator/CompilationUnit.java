/*
 * 0 - ?
 * 1 - ImportDeclaration
 * n - ClassDeclaration
 */
package translator;

import xtc.tree.Node;

public class CompilationUnit {
  
  private ImportDeclaration[] imports;
  private ClassDeclaration classDec;

  public CompilationUnit(Node n) {
    if (!n.getName().equals("CompilationUnit"))
      throw new RuntimeException("Invalid node type");
    int size = n.size();
    if (size == 2) {
      this.imports = null;
      this.classDec = new ClassDeclaration(n.getNode(1));
    } else {
      this.imports = new ImportDeclaration[size - 2];
      for (int i = 1; i < size - 1; i++) {
        this.imports[i-1] = new ImportDeclaration(n.getNode(i));
      }
      this.classDec = new ClassDeclaration(n.getNode(size-1));
    }
    System.out.println(this.classDec.translate(0));
  }

}