package eval;

import java.util.Vector;
import java.util.Iterator;

import java.util.List;
import ast.*;
import visitor.ASTVisitor;
import eval.value.Value;
import eval.value.UnitValue;

public class EvalProgramVisitor
   extends ASTVisitor<State>
{
   public State visit(ASTRoot t)
   {
      return new State();
   }
   public State visit(Declarations t)
   {
      State state = new State();
      /*
      for (Declaration d : t)
      {
         String id = d.getIdentifier();
         if (state.contains(id))
         {
            error("redeclaration of '" + id + "'");
         }
         else
         {
            state.put(id, new UnitValue());
         }
      }
      */
      return state;
   }
   public State visit(Function t)
   {
      error("-- functions not supported -- ");
      return new State();
   }
   public State visit(Functions t)
   {
      error("-- functions not supported -- ");
      return new State();
   }
   public State visit(Parameters t)
   {
      error("-- functions not supported -- ");
      return new State();
   }
   public State visit(Program t)
   {
      /*
      State global_state = t.getDeclarations().visit(this);
      List<SourceElement> body = t.getBody();
      State st = null;
      for (SourceElement se : body)
      {
         if (se instanceof WhenStatement)
         {
            ((WhenStatement)se).visit(new EvalStatementVisitor(global_state));
         }
         if (se instanceof Statement)
         {
            st = ((Statement)se).visit(new EvalStatementVisitor(global_state));
         }
      }
      return st;
      */
      return null;
   }

   private void error(String msg)
   {
      System.err.println(msg);
      System.exit(1);
   }
}
