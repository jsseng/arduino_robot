package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class SpinStatement
   extends Statement
{
   private Motor _motor;
   private Expression _value;

   public SpinStatement(Motor motor, Expression value)
   {
      _motor = motor;
      _value = value;
   }

   public Motor getMotor()
   {
      return _motor;
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
