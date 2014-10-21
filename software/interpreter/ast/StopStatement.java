package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;


public class StopStatement extends Statement{
    
    private boolean _endProg;
    
    public StopStatement() {
	_endProg = true;
    }

    public <T> T visit(StatementVisitor<T> guest)
    {
	return guest.visit(this);
    }

    public <T> T visit(ASTVisitor<T> guest)
    {
	return guest.visit(this);
    }


}