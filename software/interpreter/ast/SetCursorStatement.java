package ast;

import visitor.*;

public class SetCursorStatement extends Statement {
   private Expression _row;
   private Expression _col;

   public SetCursorStatement(Expression row, Expression column) {
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

