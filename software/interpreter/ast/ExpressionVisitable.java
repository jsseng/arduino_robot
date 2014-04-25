package ast;

import visitor.ExpressionVisitor;

public interface ExpressionVisitable
{
   <T> T visit(ExpressionVisitor<T> guest);
}
