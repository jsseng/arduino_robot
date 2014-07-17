package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;
import java.util.List;

public class CallExpression
   extends Expression
{
   String _id;
   List<Expression> _params;

   public CallExpression(String id, List<Expression> params)
   {
      _id = id;
      _params = params;
   }

   public String getID()
   {
      return _id;
   }

   public List<Expression> getParams()
   {
      return _params;
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

