package ast;

import visitor.*;

public class Button extends Machinery implements Gettable
{
   public Button()
   {
      super("");
   }

   public String toGetString()
   {
      return "get_sw()";
   }

   public int getMachineNumber()
   {
      throw new UnsupportedOperationException();
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
      return o instanceof Button;
   }
}
