package ast;
import visitor.*;

public class Accelerometer extends Machinery implements Gettable {

   private String _accelAxis;

   public Accelerometer(String id, String axis) {
      super(id);
      _accelAxis = String.valueOf(Character.toLowerCase(axis.charAt(0)));
   }

   public int getMachineNumber()
   {
      return 0;
      //throw new UnsupportedOperationException();
   }

   public String toGetString()
   {
      return "get_accel_" + _accelAxis + "()";
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
         return other._accelAxis.equals(this._accelAxis);
      }
      return false;
   }
}

