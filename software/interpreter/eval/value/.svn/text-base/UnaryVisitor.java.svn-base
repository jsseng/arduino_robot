package eval.value;

public class UnaryVisitor
   implements ValueVisitor<UnaryEval>
{
   public UnaryEval visit(IntValue v)
   {
      return new UnaryEval("int");
   }

   public UnaryEval visit(BoolValue v)
   {
      return new UnaryEval("bool");
   }

   public UnaryEval visit(UnitValue v)
   {
      return new UnaryEval("unit");
   }
}

