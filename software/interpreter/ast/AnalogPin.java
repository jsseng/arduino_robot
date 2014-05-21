package ast;

import visitor.*;

public class AnalogPin extends Machinery {
   int _pin;

   public AnalogPin(String id, int pin) {
      super(id);
      _pin = pin;
   }

   public int getPin() {
      return _pin;
   }

   public String toString()
   {
      return String.valueOf(_pin);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
