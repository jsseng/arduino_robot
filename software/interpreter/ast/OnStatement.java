package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class OnStatement
   extends Statement
{
   private String _device;

   public OnStatement(String device)
   {
      _device = device;
   }

   public String getDevice()
   {
      return _device;
   }

   public <T> T visit(StatementVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}

