package ast;

import visitor.*;

public class CursorStatement extends Statement {
   private int _row;
   private int _col;

   public CursorStatement(int row, int column) {
      _row = row;
      _col = column;
   }

   public int getRow()
   {
      return _row;
   }

   public int getCol()
   {
      return _col;
   }

   public <T> T visit(StatementVisitor<T> guest) {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest) {
      return guest.visit(this);
   }

}

