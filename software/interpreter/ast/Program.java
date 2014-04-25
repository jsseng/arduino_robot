package ast;

import java.util.List;
import visitor.ASTVisitor;

public class Program
   implements ASTRoot, Visitable
{
   private List<Machinery>_decls;
   private List<SourceElement> _body;

   public Program(List<Machinery> decls, List<SourceElement> body)
   {
      _decls = decls;
      _body = body;
   }

   public List<Machinery> getDeclarations()
   {
      return _decls;
   }

   public List<SourceElement> getBody()
   {
      return _body;
   }

   public Functions getFunctions()
   {
      return null;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
