package ast;
import visitor.*;

public class Gyroscope extends Machinery implements Gettable {

   private int _accelerometerNum;

   public Gyroscope(String id, String axis) {
      super(id);
      _accelerometerNum = Character.toUpperCase(axis.charAt(0)) - 'X';
   }

   public int getMachineNumber()
   {
      return _accelerometerNum;
   }

   public String toGetString()
   {
      return "accelerometer(" + getIdentifier() + ")";
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}

