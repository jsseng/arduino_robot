package parser;

public final class Token
{
   private String _str;
   private TokenCode _code;
   private int lineNumber;

   public Token(TokenCode code, int lineNumber)
   {
      _code = code;
      _str = code.concreteString();
      this.lineNumber = lineNumber;
   }

   public Token(TokenCode code, String s, int lineNumber)
   {
      _code = code;
      _str = s;
      this.lineNumber = lineNumber;
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

   public int getLine()
   {
      return lineNumber;
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
