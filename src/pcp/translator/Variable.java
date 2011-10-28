package pcp.translator;

import java.util.ArrayList;
import java.util.List;

public class Variable {

  public final String type;
  public final String name;
  
  public Variable(String type, String name) {
    this.type = type;
    this.name = name;
  }
  
  public boolean equals(Object o) {
    if (o instanceof Variable) {
      Variable v = (Variable)o;
      if (this.type.equals(v.type) && this.name.equals(v.name))
        return true;
    }
    return false;
  }

}
