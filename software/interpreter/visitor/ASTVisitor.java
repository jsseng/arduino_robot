package visitor;

import ast.*;

public abstract class ASTVisitor<T>
{
   public abstract T visit(ASTRoot t);

   /* all default to base behavior */
   public T visit(AddExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(CallExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(AndExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(AssignmentExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(BooleanConstantExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(WhenExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(BooleanWhenExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(CommaExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(ExpressionStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(SetStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Machinery t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Declarations t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(VariableDeclarationStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(DivideExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(EqualExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Function t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Functions t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(GreaterEqualExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(GreaterThanExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(IdentifierExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(AndOrExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(AndWhenExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(OrWhenExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(IfStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(IntegerConstantExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(LessEqualExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(LessThanExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(MultiplyExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(NotEqualExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(NotExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(OrExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Parameters t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Program t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(ReturnStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(GetExpression t) {
       return visit((ASTRoot)t);
   }
   public T visit(BetweenExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(StartExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(ChangesExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(SubtractExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(UnitConstantExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(StringExpression t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(FloatConstantExpression t)
   {
      return visit((ASTRoot)t);
   }
   /*
   public T visit(AnalogPin t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(DigitalPin t)
   {
      return visit((ASTRoot) t);
   }
   */
   public T visit(WhileStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Declaration t)
   {
      return visit((Declaration)t);
   }
   public T visit(DisplayStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(Statement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(BackwardStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(ForwardStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(LeftStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(RightStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(SleepStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(RepeatStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(MoveStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(OnStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(OffStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(RotateStatement t)
   {
      return visit((ASTRoot)t);
   }
   public T visit(WhenStatement t)
   {
      return visit((ASTRoot)t);
   }
}
