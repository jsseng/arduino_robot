package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class WhileStatement
   extends Statement
{
   private Expression _guard;
   private Statement _body[];

   public WhileStatement(Expression guard, Statement[] body)
   {
      _guard = guard;
      _body = body;
   }

   public Expression getGuard()
   {
      return _guard;
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
