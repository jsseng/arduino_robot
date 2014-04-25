package eval.value;

public class BinaryFirstVisitor
   implements ValueVisitor<ValueVisitor<BinaryEval>>
{
   public ValueVisitor<BinaryEval> visit(IntValue v)
   {
      return new BinarySecondVisitor("int");
   }

   public ValueVisitor<BinaryEval> visit(BoolValue v)
   {
      return new BinarySecondVisitor("bool");
   }

   public ValueVisitor<BinaryEval> visit(UnitValue v)
   {
      return new BinarySecondVisitor("unit");
   }
}

