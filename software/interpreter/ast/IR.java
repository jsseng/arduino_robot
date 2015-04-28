package ast;
import visitor.*;

public class IR extends Machinery implements Gettable {

   public IR() {
      super("");
   }

   public int getMachineNumber()
   {
      throw new UnsupportedOperationException();
   }

   public String toGetString()
   {
      return "ir()";
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
      return o instanceof AnalogPin;
   }
}


