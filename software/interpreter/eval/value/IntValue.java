package eval.value;

public class IntValue
   implements Value
{
   private int _val;

   public IntValue(int v)
   {
      _val = v;
   }

   public int getValue()
   {
      return _val;
   }

   public String toString()
   {
      return String.valueOf(_val);
   }

   public <T> T visit(ValueVisitor<T> guest)
   {
      return guest.visit(this);
   }
}
