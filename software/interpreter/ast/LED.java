package ast;

import visitor.*;

public class LED extends Machinery implements Settable
{
   public LED()
   {
      super("");
   }

   public String toSetString(CharSequence seq)
   {
      return String.format("led1(%s", seq);
   }

   public int getMachineNumber()
   {
      throw new UnsupportedOperationException();
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}

