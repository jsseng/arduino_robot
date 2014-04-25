package eval;

import java.util.Vector;
import java.util.Iterator;

import ast.*;
import visitor.ExpressionVisitor;
import eval.value.*;
import eval.funcs.*;

public class EvalExpressionVisitor
   extends ExpressionVisitor<Value>
{
   private State _state;
   public EvalExpressionVisitor(State state)
   {
      _state = state;
   }

   public Value visit(WhenExpression t)
   {
      throw new UnsupportedOperationException();
   }
   public Value visit(Expression t)
   {
      return new UnitValue();
   }
   public Value visit(AddExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_plusf));
      }
      catch (BinaryEvalException e)
      {
         opError("+", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(AndExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_andf));
      }
      catch (BinaryEvalException e)
      {
         opError("&", "bool * bool", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(BooleanConstantExpression t)
   {
      return new BoolValue(t.getValue());
   }
   public Value visit(DivideExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_dividef));
      }
      catch (BinaryEvalException e)
      {
         opError("/", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(EqualExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_eqf));
      }
      catch (BinaryEvalException e)
      {
         opError("=", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(GreaterEqualExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_gef));
      }
      catch (BinaryEvalException e)
      {
         opError(">=", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(GreaterThanExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_gtf));
      }
      catch (BinaryEvalException e)
      {
         opError(">", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(IdentifierExpression t)
   {
      String id = t.getIdentifier();
      if (!_state.containsKey(id))
      {
         System.err.println("use of undeclared identifier '" + id + "'");
         System.exit(1);
         return new UnitValue();
      }
      else
      {
         return _state.get(id);
      }
   }
   public Value visit(IntegerConstantExpression t)
   {
      return new IntValue(t.getValue());
   }
   public Value visit(InvocationExpression t)
   {
      System.err.println("invocation not supported");
      return new UnitValue();
   }
   public Value visit(LessEqualExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_lef));
      }
      catch (BinaryEvalException e)
      {
         opError("<=", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(LessThanExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_ltf));
      }
      catch (BinaryEvalException e)
      {
         opError("<", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(MultiplyExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_multf));
      }
      catch (BinaryEvalException e)
      {
         opError("*", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(NotEqualExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_nef));
      }
      catch (BinaryEvalException e)
      {
         opError("!=", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(NotExpression t)
   {
      try
      {
         return evaluateUnary(t, FuncFactory.create(_notf));
      }
      catch (UnaryEvalException e)
      {
         error("boolean operand required for operator '!', found " +
            e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(OrExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_orf));
      }
      catch (BinaryEvalException e)
      {
         opError("|", "bool * bool", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(SubtractExpression t)
   {
      try
      {
         return evaluateBinary(t, FuncFactory.create(_minusf));
      }
      catch (BinaryEvalException e)
      {
         opError("-", "int * int", e.getFound());
         return new UnitValue();
      }
   }
   public Value visit(UnitConstantExpression t)
   {
      return new UnitValue();
   }

   private Value evaluateBinary(BinaryExpression e, BinaryFirstVisitor guest)
      throws BinaryEvalException
   {
      Value lft = e.getLeftOperand().visit(this);
      Value rht = e.getRightOperand().visit(this);
      ValueVisitor<BinaryEval> v = lft.visit(guest);
      return rht.visit(v).eval();
   }

   private Value evaluateUnary(UnaryExpression e, UnaryVisitor guest)
      throws UnaryEvalException
   {
      Value opnd = e.getOperand().visit(this);
      return opnd.visit(guest).eval();
   }

   private static final BinaryBoolBoolToBool _andf =
      new BinaryBoolBoolToBool() {
            public boolean eval(boolean lft, boolean rht)
            { return lft && rht; }
      };
   private static final BinaryBoolBoolToBool _orf =
      new BinaryBoolBoolToBool() {
            public boolean eval(boolean lft, boolean rht)
            { return lft || rht; }
      };
   private static final BinaryIntIntToInt _dividef =
      new BinaryIntIntToInt() {
            public int eval(int lft, int rht)
            { return lft / rht; }
      };
   private static final BinaryIntIntToInt _multf =
      new BinaryIntIntToInt() {
            public int eval(int lft, int rht)
            { return lft * rht; }
      };
   private static final BinaryIntIntToInt _plusf =
      new BinaryIntIntToInt() {
            public int eval(int lft, int rht)
            { return lft + rht; }
      };
   private static final BinaryIntIntToInt _minusf =
      new BinaryIntIntToInt() {
            public int eval(int lft, int rht)
            { return lft - rht; }
      };

   private static final BinaryIntIntToBool _eqf =
      new BinaryIntIntToBool() {
            public boolean eval(int lft, int rht)
            { return lft == rht; }
      };
   private static final BinaryIntIntToBool _gef =
      new BinaryIntIntToBool() {
            public boolean eval(int lft, int rht)
            { return lft >= rht; }
      };
   private static final BinaryIntIntToBool _gtf =
      new BinaryIntIntToBool() {
            public boolean eval(int lft, int rht)
            { return lft > rht; }
      };
   private static final BinaryIntIntToBool _lef =
      new BinaryIntIntToBool() {
            public boolean eval(int lft, int rht)
            { return lft <= rht; }
      };
   private static final BinaryIntIntToBool _ltf =
      new BinaryIntIntToBool() {
            public boolean eval(int lft, int rht)
            { return lft < rht; }
      };
   private static final BinaryIntIntToBool _nef =
      new BinaryIntIntToBool() {
            public boolean eval(int lft, int rht)
            { return lft != rht; }
      };

   private static final UnaryBoolToBool _notf =
      new UnaryBoolToBool() {
            public boolean eval(boolean opnd)
            { return !opnd; }
      };

   private static void opError(String op, String required, String found)
   {
      error("operator '" + op + "' requires " + required + ", found " + found);
      System.exit(1);
   }

   private static void error(String msg)
   {
      System.err.println(msg);
      System.exit(1);
   }
}
