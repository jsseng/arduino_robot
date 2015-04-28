package ast;

import visitor.ASTVisitor;

public class Declaration extends SourceElement
{
   private String _id;
   private Machinery _exp;
   private int _lineNum;

   public Declaration(String id, Machinery exp, int lineNum)
   {
      _id = id;
      _exp = exp;
      _lineNum = lineNum;
   }

   public String getIdentifier()
   {
      return "" + _id;
   }

   public Machinery getMachinery()
   {
      return _exp;
   }

   public int getLineNum()
   {
      return _lineNum;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
