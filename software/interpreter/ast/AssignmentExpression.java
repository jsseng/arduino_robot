package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class AssignmentExpression
   extends Expression
{
   private Expression _dest;
   private Expression _src;

   public AssignmentExpression(Expression dest, Expression src)
   {
      _dest = dest;
      _src = src;
   }

   public Expression getTarget()
   {
      return _dest;
   }

   public Expression getSource()
   {
      return _src;
   }

   public <T> T visit(ExpressionVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
