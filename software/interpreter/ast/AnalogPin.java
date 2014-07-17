package ast;

import visitor.*;

public class AnalogPin extends Machinery implements Gettable {
   private int _pin;

   public AnalogPin(String id, int pin) {
      super(id);
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
