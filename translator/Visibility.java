package translator;

public enum Visibility {

  PUBLIC ("public"),
  PRIVATE ("private"),
  PROTECTED ("protected"),
  PACKAGE_PROTECTED ("package protected");
  
  String vis;

  Visibility(String vis) {
    this.vis = vis;
  }

  public String toString() {
    return this.vis;
  }

}