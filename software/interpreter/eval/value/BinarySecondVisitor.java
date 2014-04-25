package eval.value;

public class BinarySecondVisitor
   implements ValueVisitor<BinaryEval>
{
   private String _ltype;
   public BinarySecondVisitor(String ltype)
   {
      _ltype = ltype;
   }

   public BinaryEval visit(IntValue v)
   {
      return new BinaryEval(_ltype, "int");
   }

   public BinaryEval visit(BoolValue v)
   {
      return new BinaryEval(_ltype, "bool");
   }

   public BinaryEval visit(UnitValue v)
   {
      return new BinaryEval(_ltype, "unit");
   }
}

