package parser;

import java.io.*;

public class Scanner
{
   Token _unget = null;
   int lineNumber = 1;

   public static void main(String [] args)
      throws Exception
   {
      Scanner scanner;
      if (args.length < 1)
      {
         scanner = new Scanner(null);
      }
      else
      {
         scanner = new Scanner(args[0]);
      }

      Token tk;
      do
      {
         tk = scanner.nextToken();
      } while (!tk.equals(TokenCode.TK_EOF));
   }

   private CharReader _in;

   public Scanner(String filename) throws FileNotFoundException
   {
      if (filename == null)
      {
         _in = new CharReader(
            new BufferedReader(new InputStreamReader(System.in)));
      }
      else
      {
         _in = new CharReader(new BufferedReader(new FileReader(filename)));
      }
      lineNumber = 1;
   }
   
   public void ungetToken(Token t)
   {
      if (_unget == null)
      {
         _unget = t;
      }
      else
      {
         System.err.println("Already one in unget");
         System.exit(0);
      }
   }

   public Token nextToken()
      throws InvalidSymbolException, InvalidCharacterException
   {
      /* Piece for ungetToken */
      if (_unget != null)
      {
         Token temp;
         temp = _unget;
         _unget = null;
         return temp;
      }
      /* End piece for ungetToken */
      clearWhitespace();
      if (_in.gotEOF())
      {
         return new Token(TokenCode.TK_EOF, lineNumber);
      }

      int c = _in.lookahead();


      if (Character.isLetter((char)c))
      {
         return buildIdentifier();
      }
      else if (Character.isDigit((char)c))
      {
         return buildNumber();
      }
      else if (c == '"')
      {
         return buildString();
      }
      else if (c == '#')
      {
         _in.read();
         while (!_in.gotEOF() && _in.lookahead() != '\n')
         {
            _in.read();
         }
         if (_in.gotEOF())
         {
            return new Token(TokenCode.TK_EOF, lineNumber);
         }
         return nextToken();
      }
      else
      {
         return buildSymbol();
      }
   }

   private void clearWhitespace()
   {
      while (!_in.gotEOF() && Character.isWhitespace((char)_in.lookahead()))
      {
         if (_in.read() == '\n')
         {
            lineNumber++;
         }
      }
   }

   private Token buildString()
   {
      StringBuilder buf = new StringBuilder();
      _in.read();
      while (_in.lookahead() != '"')
      {
         buf.append((char)_in.read());
      }
      _in.read();
      return new Token(TokenCode.TK_STRING, buf.toString(), lineNumber);
   }

   private Token buildIdentifier()
   {
      StringBuilder buf = new StringBuilder();

      while (Character.isLetterOrDigit((char)_in.lookahead()))
      {
         buf.append((char)_in.read());
      }

      return checkForKeyword(buf.toString());
   }

   private Token buildNumber()
   {
      StringBuilder buf = new StringBuilder();

      while (Character.isDigit((char)_in.lookahead()))
      {
         buf.append((char)_in.read());
      }

      if (_in.lookahead() == '.')
      {
         buf.append((char)_in.read());
         while (Character.isDigit((char)_in.lookahead()))
         {
            buf.append((char)_in.read());
         }
         return new Token(TokenCode.TK_FLOAT, buf.toString(), lineNumber);
      }

      return new Token(TokenCode.TK_NUM, buf.toString(), lineNumber);
   }

   private String multiSymbol(int c, int[] need, boolean optional) 
      throws InvalidSymbolException {
         StringBuilder s = new StringBuilder();
         s.append((char)c);
         int lookahead = _in.lookahead();

         for (int i = 0; i < need.length; i++) {
            if (lookahead == need[i])
            {
               _in.read();
               s.append((char)need[i]);
               return s.toString();
            }
         }
         if (optional)
         {
            return s.toString();
         }
         else
         {
            if (s.length() == 1)
               throw new InvalidSymbolException();
            else
               return s.toString();
         }

   }

   private Token buildSymbol()
      throws InvalidSymbolException, InvalidCharacterException
   {
      String str = null;
      switch (_in.lookahead())
      {
         case '(':
         case ')':
         case '{':
         case '}':
         case ';':
         case ',':
         case '=':
         case '&':
         case '|':
         case '[':
         case ']':
         {
            str = String.valueOf((char)_in.read());
            break;
         }
         /*case '=':
         {
            str = multiSymbol(_in.read(), '=', true); 
            break;
         }*/
         case '+':
	     str = multiSymbol(_in.read(), new int[]{'+','='}, true);
	     break;
         case '*':
	     str = multiSymbol(_in.read(), new int[]{'='}, true);
	     break;
         case '/':
	     str = multiSymbol(_in.read(), new int[]{'='}, true);
	     break;
         case '>':
	     str = multiSymbol(_in.read(), new int[]{'='}, true); 
            break;
         case '<':
	     str = multiSymbol(_in.read(), new int[]{'='}, true); 
            break;
         case '!':
	     str = multiSymbol(_in.read(), new int[]{'='}, true); 
            break;
         case '-':
            int character = _in.read();
            if (Character.isDigit(_in.lookahead()))
            {
               Token t = buildNumber();
               if (t.code().equals(TokenCode.TK_NUM))
               {
                  return new Token(TokenCode.TK_NUM, "-" + t.toString(), lineNumber);
               }
               else
               {  
                  return new Token(TokenCode.TK_FLOAT, "-" + t.toString(), lineNumber);
               }
            }

	    str = multiSymbol(character, new int[]{'-','='}, true);
            break;
         case ':':
	     str = multiSymbol(_in.read(), new int[]{'='}, false); 
            break;
         // unrecognized character
         default:
            throw new InvalidCharacterException();
      }
      return new Token(TokenCode.lookupSymbol(str), lineNumber);
   }

   private Token checkForKeyword(String id)
   {
      TokenCode tk = TokenCode.lookupKeyword(id);
      if (tk == TokenCode.TK_EQ2)
      {
         return new Token(TokenCode.TK_EQ, id, lineNumber);
      }
      if (tk == TokenCode.TK_NE2)
      {
         return new Token(TokenCode.TK_NE, id, lineNumber);
      }
      if (tk == TokenCode.TK_VARIABLE2)
      {
         return new Token(TokenCode.TK_VARIABLE, id, lineNumber);
      }
      if (tk != TokenCode.TK_NONE)
      {
         return new Token(tk, lineNumber);
      }
      else
      {
         return new Token(TokenCode.TK_ID, id, lineNumber);
      }
   }
}
