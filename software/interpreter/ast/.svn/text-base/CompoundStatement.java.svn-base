package ast;

import java.util.Vector;
import java.util.Iterator;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class CompoundStatement
   extends Statement
{
   private Vector<Statement> _stmts;
   public CompoundStatement(Vector<Statement> stmts)
   {
      _stmts = stmts;
   }

   public Vector<Statement> getStatements()
   {
      return _stmts;
   }

   public <T> T visit(StatementVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
