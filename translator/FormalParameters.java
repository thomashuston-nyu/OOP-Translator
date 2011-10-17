package translator;

import xtc.tree.Node;

public class FormalParameters {
  
  private FormalParameter[] parameters;
  
  public FormalParameters(Node n) {
    if (!n.getName().equals("FormalParameters"))
      throw new RuntimeException("Invalid node type");
    if (n.size() > 0) {
      this.parameters = new FormalParameter[n.size()];
      for (int i = 0; i < this.parameters.length; i++) {
        this.parameters[i] = new FormalParameter(n.getNode(i));
      }
    } else {
      this.parameters = null;
    }
  }
  
  public String toString() {
    String params = "";
    if (parameters != null) {
      for (int i = 0; i < this.parameters.length; i++) {
        params += this.parameters[i];
        if (i < this.parameters.length - 1)
          params += ", ";
      }
    }
    return params;
  }
  
}