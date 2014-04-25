package eval;

import visitor.StatementVisitor;
import ast.*;
import eval.value.*;

public class EvalConditionalVisitor
   implements ValueVisitor<State>
{
   private StatementVisitor<State> _guest;
   private Statement _th;
   private Statement _el;

   public EvalConditionalVisitor(StatementVisitor<State> guest,
      Statement th, Statement el)
   {
      _guest = guest;
      _th = th;
      _el = el;
   }

   public State visit(BoolValue v)
   {
      if (v.getValue())
      {
         return _th.visit(_guest);
      }
      else
      {
         return _el.visit(_guest);
      }
   }
   public State visit(IntValue v)
   {
      error("int");
      return new State();
   }
   public State visit(UnitValue v)
   {
      error("unit");
      return new State();
   }

   private void error(String msg)
   {
      System.err.println("boolean guard required for 'if' statement, found " +
         msg);
      System.exit(1);
   }
}
