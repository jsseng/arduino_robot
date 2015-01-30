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
      //return String.format("led1(%s)", seq);
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

