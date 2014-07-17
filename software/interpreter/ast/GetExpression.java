package ast;

import visitor.*;

public class GetExpression extends Expression
{
   private String _mach;

   public GetExpression(String mach)
   {
      _mach = mach;
   }

   public String getMachinery()
   {
      return _mach;
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

