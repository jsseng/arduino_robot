package eval.value;

public interface ValueVisitor<T>
{
   T visit(BoolValue v);
   T visit(IntValue v);
   T visit(UnitValue v);
}
