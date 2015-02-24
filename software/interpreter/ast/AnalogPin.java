package ast;

import visitor.*;
import parser.ExpectedException;

public class AnalogPin extends Machinery implements Gettable {
   private int _pin;
   private final static int MINIMUM_PIN = 0;
   private final static int MAXIMUM_PIN = 5;

   public AnalogPin(String id, int pin) {
      super(id);
      if (pin < MINIMUM_PIN || pin > MAXIMUM_PIN)
      {
         throw new ExpectedException(String.format("Analog pin limited between %d and %d", MINIMUM_PIN, MAXIMUM_PIN));
      }
      _pin = pin;
   }

   public int getMachineNumber() {
      return _pin;
   }

   public String toGetString()
   {
      return "analog(" + getIdentifier() + ")";
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
