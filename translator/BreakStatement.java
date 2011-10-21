/**
 * Identifier?
 */

package translator;

import xtc.tree.GNode;
import xtc.tree.Node;
import xtc.tree.Visitor;

public class BreakStatement extends TranslationVisitor { 
    
    private String name;
    
    public BreakStatement(GNode n)
    {
        visit(n);
    }
    
    public void visitIdentifier(Gnode n)
    {
        name = n.getString(0);
    }
}