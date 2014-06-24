package visitor;

import ast.*;

public class ASTCheckerVisitor
{
   public static boolean hasReturn(Statement[] body)
   {
      for (Statement s : body)
      {
         if (s instanceof ReturnStatement)
         {
            return true;
         }
      }

      for (Statement s : body)
      {
         if (s instanceof IfStatement)
         {
            IfStatement i = (IfStatement)s;
            if (hasReturn(i.getThen()) && hasReturn(i.getElse()))
            {
               return true;
            }
         }
      }
      return false;
   }
}
