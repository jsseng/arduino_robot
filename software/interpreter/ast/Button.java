package ast;

import visitor.*;

public class Button 
   extends Machinery
{

   public Button()
   {
      super("");
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toString()
   {
      return "button()";
   }
}


