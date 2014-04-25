package eval.value;

import eval.funcs.UnaryBoolToBool;
import eval.funcs.BinaryBoolBoolToBool;
import eval.funcs.BinaryIntIntToBool;
import eval.funcs.BinaryIntIntToInt;

public class FuncFactory
{
   public static BinaryFirstVisitor create(final BinaryBoolBoolToBool func)
   {
      return new BinaryFirstVisitor()
      {
         public BinarySecondVisitor visit(final BoolValue lft)
         {
            return new BinarySecondVisitor("bool")
            {
               public BinaryEval visit(final BoolValue rht)
               {
                  return new BinaryEval("bool", "bool")
                  {
                     public Value eval()
                     {
                        return new BoolValue(
                           func.eval(lft.getValue(), rht.getValue()));
                     }
                  };
               }
            };
         }
      };
   }

   public static BinaryFirstVisitor create(final BinaryIntIntToBool func)
   {
      return new BinaryFirstVisitor()
      {
         public BinarySecondVisitor visit(final IntValue lft)
         {
            return new BinarySecondVisitor("int")
            {
               public BinaryEval visit(final IntValue rht)
               {
                  return new BinaryEval("int", "int")
                  {
                     public Value eval()
                     {
                        return
                           new BoolValue(
                              func.eval(lft.getValue(), rht.getValue()));
                     }
                  };
               }
            };
         }
      };
   }

   public static BinaryFirstVisitor create(final BinaryIntIntToInt func)
   {
      return new BinaryFirstVisitor()
      {
         public BinarySecondVisitor visit(final IntValue lft)
         {
            return new BinarySecondVisitor("int")
            {
               public BinaryEval visit(final IntValue rht)
               {
                  return new BinaryEval("int", "int")
                  {
                     public Value eval()
                     {
                        return new IntValue(
                           func.eval(lft.getValue(), rht.getValue()));
                     }
                  };
               }
            };
         }
      };
   }

   public static UnaryVisitor create(final UnaryBoolToBool func)
   {
      return new UnaryVisitor()
      {
         public UnaryEval visit(final BoolValue opnd)
         {
            return new UnaryEval("bool") {
               public Value eval()
               {
                  return new BoolValue(func.eval(opnd.getValue()));
               }
            };
         }
      };
   }
}
