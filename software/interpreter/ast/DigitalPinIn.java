package ast;

import visitor.*;

public class DigitalPinIn extends Machinery {
    int _pins[];

    public DigitalPinIn(String id, int pin0, int pinn)
    {
       super(id);
       int i = pin0;
       _pins = new int[(pinn-pin0) + 1];

       while (i <= pinn) {
          _pins[i] = i;
          i++;
       }
    }

    public int[] getPins() {
	return _pins;
    }

    public String toString()
    {
      String buf = "";
      for (int i : _pins)
      {
         buf = buf + " " + i;
      }
      return buf;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
