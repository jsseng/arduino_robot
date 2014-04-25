package parser;

import java.io.*;

public class CharReader
{
   private Reader _input;
   private boolean _gotEOF = false, _putback = false;
   private int _currentChar = '\0';

   public static final int _EOF = -1;

   public CharReader(Reader reader)
   {
      _input = reader;
   }

   public int read()
   {
      if (_putback)
      {
         _putback = false;
      }
      else
      {
         _currentChar = protectedRead();
         /*
         if ((_currentChar = protectedRead()) == '#')
         {
            while (_currentChar != _EOF && (_currentChar = protectedRead()) != '\n');
         }
         */
      }

      if (_currentChar == _EOF)
      {
         _gotEOF = true;
      }

      return _currentChar;
   }

   public int lookahead()
   {
      if (_putback)
      {
         return _currentChar;
      }
      else
      {
         int c = read();
         _putback = true;
         return c;
      }
   }

   public boolean gotEOF()
   {
      return _gotEOF;
   }

   private int protectedRead()
   {
      try
      {
         return _input.read();
      }
      catch (IOException e)
      {
         System.err.println("Unexpected I/O error.");
         System.exit(1);
      }
      return -1;
   }
}

