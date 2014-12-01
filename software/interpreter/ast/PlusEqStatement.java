package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class PlusEqStatement extends Statement {

    private Expression _lft;
    private Expression _rht;

    public PlusEqStatement(Expression lft, Expression rht) {
	_lft = lft;
	_rht = rht;
    }

    public Expression getVariable() {
	return _lft;
    }

    public Expression getExpression() {
	return _rht;
    }

    public <T> T visit(StatementVisitor<T> guest) {
	return guest.visit(this);
    }

    public <T> T visit(ASTVisitor<T> guest) {
	return guest.visit(this);
    }
}