package ast;

import visitor.ExpressionVisitor;
import visitor.ASTVisitor;

public class DivEqExpression extends BinaryExpression {

    public DivEqExpression(Expression lft, Expression rht) {
	super(lft, rht);
    }

    public <T> T visit(ExpressionVisitor<T> guest) {
	return guest.visit(this);
    }

    public <T> T visit(ASTVisitor<T> guest) {
	return guest.visit(this);
    }


}