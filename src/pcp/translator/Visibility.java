package pcp.translator;

import java.util.ArrayList;
import java.util.List;

public enum Visibility {

  PUBLIC ("public"),
  PRIVATE ("private"),
  PROTECTED ("protected"),
  PACKAGE_PRIVATE ("package-private");
  
  String vis;

  Visibility(String vis) {
    this.vis = vis;
  }

  public String toString() {
    return this.vis;
  }

}
