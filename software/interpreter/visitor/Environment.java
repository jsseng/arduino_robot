package visitor;

import java.util.*;
import ast.Visitable;

public class Environment
{
   private Environment next;
   private Map<String, Visitable> idToType;

   public Environment()
   {
      next = null;
      idToType = new HashMap<String, Visitable>();
   }

   public Environment(Environment next)
   {
      this.next = next;
      idToType = new HashMap<String, Visitable>();
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

   public void put(String id, Visitable v)
   {
      idToType.put(id, v);
   }
}
