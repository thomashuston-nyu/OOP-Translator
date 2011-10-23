package translator;

import java.util.ArrayList;
import java.util.List;

public interface Translatable {
  
  public String getCC(int indent, String className, List<Variable> variables);

}