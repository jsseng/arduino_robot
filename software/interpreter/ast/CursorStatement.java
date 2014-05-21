package ast;

import visitor.*;

public class CursorStatement extends Statement {
   private Position _cursor;

   public CursorStatement(int row, int column) {
      _cursor = new Position(row, column);
   }

   public Position getPosition() {
      return _cursor;
   }

   public <T> T visit(StatementVisitor<T> guest) {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest) {
      return guest.visit(this);
   }

}

