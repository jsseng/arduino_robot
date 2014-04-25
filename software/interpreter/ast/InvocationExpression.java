package ast;

import java.util.Vector;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class InvocationExpression
   extends Expression
{
   private String _id;
   private Vector<Expression> _args;

   public InvocationExpression(String id, Vector<Expression> args)
   {
      _id = id;
      _args = args;
   }

   public String getName()
   {
      return _id;
   }

   public Vector<Expression> getArguments()
   {
      return _args;
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
