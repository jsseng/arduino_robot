package eval.value;

public class UnitValue
   implements Value
{
   public String toString()
   {
      return "unit";
   }

   public <T> T visit(ValueVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
