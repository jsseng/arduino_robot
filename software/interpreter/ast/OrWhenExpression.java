package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class OrWhenExpression
   extends BinaryWhenExpression
{
   public OrWhenExpression(WhenExpression lft, WhenExpression rht)
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
