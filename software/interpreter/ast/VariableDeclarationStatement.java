package ast;

import visitor.*;

public class VariableDeclarationStatement extends Statement implements Visitable
{
   private String _id;
   private Expression _assign;

   public VariableDeclarationStatement(String id, Expression assign)
   {
      _id = id;
      _assign = assign;
   }

   public String getId()
   {
      return _id;
   }

   public Expression getExpression()
   {
      return _assign;
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
