package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class WhenStatement
   extends Statement
{
   private WhenExpression _guard;
   private Statement[] _body;

   public WhenStatement(WhenExpression guard, Statement[] body)
   {
      _guard = guard;
      _body = body;
   }

   public WhenExpression getGuard()
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

