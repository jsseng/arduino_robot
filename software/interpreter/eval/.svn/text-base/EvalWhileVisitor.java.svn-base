package eval;

import visitor.StatementVisitor;
import ast.*;
import eval.value.*;

public class EvalWhileVisitor
   implements ValueVisitor<State>
{
   private StatementVisitor<State> _guest;
   private Expression _guard;
   private Statement _body;
   private State _state;

   public EvalWhileVisitor(StatementVisitor<State> guest, Expression guard,
      Statement body, State state)
   {
      _guest = guest;
      _guard = guard;
      _body = body;
      _state = state;
   }

   public State visit(BoolValue v)
   {
      if (v.getValue())
      {
         _state = _body.visit(_guest);
         Value newval = _guard.visit(new EvalExpressionVisitor(_state));
         return newval.visit(this);
      }
      else
      {
         return _state;
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
      System.err.println(
         "boolean guard required for 'while' statement, found " + msg);
      System.exit(1);
   }
}
