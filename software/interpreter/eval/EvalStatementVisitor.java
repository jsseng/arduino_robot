package eval;

import java.util.Vector;
import java.util.Iterator;
import java.util.Arrays;

import ast.*;
import visitor.StatementVisitor;
import eval.value.Value;
import eval.value.ValueVisitor;

public class EvalStatementVisitor
   extends StatementVisitor<State>
{
   private State _state;

   public EvalStatementVisitor(State state)
   {
      _state = state;
   }

   public State visit(Statement t)
   {
      return _state;
   }

   public State visit(AssignmentExpression t)
   {
      /*
      String id = t.getTarget();
      if (!_state.containsKey(id))
      {
         System.err.println("undeclared identifier '" + id + "'");
         System.exit(1);
      }
      else
      {
         _state.put(id,
            t.getSource().visit(new EvalExpressionVisitor(_state)));
      }
      return _state;
      */
      return null;
   }

   public State visit(CompoundStatement t)
   {
      State curState = _state;
      for (Statement st : t.getStatements())
      {
         curState = st.visit(this);
      }
      return curState;
   }

   public State visit(IfStatement t)
   {
      Expression guard = t.getGuard();
      Statement th = new CompoundStatement(new Vector<Statement>(Arrays.asList(t.getThen())));
      Statement el = new CompoundStatement(new Vector<Statement>(Arrays.asList(t.getElse())));

      Value v = guard.visit(new EvalExpressionVisitor(_state));

      EvalConditionalVisitor guest = new EvalConditionalVisitor(this, th, el);
      return v.visit(guest);
   }

   public State visit(ReturnStatement t)
   {
      System.err.println("return not supported");
      return _state;
   }

   public State visit(WhileStatement t)
   {
      Expression guard = t.getGuard();
      Statement body = t.getBody();

      Value v = guard.visit(new EvalExpressionVisitor(_state));

      EvalWhileVisitor guest = new EvalWhileVisitor(this, guard, body,
         _state);
      return v.visit(guest);
   }

   public State visit(DisplayStatement t)
   {
      /*
      System.out.print(
         t.getExpression().visit(new EvalExpressionVisitor(_state)) + " ");
      */
      return _state;
   }

   public State visit(WriteStatement t)
   {
      System.out.println(
         t.getExpression().visit(new EvalExpressionVisitor(_state)));
      return _state;
   }

   public State visit(ReadStatement t) {
       System.out.println(t.getExpression().visit(new EvalExpressionVisitor(_state)));
       return _state;
   }

   public State visit(ChangeStatement t) {
       System.out.println(t.getExpression().visit(new EvalExpressionVisitor(_state)));
       return _state;
   }

   public State visit(StopStatement t) {
       //System.out.println(t.getExpression().visit(new EvalStatementVisitor(_state)));
       return _state;
   }
}
