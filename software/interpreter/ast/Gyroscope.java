package ast;
import visitor.*;

public class Gyroscope extends Machinery {

    private int _accelerometerNum;

    public Gyroscope(String id, String axis) {
       super(id);
       _accelerometerNum = Character.toUpperCase(axis.charAt(0)) - 'X';
    }

    public int getAxis()
    {
       return _accelerometerNum;
    }

   public <T> T visit(ASTVisitor<T> guest)
   {
      return guest.visit(this);
   }

   public String toString()
   {
      return String.valueOf(_accelerometerNum);
   }
}

