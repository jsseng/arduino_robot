package ast;

import visitor.ASTVisitor;
import visitor.StatementVisitor;

public class RotateStatement
   extends Statement
{
   private String _servo;
   private Expression _angle;

   public RotateStatement(String servo, Expression angle)
   {
      _servo = servo;
      _angle = angle;
   }

   public String getServo()
   {
      return _servo;
   }

   public Expression getAngle()
   {
      return _angle;
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
