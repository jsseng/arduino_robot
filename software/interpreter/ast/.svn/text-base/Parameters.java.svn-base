package ast;

import java.util.Vector;
import java.util.Iterator;

import visitor.ASTVisitor;

public class Parameters
   extends Vector<Declaration>
   implements Visitable
{
   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
