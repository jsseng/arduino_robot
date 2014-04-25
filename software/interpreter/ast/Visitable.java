package ast;

import visitor.ASTVisitor;

public interface Visitable
{
   <T> T visit(ASTVisitor<T> guest);
}
