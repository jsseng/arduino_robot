package ast;

import visitor.*;

public class SetCursorStatement extends Statement {
   private Position _cursor;

   public SetCursorStatement(IntegerConstantExpression row, IntegerConstantExpression column) {
       _cursor = new Position(row.getValue(), column.getValue());
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

