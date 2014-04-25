package visitor;

import ast.*;

public abstract class ExpressionVisitor<T>
{
   public abstract T visit(Expression t);
   public abstract T visit(WhenExpression t);

   public T visit(AddExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(AndExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(BooleanConstantExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(DivideExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(EqualExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(GreaterEqualExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(GreaterThanExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(IdentifierExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(IntegerConstantExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(FloatConstantExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(InvocationExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(LessEqualExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(LessThanExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(MultiplyExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(NotEqualExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(NotExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(OrExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(SubtractExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(UnitConstantExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(StringExpression t)
   {
      return visit((Expression)t);
   }
   public T visit(BetweenExpression t)
   {
      return visit((WhenExpression)t);
   }
   public T visit(StartExpression t)
   {
      return visit((WhenExpression)t);
   }
   public T visit(ChangesExpression t)
   {
      return visit((WhenExpression)t);
   }
}
