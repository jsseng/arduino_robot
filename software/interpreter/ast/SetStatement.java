package ast;

import visitor.*;

public class SetStatement extends Statement
{
   private Machinery _mach;
   private Expression _value;

   public SetStatement(Machinery mach, Expression value)
   {
      _mach = mach;
      _value = value;
   }

   public Machinery getMachinery()
   {
      return _mach;
   }

   public Expression getValue()
   {
      return _value;
   }

   public <T> T visit(StatementVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

}
