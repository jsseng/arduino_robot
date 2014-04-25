package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class BooleanWhenExpression
   extends WhenExpression
{
   private Expression _exp;

   public BooleanWhenExpression(Expression e)
   {
      super();
      _exp = e;
   }

   public Expression getExp()
   {
      return _exp;
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


