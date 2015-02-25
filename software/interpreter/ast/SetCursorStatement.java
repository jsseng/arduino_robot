package ast;

import visitor.*;
import parser.ExpectedException;

public class SetCursorStatement extends Statement {
   private Expression _row;
   private Expression _col;

   private static final int NUM_ROWS = 2;
   private static final int NUM_COLS = 8;

   public SetCursorStatement(Expression row, Expression column) {
      /*
      if (row < 0 || row >= NUM_ROWS)
      {
         throw new ExpectedException(String.format("LCD rows limited between 0 and %d", NUM_ROWS));
      }
      if (column < 0 || column >= NUM_COLS)
      {
         throw new ExpectedException(String.format("LCD columns limited between 0 and %d", NUM_COLS));
      }
      */
       _row = row;
       _col = column;
   }

   public Expression getRow() {
      return _row;
   }

   public Expression getCol() {
      return _col;
   }

   public <T> T visit(StatementVisitor<T> guest) {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest) {
      return guest.visit(this);
   }

}

