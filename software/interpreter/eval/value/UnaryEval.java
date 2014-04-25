package eval.value;

import eval.funcs.UnaryBoolToBool;

public class UnaryEval
{
   private String _found;
   protected UnaryEval(String type)
   {
      _found = type;
   }

   public Value eval()
      throws UnaryEvalException
   {
      throw new UnaryEvalException(_found);
   }
}
