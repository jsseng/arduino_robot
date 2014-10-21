package visitor;

import ast.*;

public abstract class StatementVisitor<T>
{
   public abstract T visit(Statement t);

   /* all default to base behavior */
   public T visit(IfStatement t)
   {
      return visit((Statement)t);
   }
   public T visit(ReturnStatement t)
   {
      return visit((Statement)t);
   }
   public T visit(WhileStatement t)
   {
      return visit((Statement)t);
   }
   public T visit(DisplayStatement t)
   {
      return visit((Statement)t);
   }
   public T visit(SetStatement t)
   {
      return visit((Statement)t);
   }
   public T visit(StopStatement t) {
       return visit((Statement) t);
   }
}
