package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class IdentifierExpression
   extends Expression
{
   private String _id;
   private boolean arrayAccess;
   private Expression arrayIndex;

   public IdentifierExpression(String id)
   {
      _id = id;
      arrayAccess = false;
      arrayIndex = null;
   }

   public IdentifierExpression(String id, Expression arrayIndex)
   {
      _id = id;
      arrayAccess = true;
      this.arrayIndex = arrayIndex;
   }

   public String getIdentifier()
   {
      return "" + _id;
   }

   public boolean isArrayAccess()
   {
      return arrayAccess;
   }

   public Expression getArrayIndex()
   {
      return arrayIndex;
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
