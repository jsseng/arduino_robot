package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class BackwardStatement
   extends Statement
{
   private Expression _move;

   public BackwardStatement(Expression move)
   {
      _move = move;
   }

   public Expression getMove()
   {
      return _move;
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

