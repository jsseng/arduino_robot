package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class BooleanConstantExpression
   extends Expression
{
   private boolean _val;
   public BooleanConstantExpression(boolean val)
   {
      _val = val;
   }

   public boolean getValue()
   {
      return _val;
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
      return String.valueOf(_val);
   }
}
