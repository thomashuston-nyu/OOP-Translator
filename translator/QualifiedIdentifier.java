package translator;

import xtc.tree.Node;

public class QualifiedIdentifier {
  
  private String[] identifier;
  
  public QualifiedIdentifier(Node n) {
    if (!n.getName().equals("QualifiedIdentifier"))
      throw new RuntimeException("Invalid node type");
    this.identifier = new String[n.size()];
    for (int i = 0; i < n.size(); i++) {
      this.identifier[i] = n.getString(i);
    }
  }
  
  public String toString() {
    String identifiers = "";
    for (int i = 0; i < identifier.length; i++) {
      if (i > 0)
        identifiers += ".";
      identifiers += identifier[i];
    }
    return identifiers;
  }
  
}