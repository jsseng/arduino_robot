package ast;
import visitor.*;

public class Accelerometer extends Machinery implements Gettable {

   private int _accelerometerNum;

   public Accelerometer(String id, String axis) {
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

   public boolean equals(Object o)
   {
      if (o == null)
      {
         return false;
      }
      if (o instanceof Accelerometer)
      {
         Accelerometer other = (Accelerometer)o;
         return other._accelerometerNum == this._accelerometerNum;
      }
      return false;
   }
}

