package ast;

import visitor.*;

public class ReadStatement extends Expression
{
   private Machinery _mach;

   public ReadStatement(Machinery mach)
   {
      _mach = mach;
   }

   public Machinery getMachinery()
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

