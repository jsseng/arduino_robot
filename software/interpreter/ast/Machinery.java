package ast;

import visitor.ASTVisitor;

public abstract class Machinery implements Visitable {
   private String  _id;

   public Machinery(String id) {
      _id = id;
   }

   public abstract int getMachineNumber();

   public String getIdentifier() { 
      return "" + _id;
   }
}

