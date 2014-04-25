package ast;
import visitor.*;

public class DigitalPin extends Machinery {
   boolean _isOut;
   int _pins[];

   public DigitalPin(String id, int pin0, int pinn, boolean out) {
      super(id);
      int i = pin0;
      _isOut = out;
      _pins = new int[(pinn-pin0) + 1];

      while (i <= pinn) {
         _pins[i] = i;
         i++;
      }
   }

   public DigitalPin(String id, int pin, boolean out)
   {
      super(id);
      _isOut = out;
      _pins = new int[1];
      _pins[0] = pin;
   }

   public boolean getIsOutPin() {
      return _isOut;
   }

   public int[] getPins() {
      return _pins;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toString()
   {
      String digital = "digital(" + _pins[0] + ")";
      if (_isOut)
      {
         return "write_" + digital;
      }
      else
      {
         return digital;
      }
   }
}