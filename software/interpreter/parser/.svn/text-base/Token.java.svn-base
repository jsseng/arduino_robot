package parser;

public final class Token
{
   private String _str;
   private TokenCode _code;

   public Token(TokenCode code)
   {
      _code = code;
      _str = code.concreteString();
   }

   public Token(TokenCode code, String s)
   {
      _code = code;
      _str = s;
   }

   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that instanceof Token)
      {
         return _code == ((Token)that)._code;
      }
      if (that instanceof TokenCode)
      {
         return _code == that;
      }

      return false;
   }

   public TokenCode code()
   {
      return _code;
   }

   public String toString()
   {
      return _str;
   }

   public String fullString()
   {
      return _code + ": " +_str;
   }
}
