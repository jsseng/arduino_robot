package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class SleepStatement
   extends Statement
{
   private Expression _duration;

   public SleepStatement(Expression duration)
   {
      _duration= duration;
   }

   public Expression getDuration()
   {
      return _duration;
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

