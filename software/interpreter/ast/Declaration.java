package ast;

import visitor.ASTVisitor;

public class Declaration extends SourceElement
{
   private String _id;
   private Machinery _exp;

   public Declaration(String id, Machinery exp)
   {
      _id = id;
      _exp = exp;
   }

   public String getIdentifier()
   {
      return _id;
   }

   public Machinery getMachinery()
   {
      return _exp;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
