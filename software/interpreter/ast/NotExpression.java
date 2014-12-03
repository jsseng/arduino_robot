package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class NotExpression
   extends UnaryExpression
{
   public NotExpression(Expression oper)
   {
       super(oper, true);
   }

   public <T> T visit(ExpressionVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
