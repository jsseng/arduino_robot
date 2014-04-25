package ast;

import visitor.*;

public class TurnOffStatement extends Statement implements Visitable
{
   private String _id;

   public TurnOffStatement(String id)
   {
      _id = id;
   }

   public String getId()
   {
      return _id;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(StatementVisitor<T> guest)
   {
      return guest.visit(this);
   }
}

