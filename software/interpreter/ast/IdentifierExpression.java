package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class IdentifierExpression
   extends Expression
{
   private String _id;

   public IdentifierExpression(String id)
   {
      _id = id;
   }

   public String getIdentifier()
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

   public String toRealString()
   {
      return _id;
   }
}
