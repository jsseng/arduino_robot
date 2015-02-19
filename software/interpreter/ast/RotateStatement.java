package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;
import parser.*;

public class RotateStatement
   extends Statement
{
   private Servo _servo;
   private Expression _angle;

   public RotateStatement(Machinery servo, Expression angle)
   {
       if (! (servo instanceof Servo)) {
	   throw new ExpectedException("Machinery must be initialized to Servo to rotate");
       }
       else {
	   _servo = (Servo)servo;
	   _angle = angle;
       }
   }

   public Servo getServo()
   {
      return _servo;
   }

   public Expression getAngle()
   {
      return _angle;
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
