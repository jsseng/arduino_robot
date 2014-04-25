package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class IfStatement
   extends Statement
{
   private Expression _guard;
   private Statement[] _then;
   private Statement[] _else;

   public IfStatement(Expression guard, Statement[] then, Statement[] else_)
   {
      _guard = guard;
      _then = then;
      _else = else_;
   }

   public Expression getGuard()
   {
      return _guard;
   }

   public Statement[] getThen()
   {
      return _then;
   }

   public Statement[] getElse()
   {
      return _else;
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
