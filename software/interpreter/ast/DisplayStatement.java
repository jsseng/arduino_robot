package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;
import java.util.List;

public class DisplayStatement
   extends Statement
{
   private List<Expression> _args;
   
   public DisplayStatement(List<Expression> args)
   {
      _args = args;
   }

   public List<Expression> getArgs()
   {
      return _args;
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
