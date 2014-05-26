package parser;

import java.io.*;

public class Scanner
{
   Token _unget = null;
   int lineNumber;

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
         System.out.println(tk);
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

   private String multiSymbol(int c, int need, boolean optional) 
      throws InvalidSymbolException
   {
      StringBuilder s = new StringBuilder();
      s.append((char)c);
      if (_in.lookahead() == need)
      {
         _in.read();
         s.append((char)need);
         return s.toString();
      }
      else if (optional)
      {
         return s.toString();
      }
      else
      {
         throw new InvalidSymbolException();
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
         case '+':
         case '*':
         case '/':
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
         case '>':
         {
            str = multiSymbol(_in.read(), '=', true); 
            break;
         }
         case '<':
         {
            str = multiSymbol(_in.read(), '=', true); 
            break;
         }
         case '!':
         {
            str = multiSymbol(_in.read(), '=', true); 
            break;
         }
         case '-':
         {
            int character = _in.read();
            if (Character.isDigit(_in.lookahead()))
            {
               Token t = buildNumber();
               return new Token(TokenCode.TK_NUM, "-" + t.toString(), lineNumber);
            }
            str = multiSymbol(character, '>', true); 
            break;
         }
         case ':':
         {
            str = multiSymbol(_in.read(), '=', false); 
            break;
         }
         // unrecognized character
         default:
         {
            throw new InvalidCharacterException();
         }
      }
      return new Token(TokenCode.lookupSymbol(str), lineNumber);
   }

   private Token checkForKeyword(String id)
   {
      TokenCode tk = TokenCode.lookupKeyword(id);
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

