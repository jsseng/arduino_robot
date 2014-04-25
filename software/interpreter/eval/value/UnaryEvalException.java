package eval.value;

public class UnaryEvalException
   extends Exception
{
   private String _found;

   public UnaryEvalException(String found)
   {
      _found = found;
   }

   public String getFound()
   {
      return _found;
   }
}
