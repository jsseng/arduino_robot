package ast;
import visitor.*;

public class Servo extends Machinery {
    int _servoNum;

    public Servo(String id, int servoNum) {
       super(id);
       _servoNum = servoNum;
    }

    public int getServoNum()
    {
       return _servoNum;
    }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toString()
   {
      return "set_servo(" + getMachineNumber() + ", ";
   }

   public String getMachineNumber()
   {
      return String.valueOf(_servoNum);
   }
}

