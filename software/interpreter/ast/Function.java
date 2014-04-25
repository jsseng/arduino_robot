package ast;

import visitor.ASTVisitor;

public class Function
   implements Visitable
{
   private String _id;
   Parameters _params;
   Declarations _decls;
   Statement _body;

   public Function(String id, Parameters params, Declarations decls,
      Statement body)
   {
      _id = id;
      _params = params;
      _decls = decls;
      _body = body;
   }

   public String getName()
   {
      return _id;
   }

   public Parameters getParameters()
   {
      return _params;
   }

   public Declarations getDeclarations()
   {
      return _decls;
   }

   public Statement getBody()
   {
      return _body;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
