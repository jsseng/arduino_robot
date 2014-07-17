package ast;

import visitor.*;

public class ChangeStatement extends Statement {
   private String _mach;
   private boolean _in;

    public ChangeStatement(String mach, boolean in) {
      _mach = mach;
      _in = in;
   }

   public String getMachinery() {
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

