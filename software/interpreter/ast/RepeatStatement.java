package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class RepeatStatement
   extends Statement
{
   private Expression _times;
   private Statement[] _body;

   public RepeatStatement(Expression times, Statement[] body)
   {
      _times = times;
      _body = body;
   }

   public Expression getTimes()
   {
      return _times;
   }

   public Statement[] getBody()
   {
      return _body;
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

