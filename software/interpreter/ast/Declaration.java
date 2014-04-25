package ast;

import visitor.ASTVisitor;

public class Declaration
   implements Visitable
{
   private String _id;

   public Declaration(String id)
   {
      _id = id;
   }

   public String getIdentifier()
   {
      return _id;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
