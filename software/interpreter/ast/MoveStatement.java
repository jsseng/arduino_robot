package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public abstract class MoveStatement
   extends Statement
{

   public abstract Expression getMove();
   public <T> T visit(StatementVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public abstract <T> T visit(ASTVisitor<T> guest);
}
