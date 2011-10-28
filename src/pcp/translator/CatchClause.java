/**
 * FormalParameter Block
 */
package pcp.translator;

import java.util.ArrayList;
import java.util.List;

import xtc.tree.GNode;
import xtc.tree.Visitor;

public class CatchClause extends TranslationVisitor {
  private FormalParameter formalParameter;
  private Block block;

  public CatchClause(GNode n) {
    visit(n);
  }

  public void visitFormalParameter(GNode n) {
    formalParameter = new FormalParameter(n);
  }

  public void visitBlock(GNode n) {
    block = new Block(n);
  }
}
