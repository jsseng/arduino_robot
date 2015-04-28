package visitor;

import java.util.*;
import ast.Visitable;

public class Environment
{
   private Environment next;
   private Map<String, Visitable> idToType;
   private Map<String, ArrayType> _isArray;

   public Environment()
   {
      next = null;
      idToType = new HashMap<String, Visitable>();
      _isArray = new HashMap<String, ArrayType>();
   }

   public Environment(Environment next)
   {
      this.next = next;
      idToType = new HashMap<String, Visitable>();
      _isArray = new HashMap<String, ArrayType>();
   }

   public boolean containsKey(String id)
   {
      if (idToType.containsKey(id))
         return true;
      else if (next != null)
         return next.containsKey(id);
      else
         return false;
   }

   public boolean containsValue(Visitable v)
   {
      if (idToType.containsValue(v))
         return true;
      else if (next != null)
         return next.containsValue(v);
      else
         return false;
   }

   public Visitable get(String id)
   {
      if (idToType.containsKey(id))
      {
         return idToType.get(id);
      }
      else if (next != null)
      {
         return next.get(id);
      }
      else
         return null;
   }

   public boolean isArray(String id)
   {
      if (idToType.containsKey(id))
      {
         return _isArray.get(id).isArray();
      }
      else if (next != null)
      {
         return next.isArray(id);
      }
      else
         return false;
   }

   public int getArraySize(String id)
   {
      if (idToType.containsKey(id))
      {
         return _isArray.get(id).getSize();
      }
      else if (next != null)
      {
         return next.getArraySize(id);
      }
      else
         return 0;
   }
   public boolean put(String id, Visitable v)
   {
      if (idToType.containsValue(v))
      {
         return false;
      }
      idToType.put(id, v);
      _isArray.put(id, new ArrayType(false, 0));
      return true;
   }

   public boolean put(String id, Visitable v, boolean v_isArray, int v_size)
   {
      if (idToType.containsValue(v))
      {
         return false;
      }
      idToType.put(id, v);
      _isArray.put(id, new ArrayType(v_isArray, v_size));
      return true;
   }
}
