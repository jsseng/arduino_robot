package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class AssignmentExpression
   extends Expression
{
   private IdentifierExpression _dest;
   private Expression _src;

   public AssignmentExpression(Expression dest, Expression src)
   {
       if (!(dest instanceof IdentifierExpression)) {
	   System.out.println("Expected an identifier expression.");
	   System.exit(0);
       }
       else {
	   _dest = (IdentifierExpression)dest;
	   _src = src;
       }
   }

   public IdentifierExpression getTarget()
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
