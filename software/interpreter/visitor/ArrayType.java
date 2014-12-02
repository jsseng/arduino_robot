package visitor;

public class ArrayType
{
   private boolean _isArray;
   private int _size;

   public ArrayType(boolean isArray, int size)
   {
      _isArray = isArray;
      _size = size;
   }

   public boolean isArray()
   {
      return _isArray;
   }

   public int getSize()
   {
      return _size;
   }
}
