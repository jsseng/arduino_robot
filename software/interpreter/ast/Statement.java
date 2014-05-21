package ast;

public abstract class Statement
   extends SourceElement
   implements StatementVisitable
{
   public Statement()
   {
      super();
   }

   public Statement(int lineNum)
   {
      super(lineNum);
   }
}
