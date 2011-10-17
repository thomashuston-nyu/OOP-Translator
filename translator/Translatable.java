package translator;

import java.lang.StringBuilder;

public abstract class Translatable {
  
  public String TAB = "  ";
  
  public String getIndent(int indent) {
    StringBuilder tabs = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      tabs.append(TAB);
    }
    return tabs.toString();
  }
  
  public abstract StringBuilder translate(int indent);

}