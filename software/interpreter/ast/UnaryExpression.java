package ast;

public abstract class UnaryExpression
   extends Expression
{
   private Expression _oper;
    private boolean _pre;

    public UnaryExpression(Expression oper, boolean preOperation)
   {
      _oper = oper;
      _pre = preOperation;
   }

    public boolean isPre() {
	return _pre;
    }

   public Expression getOperand()
   {
      return _oper;
   }
}
