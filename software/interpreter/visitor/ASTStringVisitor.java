package visitor;

import ast.ASTRoot;

public class ASTStringVisitor
   extends ASTVisitor<String>
{
   private ASTStringBuilderVisitor v = new ASTStringBuilderVisitor();

   public String visit(ASTRoot t)
   {
      return t.visit(v).toString();
   }
}
