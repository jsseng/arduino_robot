package ast;

public abstract class UnaryExpression
   extends Expression
{
   private Expression _oper;

   public UnaryExpression(Expression oper)
   {
      _oper = oper;
   }

   public Expression getOperand()
   {
      return _oper;
   }
}
