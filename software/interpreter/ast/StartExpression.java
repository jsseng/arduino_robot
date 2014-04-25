package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class StartExpression
   extends WhenExpression
{
   public StartExpression()
   {
      super();
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

