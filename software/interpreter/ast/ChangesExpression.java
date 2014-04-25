package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class ChangesExpression
   extends WhenExpression
{
   private String _id;

   public ChangesExpression(String id)
   {
      super();
      _id = id;
   }

   public String getId()
   {
      return _id;
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

