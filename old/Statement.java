package pcp.translator;

import java.util.ArrayList;
import java.util.List;

public class Statement extends TranslationVisitor implements Translatable {
  
  public Statement() {
    
  }

  public String getCC(int indent, String className, List<Variable> variables) {
    return "";
  }
  
}
