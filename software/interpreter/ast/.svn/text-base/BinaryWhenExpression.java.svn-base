package ast;

public abstract class BinaryWhenExpression
   extends WhenExpression
{
   private WhenExpression _lft;
   private WhenExpression _rht;

   public BinaryWhenExpression(WhenExpression lft, WhenExpression rht)
   {
      _lft = lft;
      _rht = rht;
   }

   public WhenExpression getLeftOperand()
   {
      return _lft;
   }

   public WhenExpression getRightOperand()
   {
      return _rht;
   }
}
