package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;

public class MinusMinusExpression extends UnaryExpression {

    private Expression _lft;

    public MinusMinusExpression(Expression lft) {
	super(lft);
    }

    public <T> T visit(ASTVisitor<T> guest) {
	return guest.visit(this);
    }

    public <T> T visit(ExpressionVisitor<T> guest) {
	return guest.visit(this);
    }

}