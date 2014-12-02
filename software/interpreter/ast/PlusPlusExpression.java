package ast;

import visitor.ASTVisitor;
import visitor.ExpressionVisitor;


public class PlusPlusExpression extends UnaryExpression {
    
    public PlusPlusExpression(Expression lft) {
	super(lft);
    }

    public <T> T visit(ASTVisitor<T> guest) {
	return guest.visit(this);
    }

    public <T> T visit(ExpressionVisitor<T> guest) {
	return guest.visit(this);
    }

}