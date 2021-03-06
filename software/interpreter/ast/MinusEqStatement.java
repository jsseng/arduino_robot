package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class MinusEqStatement extends AssignmentStatement {

   public MinusEqStatement(Expression lft, Expression rht) {
      super(lft, rht);
   }

    public <T> T visit(StatementVisitor<T> guest) {
	return guest.visit(this);
    }

    public <T> T visit(ASTVisitor<T> guest) {
	return guest.visit(this);
    }
    
}
