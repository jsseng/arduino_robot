package ast;

import visitor.*;
import java.util.List;

public class StringExpression
   extends Expression
{
   private String _str;

   public StringExpression(String str)
   {
      _str = str;
   }

   public String getString()
   {
      return _str;
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

