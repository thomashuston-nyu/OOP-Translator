package translator;

public enum Scope {

  PUBLIC ("public"),
  PRIVATE ("private"),
  PROTECTED ("protected");
  
  String scope;

  Scope(String scope) {
    this.scope = scope;
  }

  public String toString() {
    return this.scope;
  }

  // Package private???

}