package ast;
import visitor.*;

public class DigitalPin extends Machinery implements Settable, Gettable {
   boolean _isOut;
   int _pin;

   public DigitalPin(String id, int pin, boolean out)
   {
      super(id);
      _isOut = out;
      _pin = pin;
   }

   public boolean getIsOutPin() {
      return _isOut;
   }

   public int getPin() {
      return _pin;
   }

   public int getMachineNumber()
   {
      return _pin;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toGetString()
   {
      return "digital(" + getIdentifier() + ")";
   }

   public String toSetString(CharSequence seq)
   {
      return "digitalWrite(" + getIdentifier() + ", " + seq + ")";
   }
}
