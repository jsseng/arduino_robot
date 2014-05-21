package ast;
import visitor.*;

public class Motor extends Machinery {
    int _motorNum;

    public Motor(String id, int motorNum) {
       super(id);
       _motorNum = motorNum;
    }

    public int getMotorNum()
    {
       return _motorNum;
    }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toString()
   {
      return String.valueOf(_motorNum);
   }
}


