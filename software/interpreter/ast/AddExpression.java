package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class AddExpression
   extends BinaryExpression
{
   public AddExpression(Expression lft, Expression rht)
   {
      super(lft, rht);
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
