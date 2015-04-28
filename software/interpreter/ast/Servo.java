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

   public boolean equals(Object o)
   {
      if (o == null)
      {
         return false;
      }
      if (o instanceof Servo)
      {
         Servo other = (Servo)o;
         return other._servoNum == this._servoNum;
      }
      return false;
   }
}

