package ast;
import visitor.*;

public class Servo extends Machinery implements Settable {
   int _servoNum;

   public Servo(String id, int servoNum) {
      super(id);
      _servoNum = servoNum;
   }

   public int getMachineNumber()
   {
      return _servoNum;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toSetString(CharSequence seq)
   {
      return "set_servo(" + getIdentifier() + ", " + seq + ")";
   }
}

