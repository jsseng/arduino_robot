package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class BetweenExpression
   extends WhenExpression
{
   private String _id;
   private Expression _lower;
   private Expression _higher;

   public BetweenExpression(String id, Expression lower, Expression higher)
   {
      super();
      _id = id;
      _lower = lower;
      _higher = higher;
   }

   public String getId()
   {
      return _id;
   }

   public Expression getLower()
   {
      return _lower;
   }

   public Expression getHigher()
   {
      return _higher;
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


