package ast;
import visitor.*;

public class Motor extends Machinery implements Settable {
   private int _motorNum;

   public Motor(String id, int motorNum) {
      super(id);
      _motorNum = motorNum;
   }

   public int getMachineNumber()
   {
      return _motorNum;
   }

   public String toSetString(CharSequence seq)
   {
      return String.format("set_motor(%s, %s)", getIdentifier(), seq);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}


