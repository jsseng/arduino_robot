package ast;

import visitor.*;

public class LED 
   extends Machinery
{
   public LED()
   {
      super("");
   }

   public String toString()
   {
      return "led";
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}

