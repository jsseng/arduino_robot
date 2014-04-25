package eval.value;

public class BoolValue
   implements Value
{
   private boolean _val;

   public BoolValue(boolean v)
   {
      _val = v;
   }

   public boolean getValue()
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
