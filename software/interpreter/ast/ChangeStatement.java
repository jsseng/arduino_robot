package ast;

import visitor.*;

public class ChangeStatement extends Statement {
   private Machinery _mach;
   private boolean _in;

    public ChangeStatement(Machinery mach, boolean in) {
      _mach = mach;
      _in = in;
   }

   public Machinery getMachinery() {
      return _mach;
   }
    
   public boolean inPin() {
       return _in;
   }

   public <T> T visit(StatementVisitor<T> guest) {
      return guest.visit(this);
   }

   public <T> T visit(ASTVisitor<T> guest) {
      return guest.visit(this);
   }

}

