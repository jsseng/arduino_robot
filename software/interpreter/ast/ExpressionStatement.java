package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class ExpressionStatement
   extends Statement
{
   private Expression _exp;

   public ExpressionStatement(Expression exp)
   {
      _exp = exp;
   }

   public Expression getExp()
   {
      return _exp;
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
