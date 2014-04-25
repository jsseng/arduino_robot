package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class IntegerConstantExpression
   extends Expression
{
   private int _val;
   public IntegerConstantExpression(int val)
   {
      _val = val;
   }

   public int getValue()
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
