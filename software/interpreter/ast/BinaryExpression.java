package ast;

public abstract class BinaryExpression
   extends Expression
{
   private Expression _lft;
   private Expression _rht;

   public BinaryExpression(Expression lft, Expression rht)
   {
      _lft = lft;
      _rht = rht;
   }

   public Expression getLeftOperand()
   {
      return _lft;
   }

   public Expression getRightOperand()
   {
      return _rht;
   }
}
