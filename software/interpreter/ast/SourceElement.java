package ast;

public abstract class SourceElement 
   implements Visitable 
{
   private int lineNum;

   public SourceElement()
   {
      lineNum = 0;
   }

   public SourceElement(int lineNum)
   {
      this.lineNum = lineNum;
   }

   public int getLineNum()
   {
      return lineNum;
   }

   public void setLineNum(int num)
   {
      lineNum = num;
   }
}

