package ast;

import visitor.StatementVisitor;

public interface StatementVisitable
{
   <T> T visit(StatementVisitor<T> guest);
}
