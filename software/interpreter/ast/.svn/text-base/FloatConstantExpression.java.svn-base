package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class FloatConstantExpression
   extends Expression
{
   private float _val;
   public FloatConstantExpression(float val)
   {
      _val = val;
   }

   public float getValue()
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
