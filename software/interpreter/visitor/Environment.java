package visitor;

import java.util.*;
import ast.Visitable;

public class Environment
{
   private Environment next;
   private Map<String, Visitable> idToType;
   private Map<String, Boolean> _isArray;

   public Environment()
   {
      next = null;
      idToType = new HashMap<String, Visitable>();
      _isArray = new HashMap<String, Boolean>();
   }

   public Environment(Environment next)
   {
      this.next = next;
      idToType = new HashMap<String, Visitable>();
      _isArray = new HashMap<String, Boolean>();
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
         return _isArray.get(id);
      }
      else if (next != null)
      {
         return next.isArray(id);
      }
      else
         return false;
   }

   public void put(String id, Visitable v)
   {
      idToType.put(id, v);
      _isArray.put(id, false);
   }

   public void put(String id, Visitable v, boolean v_isArray)
   {
      idToType.put(id, v);
      _isArray.put(id, v_isArray);
   }
}
