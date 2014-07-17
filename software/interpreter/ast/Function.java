package ast;

import visitor.ASTVisitor;
import java.util.List;

public class Function extends SourceElement
   implements Visitable
{
   private String _id;
   List<String> _params;
   Statement[] _body;

   public Function(String id, List<String> params, Statement[] body)
   {
      super();
      _id = id;
      _params = params;
      _body = body;
   }

   public String getID()
   {
      return _id;
   }

   public List<String> getParameters()
   {
      return _params;
   }

   public Statement[] getBody()
   {
      return _body;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
