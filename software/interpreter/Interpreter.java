import parser.*;
import ast.Program;
import visitor.*;
import eval.EvalProgramVisitor;

import java.io.FileNotFoundException;

public class Interpreter
{
   private static boolean _dumpAST;
   private static final String ASTFLAG = "-dumpAST";

   public static void main(String [] args)
   {
      try
      {
         Program prog = new Parser(createScanner(args)).parse();
         if (_dumpAST)
         {
            System.out.print(prog.visit(new ASTStringVisitor()));
            System.exit(0);
         }

         System.out.println("Finished Parsing");
         System.exit(0);
         EvalProgramVisitor eval = new EvalProgramVisitor();
         eval.visit(prog);
      }
      catch (FileNotFoundException e)
      {
         System.err.println("file not found");
      }
      catch (InvalidSymbolException e)
      {
         System.err.println("invalid symbol");
      }
      catch (InvalidCharacterException e)
      {
         System.err.println("invalid character");
      }
      catch (ScannerException e)
      {
         System.err.println("scan error");
      }
   }

   private static Scanner createScanner(String [] args)
      throws FileNotFoundException
   {
      if (args.length < 1)
      {
         _dumpAST = false;
         return new Scanner(null);
      }
      else if (args.length == 1)
      {
         if (args[0].equals(ASTFLAG))
         {
            _dumpAST = true;
            return new Scanner(null);
         }
         else
         {
            _dumpAST = false;
            return new Scanner(args[0]);
         }
      }
      else
      {
         if (args[0].equals(ASTFLAG))
         {
            _dumpAST = true;
            return new Scanner(args[1]);
         }
         else if (args[1].equals(ASTFLAG))
         {
            _dumpAST = true;
            return new Scanner(args[0]);
         }
         else
         {
            System.err.println("error: too many arguments");
            // try the first one
            return new Scanner(args[0]);
         }
      }
   }
}
