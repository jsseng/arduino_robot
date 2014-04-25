package ast;
import visitor.*;

public class AnalogPin extends Machinery {
   boolean _isOut;
   int _pin;

   public AnalogPin(String id, int pin, boolean out) {
      super(id);
      _pin = pin;
      _isOut = out;
   }

   public boolean getIsOutPin() {
      return _isOut;
   }

   public int getPin() {
      return _pin;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toString()
   {
      String analog = "analog(" + _pin + ")";
      if (_isOut)
      {
         return "write_" + analog;
      }
      else
      {
         return analog;
      }
   }
}
