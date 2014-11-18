package ast;

import visitor.*;

public class VariableDeclarationStatement extends Statement implements Visitable
{
   private String _id;
   private Expression _assign;
   private boolean isArray;
   private int arraySize;

   public VariableDeclarationStatement(String id, Expression assign)
   {
      _id = id;
      _assign = assign;
      isArray = false;
      arraySize = 0;
   }

   public VariableDeclarationStatement(String id, Expression assign, int arraySize)
   {
      _id = id;
      _assign = assign;
      isArray = true;
      this.arraySize = arraySize;
   }

   public String getId()
   {
      return _id;
   }

   public boolean getIsArray()
   {
      return isArray;
   }

   public int getArraySize()
   {
      return arraySize;
   }

   public Expression getExpression()
   {
      return _assign;
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(StatementVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
