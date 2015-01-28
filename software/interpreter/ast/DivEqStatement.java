package ast;

import visitor.StatementVisitor;
import visitor.ASTVisitor;

public class DivEqStatement extends AssignmentStatement {

   public DivEqStatement(Expression lft, Expression rht) {
      super(lft, rht);
   }

   public <T> T visit(StatementVisitor<T> guest) {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest) {
      return guest.visit(this);
   }


}
