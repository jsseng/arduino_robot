import parser.*;
import ast.Program;
import visitor.*;

import java.io.FileNotFoundException;

public class Interpreter
{
    public void run(String filename)
    {
        try
        {
            Program prog = new Parser(new Scanner(filename)).parse();
            System.out.print(prog.visit(new ASTStringBuilderVisitor()));
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found");
        }
        catch (InvalidSymbolException e)
        {
            System.err.println("invalid symbol: " + e.getMessage());
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
}
// vim: ts=4:sw=4
