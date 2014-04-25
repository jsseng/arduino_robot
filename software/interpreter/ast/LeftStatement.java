package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class LeftStatement
   extends Statement
{

   public LeftStatement()
   {
   }

   public <T> T visit(StatementVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}


