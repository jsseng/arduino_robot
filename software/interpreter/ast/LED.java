package ast;

import visitor.*;

public class LED extends Machinery implements Settable
{
   private int LEDnum;
   public LED(int num)
   {
      super("");
      LEDnum = num;
   }

   public String toSetString(CharSequence seq)
   {
      return String.format("led(%d, %s)", LEDnum, seq.toString());
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
      if (o instanceof LED)
      {
         LED other = (LED)o;
         return other.LEDnum == this.LEDnum;
      }
      return false;
   }
}

