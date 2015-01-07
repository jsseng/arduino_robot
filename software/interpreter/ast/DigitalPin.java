package ast;
import visitor.*;

public class DigitalPin extends Machinery implements Settable, Gettable {
   boolean _isOut;
   int _pins[];

   public DigitalPin(String id, int pin0, int pinn, boolean out) {
      super(id);
      int i = pin0;
      _isOut = out;
      // _pins = new int[(pinn-pin0) + 1];
      _pins = new int[pinn + 1];

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

   public int getMachineNumber()
   {
      throw new UnsupportedOperationException();
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toGetString()
   {
      return "digital(" + _pins[0] + ")";
   }

   public String toSetString(CharSequence seq)
   {
      return "UNSUPPORTED TOSETSTRING";
   }
}
