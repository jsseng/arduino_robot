package visitor;

import java.util.Vector;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.*;

import visitor.*;
import ast.*;

public class ASTStringBuilderVisitor
extends ASTVisitor<StringBuilder>
{
   private int whenCount = 0;
   private int lineNum = 0;
   private Environment currentEnvir;


   public StringBuilder visit(Program t) {
      currentEnvir = new Environment();
      /* Initialize with 2 pieces of machinery */
      currentEnvir.put("button", new Button());
      currentEnvir.put("LED0", new LED(0));
      currentEnvir.put("LED1", new LED(1));
      List<SourceElement> elems = t.getBody();
      StringBuilder buf = new StringBuilder();
      buf.append("#include <stdio.h>\n");
      buf.append("#include <stdlib.h>\n\n");

      /* These are the arduino headers */
      buf.append("#include \"globals.h\"\n");
      buf.append("#include <util/delay.h>\n");
      buf.append("#include <avr/io.h>\n");
      buf.append("#include \"USI_TWI_Master.h\"\n");
      buf.append("#include <avr/interrupt.h>\n\n");

      buf.append(String.format("#define BUFFER_LEN %d\n\n", 128));


      buf.append("int _arrayCheck(int size, int index) { if (index >= size) { fprintf(stderr, \"Index out of bounds\\n\"); exit(0); } return index; }\n");

      buf.append(visit(t.getDeclarations()));
      buf.append(String.format("int chng_temp;\n"));
      buf.append(String.format("char _printBuffer[64];\n"));
      buf.append(visitGlobalVars(elems));
      buf.append(visitFunctions(elems));
      buf.append(visitStart(elems));
      buf.append(visitWhens(elems));
      buf.append(visitRepeat(elems));

      buf.append("int main(void) {\n");
      buf.append("init();\n");
      buf.append("start();\n");

      buf.append(visitNonRepeats(elems));

      buf.append(callWhens(elems));

      buf.append("return 0;\n}");

      return buf;
   }
   public StringBuilder visit(Declaration t)
   {
      String id = t.getIdentifier();
      uniqueID(id);
      Machinery m = t.getMachinery();
      currentEnvir.put(id, m);
      StringBuilder buf = new StringBuilder();
      id = id;
      buf.append("#define " + id + " " + m.getMachineNumber() + "\n");
      if (m instanceof Gettable)
      {
         buf.append(String.format("static int chng_%s = 0;\n", id));
      }
      return buf;
   }
   public StringBuilder visit(AssignmentStatement t)
   {
      StringBuilder buf = new StringBuilder();
      String id = t.getTarget().getIdentifier();
      Visitable type = currentEnvir.get(id);

      buf.append(t.getTarget().visit(this));
      buf.append(" = ");
      buf.append(t.getSource().visit(this));
      buf.append(";\n");
      Expression numType = numberType(t.getSource());
      if (type instanceof Machinery)
      {
         expected("Cannot assign values into a Machinery");
      }
      if (type instanceof StringExpression && !(numType instanceof StringExpression))
      {
         expected("Cannot assign numbers into String variables");
      }
      if (!(type instanceof StringExpression) && numType instanceof StringExpression)
      {
         expected("Cannot assign Strings into numerical variables");
      }
      if (numType instanceof FloatConstantExpression && type instanceof IntegerConstantExpression)
      {
         System.err.println("Warning: Loss of precision involving assigning a decimal into " + id + " at line " + lineNum);
      }
      return buf;
   }
   public StringBuilder visit(ExpressionStatement t)
   {
      lineNum = t.getLineNum();
      return new StringBuilder(t.getExp().visit(this) + ";\n");
   }
   public StringBuilder visit(List<Declaration> t)
   {
      StringBuilder buf = new StringBuilder();
      for (Declaration d : t)
      {
         buf.append(d.visit(this));
         buf.append("\n");
      }
      return buf;
   }
   public StringBuilder visit(DivideExpression t)
   {
      /* Debatable. Auto cast all division into floats? */
      // return visitBinary(t, "/");
      return visitBinary(t, "/(float)");
   }
   public StringBuilder visit(Function t)
   {
      lineNum = t.getLineNum();
      String id = t.getID();
      uniqueID(id);
      currentEnvir.put(id, t);
      if (!ASTCheckerVisitor.hasReturn(t.getBody()))
      {
         expected("A return statement in function " + t.getID());
      }

      Environment oldEnvir = currentEnvir;
      currentEnvir = new Environment(oldEnvir);

      StringBuilder buf = new StringBuilder();
      buf.append(String.format("float %s(", id));
      List<String> params = t.getParameters();
      buf.append(listToComma(params, "float"));

      for (String s : params)
      {
         currentEnvir.put(s, new FloatConstantExpression(0));
      }

      buf.append(")\n");
      buf.append(visit(t.getBody(), currentEnvir));

      currentEnvir = oldEnvir;
      return buf;
   }
   public StringBuilder visit(IdentifierExpression t)
   {
      if (currentEnvir.containsKey(t.getIdentifier()))
      {
         if (t.isArrayAccess() != currentEnvir.isArray(t.getIdentifier()))
         {
            expected("Improper access. Either accessing as array of non-array variable or accessing array variable as non-array\n");
         }
         if (!t.isArrayAccess())
         {
            return new StringBuilder(t.getIdentifier());
         }
         else
         {
            return new StringBuilder(String.format("%s[_arrayCheck(%s, %s)]", t.getIdentifier(), currentEnvir.getArraySize(t.getIdentifier()), t.getArrayIndex().visit(this)));
         }
      }
      else
      {
         expected(String.format("Identifer %s not found\n", t.getIdentifier()));
         return null;
      }
   }
   public StringBuilder visit(Statement[] t)
   {
      return visit(t, new Environment(currentEnvir));
   }

   public StringBuilder visit(Statement[] t, Environment e)
   {
      StringBuilder str = new StringBuilder();
      Environment oldEnvir = currentEnvir;
      currentEnvir = e;
      str.append("{\n");
      for (Statement s : t)
      {
         str.append(s.visit(this));
      }
      str.append("}\n");
      currentEnvir = oldEnvir;
      return str;
   }
   public StringBuilder visit(VariableDeclarationStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      Expression e = t.getExpression();
      String id = t.getId();
      String varType = null;
      uniqueID(id);
      if (numberType(e) instanceof FloatConstantExpression)
      {
         currentEnvir.put(id, new FloatConstantExpression(0), t.getIsArray(), t.getArraySize());
         varType = "float ";
      }
      if (numberType(e) instanceof IntegerConstantExpression)
      {
         currentEnvir.put(id, new IntegerConstantExpression(0), t.getIsArray(), t.getArraySize());
         varType = "int ";
      }
      if (numberType(e) instanceof StringExpression)
      {
         currentEnvir.put(id, new StringExpression(""), t.getIsArray(), t.getArraySize());
         varType = "char *";
      }
      if (t.getIsArray())
      {
         if (t.getArraySize() < 1)
         {
            expected("Array size must be 2 or more");
         }
         str.append(String.format("%s%s[%d] = {", varType, id, t.getArraySize()));
         List<String> init = new ArrayList<String>();
         String arg = t.getExpression().visit(this).toString();
         for (int i = 0; i < t.getArraySize(); i++)
         {
            init.add(arg);
         }
         str.append(listToComma(init, ""));
         str.append("};\n");
      }
      else
      {
         str.append(String.format("%s%s = %s;\n", varType, id, t.getExpression().visit(this).toString()));
      }
      return str;
   }
   public StringBuilder visit(ForwardStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      str.append("motor(0, " + t.getMove().visit(this) + ");\n");
      str.append("motor(1, " + t.getMove().visit(this) + ");\n");
      return str;
   }
   public StringBuilder visit(BackwardStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      str.append("motor(0, -1*(" + t.getMove().visit(this) + "));\n");
      str.append("motor(1, -1*(" + t.getMove().visit(this) + "));\n");
      return str;
   }
   public StringBuilder visit(LeftStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      /* Assumptions that 0 is top left, 1 is top right
       * 2 is bottom left, 3 is bottom right */
      str.append("/* Beginning left turn */\n");
      str.append("motor(0, -50);\n");
      str.append("motor(1, 50);\n");
      str.append("motor(2, -50);\n");
      str.append("motor(3, 50);\n");
      str.append("_delay_ms(300);\n");
      str.append("motor(0, 0);\n");
      str.append("motor(1, 0);\n");
      str.append("motor(2, 0);\n");
      str.append("motor(3, 0);\n");
      str.append("/* End left turn */\n");
      return str;
   }
   public StringBuilder visit(RightStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      /* Assumptions that 0 is top left, 1 is top right
       * 2 is bottom left, 3 is bottom right */
      str.append("/* Beginning right turn */\n");
      str.append("motor(0, 50);\n");
      str.append("motor(1, -50);\n");
      str.append("motor(2, 50);\n");
      str.append("motor(3, -50);\n");
      str.append("_delay_ms(300);\n");
      str.append("motor(0, 0);\n");
      str.append("motor(1, 0);\n");
      str.append("motor(2, 0);\n");
      str.append("motor(3, 0);\n");
      str.append("/* End right turn */\n");
      return str;
   }

   public StringBuilder visit(SleepStatement t)
   {
      lineNum = t.getLineNum();
      return new StringBuilder("_delay_ms(" + t.getDuration().visit(this) + ");\n");
   }

   public StringBuilder visit(StopStatement t) {
       lineNum = t.getLineNum();
       return new StringBuilder("exit(0);\n");
   }

   public StringBuilder visit(RepeatStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      Expression times = t.getTimes();
      if (times != null)
      {
         if (!isValidNonDisplayExpression(times))
         {
            expected("when repeats can only accept numerical values");
         }
         buf.append("{\nint rptCnt;\n");
         buf.append(String.format("for (rptCnt = 0; rptCnt < %s; rptCnt++)\n", t.getTimes().visit(this)));
         buf.append(visit(t.getBody()));
         buf.append("}\n");
      }
      else
      {
         buf.append(visit(t.getBody()));
      }
      return buf;
   }
   public StringBuilder visit(IfStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append("if (");
      buf.append(t.getGuard().visit(this));
      buf.append(")\n");
      buf.append(visit(t.getThen()));
      buf.append("else\n");
      buf.append(visit(t.getElse()));
      return buf;
   }
   public StringBuilder visit(CallExpression t)
   {
      StringBuilder buf = new StringBuilder();
      String functionID = t.getID();
      Function f = (Function)currentEnvir.get(functionID);
      
      if (f == null)
      {
         expected(String.format("function name of %s not found", functionID));
      }

      buf.append(t.getID());
      buf.append("(");
      List<Expression> args = t.getParams();
      if (f.getParameters().size() != args.size())
      {
         expected(String.format("Parameter count do not match, expected %d parameters, found %d\n", f.getParameters().size(), args.size()));
      }
      buf.append(visitableListToComma(args, ""));
      buf.append(")");
      return buf;
   }
   
   public StringBuilder visit(ChangesExpression t)
   {
      StringBuilder buf = new StringBuilder();
      String id = t.getId();

      buf.append(String.format("chng_temp = chng_%s, chng_%s =  %s, chng_temp != chng_%s", id, id, visit(new GetExpression(id)), id));
      
      
      return buf;
   }

   public StringBuilder visit(OrWhenExpression t)
   {
      StringBuilder buf = new StringBuilder();
      buf.append("(");
      buf.append(t.getLeftOperand().visit(this));
      buf.append(" || ");
      buf.append(t.getRightOperand().visit(this));
      buf.append(")");
      return buf;
   }
   public StringBuilder visit(AndWhenExpression t)
   {
      StringBuilder buf = new StringBuilder();
      buf.append("(");
      buf.append(t.getLeftOperand().visit(this));
      buf.append(" && ");
      buf.append(t.getRightOperand().visit(this));
      buf.append(")");
      return buf;
   }
   public StringBuilder visit(WhenStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append(String.format("void when%d(void)\n{\n", whenCount++));
      
      buf.append("if (");
      buf.append(t.getGuard().visit(this));
      buf.append(")\n");
      buf.append(visit(t.getBody()));
      buf.append("}\n");
      return buf;
   }
   public StringBuilder visit(BooleanWhenExpression t)
   {
      return t.getExp().visit(this);
   }
   public StringBuilder visit(BetweenExpression t)
   {
      StringBuilder buf = new StringBuilder();
      buf.append(String.format("(%s <= %s && %s <= %s)", t.getLower().visit(this), t.getId(), t.getId(), t.getHigher().visit(this)));
      return buf;
   }
   public StringBuilder visitWhens(List<SourceElement> t)
   {
      StringBuilder buf = new StringBuilder();
      WhenExpression guard;
      for (SourceElement s : t)
      {
         if (s instanceof WhenStatement)
         {
            buf.append(visit((WhenStatement)s));
         }
      }
      return buf;
   }
   public StringBuilder callWhens(List<SourceElement> t)
   {
      int count = 0;
      StringBuilder buf = new StringBuilder();
      buf.append("while (1) {\n");
      Iterator<SourceElement> iter = t.iterator();
      while (iter.hasNext())
      {
         SourceElement s = iter.next();
         if (s instanceof WhenStatement)
         {
            WhenExpression guard = ((WhenStatement)s).getGuard();
               buf.append(String.format("when%d();\n", count++));
               iter.remove();
         }
      }
      buf.append("repeatAlways();\n");
      buf.append("}\n");
      return buf;
   }

   public StringBuilder visitStart(List<SourceElement> t)
   {
      StringBuilder buf = new StringBuilder();
      WhenExpression guard;
      Statement[] body = new Statement[0];
      boolean start = false;
      Iterator<SourceElement> iter = t.iterator();
      while (iter.hasNext())
      {
         SourceElement s = iter.next();
         if (s instanceof WhenStatement)
         {
            guard = ((WhenStatement)s).getGuard();
            if (guard instanceof StartExpression)
            {
               if (!start)
               {
                  body = ((WhenStatement)s).getBody();
                  iter.remove();
                  start = true;
               }
               else
               {
                  expected("Cannot have more than one when start");
               }
            }
         }         
      }
      buf.append("void start(void)\n");
      buf.append(visit(body));
      return buf;
   }

   public StringBuilder visitRepeat(List<SourceElement> t)
   {
      StringBuilder buf = new StringBuilder();
      Statement[] body = new Statement[0];
      Expression times;
      boolean found = false;
      int i = 0;
      Iterator<SourceElement> iter = t.iterator();
      while (iter.hasNext())
      {
         SourceElement s = iter.next();
         if (s instanceof RepeatStatement)
         {
            times = ((RepeatStatement)s).getTimes();
            if (times == null)
            {
               if (!found)
               {
                  body = ((RepeatStatement)s).getBody();
                  iter.remove();
                  found = true;
               }
               else
               {
                  expected("Cannot have more than one repeat forever statement");
               }
            }
         }
      }
      buf.append("void repeatAlways(void)\n");
      buf.append(visit(body));
      return buf;
   }

   public StringBuilder visitNonRepeats(List<SourceElement> elem)
   {
      StringBuilder buf = new StringBuilder();
      for (SourceElement s : elem)
      {
         if (!(s instanceof WhenStatement))
         {
            buf.append(s.visit(this));
         }
      }
      return buf;
   }

   public StringBuilder visitGlobalVars(List<SourceElement> elems)
   {
      StringBuilder buf = new StringBuilder();
      Iterator<SourceElement> iter = elems.iterator();
      while (iter.hasNext())
      {
         SourceElement s = iter.next();
         if (s instanceof VariableDeclarationStatement)
         {
            buf.append(((VariableDeclarationStatement)s).visit(this));
            iter.remove();
         }
      }

      return buf;
   } 


   public StringBuilder visitFunctions(List<SourceElement> elems)
   {
      StringBuilder buf = new StringBuilder();
      List<Function> funcs = new ArrayList<Function>();
      Iterator<SourceElement> iter = elems.iterator();
      while (iter.hasNext())
      {
         SourceElement elem = iter.next();
         if (elem instanceof Function)
         {
            funcs.add((Function)elem);
            iter.remove();
         }
      }

      /* Process Function Definitions */
      for (Function func : funcs)
      {
         buf.append(visit(func));
         buf.append("\n");
      }

      return buf;
   }

   public StringBuilder visit(ReturnStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append("return ");
      buf.append(t.getExpression().visit(this));
      buf.append(";\n");
      return buf;
   }
   public StringBuilder visit(DisplayStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder formatStr = new StringBuilder();
      formatStr.append("sprintf(_printBuffer, \"");
      //formatStr.append("print_string

      //formatStr.append("printf(\"");
      StringBuilder args = new StringBuilder();
      List<Expression> exp = t.getArgs();
      for (Expression e : exp)
      {
         formatStr.append(typeToFormatStr(numberType(e)));
         args.append("," + e.visit(this));
      }
      formatStr.append("\\n\"");
      formatStr.append(args + ");\n");
      formatStr.append("print_string(_printBuffer);\n");
      return formatStr;
   }
   public StringBuilder visit(SetStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      String id = t.getMachinery();
      Visitable v = currentEnvir.get(id);
      if (v instanceof Settable)
      {
         Settable s = (Settable)v;
         Expression exp = t.getValue();
         if (!isValidNonDisplayExpression(exp))
         {
            expected("Machinery can only accept numerical values");
         }
         buf.append(s.toSetString(t.getValue().visit(this)));
         buf.append(";\n");
      }
      else
      {
         expected(id + " cannot be set");
      }
      return buf;
   }
   public StringBuilder visit(GetExpression t)
   {
      StringBuilder buf = new StringBuilder();
      String id = t.getMachinery();
      Visitable v = currentEnvir.get(id);
      if (v instanceof Gettable)
      {
         Gettable g = (Gettable)v;
         buf.append(g.toGetString());
      }
      else
      {
         expected(id + " cannot be get");
      }
      return buf;
   }
   /* ===== Start Trivial Functions ===== */
   public StringBuilder visit(LessEqualExpression t)
   {
      return visitBinary(t, "<=");
   }
   public StringBuilder visit(LessThanExpression t)
   {
      return visitBinary(t, "<");
   }
   public StringBuilder visit(MultiplyExpression t)
   {
      return visitBinary(t, "*");
   }
   public StringBuilder visit(NotEqualExpression t)
   {
      return visitBinary(t, "!=");
   }
   public StringBuilder visit(NotExpression t)
   {
      return visitUnary(t, "!");
   }
   public StringBuilder visit(OrExpression t)
   {
      return visitBinary(t, "||");
   }
   public StringBuilder visit(IntegerConstantExpression t)
   {
      return new StringBuilder(String.valueOf(t.getValue()));
   }
   public StringBuilder visit(GreaterEqualExpression t)
   {
      return visitBinary(t, ">=");
   }
   public StringBuilder visit(GreaterThanExpression t)
   {
      return visitBinary(t, ">");
   }
   public StringBuilder visit(EqualExpression t)
   {
      return visitBinary(t, "==");
   }
   public StringBuilder visit(BooleanConstantExpression t)
   {
      return new StringBuilder(String.valueOf(t.getValue()));
   }
   public StringBuilder visit(StringExpression t)
   {
      return new StringBuilder("\"" + t.getString() + "\"");
   }
   public StringBuilder visit(ASTRoot t)
   {
      return new StringBuilder();
   }
   public StringBuilder visit(FloatConstantExpression t)
   {
      StringBuilder buf = new StringBuilder();
      buf.append(String.valueOf(t.getValue()));
      return buf;
   }
   public StringBuilder visit(AddExpression t)
   {
      return visitBinary(t, "+");
   }
   
   public StringBuilder visit(PlusEqStatement t) {
       return new StringBuilder().append(visit(t.getTarget()) + "+=" + t.getSource().visit(this) + ";\n");
   }

   public StringBuilder visit(MinusEqStatement t) {
       return new StringBuilder().append(visit(t.getTarget()) + "-=" + t.getSource().visit(this) + ";\n");
   }

   public StringBuilder visit(MultEqStatement t) {
       return new StringBuilder().append(visit(t.getTarget()) + "*=" + t.getSource().visit(this) + ";\n");
   }

   public StringBuilder visit(DivEqStatement t) {
       return new StringBuilder().append(visit(t.getTarget()) + "/=" + t.getSource().visit(this) + ";\n");
   }

   public StringBuilder visit(PlusPlusExpression t) {
       return visitUnary(t, "++");
   }

   public StringBuilder visit(MinusMinusExpression t) {
       return visitUnary(t, "--");
   }

   public StringBuilder visit(AndExpression t)
   {
      return visitBinary(t, "&&");
   }
   public StringBuilder visit(SubtractExpression t)
   {
      return visitBinary(t, "-");
   }
   public StringBuilder visit(UnitConstantExpression t)
   {
      return new StringBuilder("unit");
   }
   public StringBuilder visit(WhileStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append("while (");
      buf.append(t.getGuard().visit(this));
      buf.append(")\n");
      buf.append(t.getBody().visit(this));
      return buf;
   }
   private StringBuilder visitBinary(BinaryExpression t, String op)
   {
      StringBuilder buf = new StringBuilder();
      buf.append("(");
      buf.append(t.getLeftOperand().visit(this));
      buf.append(" ");
      buf.append(op);
      buf.append(" ");
      buf.append(t.getRightOperand().visit(this));
      buf.append(")");

      return buf;
   }
   private StringBuilder visitUnary(UnaryExpression t, String op)
   {
      StringBuilder buf = new StringBuilder();
      if (t.isPre()) {
	  buf.append("(");
	  buf.append(op);
	  buf.append(t.getOperand().visit(this));
	  buf.append(")");
      }
      else {
	  buf.append("(");
          buf.append(t.getOperand().visit(this));
	  buf.append(op);
          buf.append(")");
      }

      return buf;
   }
   /* ===== End Trivial Functions ===== */

   private Expression numberType(Expression e)
   {
      if (e instanceof IdentifierExpression)
      {
         String id = ((IdentifierExpression)e).getIdentifier();
         Visitable v = currentEnvir.get(id);
         if (v instanceof Machinery)
         {
            expected("Cannot numberType a machinery");
         }
         if (v instanceof FloatConstantExpression)
         {
            return new FloatConstantExpression(0);
         }
         return new IntegerConstantExpression(0);
      }
      if (e instanceof StringExpression)
      {
         return e;
      }
      if (e instanceof CallExpression)
      {
         return new FloatConstantExpression(0);
      }
      if (e instanceof GetExpression)
      {
         return new IntegerConstantExpression(0);
      }
      if (e instanceof UnaryExpression)
      {
         return numberType(((UnaryExpression)e).getOperand());
      }
      if (e instanceof EqualExpression || e instanceof NotEqualExpression)
      {
         return new IntegerConstantExpression(0);
      }
      if (e instanceof BinaryExpression)
      {
         BinaryExpression exp = (BinaryExpression)e;
         Expression typeLeft = numberType(exp.getLeftOperand());
         Expression typeRight = numberType(exp.getRightOperand());
         if (typeLeft instanceof StringExpression || typeRight instanceof StringExpression)
         {
            return new StringExpression("");
         }
         if (typeLeft instanceof IntegerConstantExpression
         &&
         typeRight instanceof IntegerConstantExpression)
         {
            return new IntegerConstantExpression(0);
         }
         else
         {
            return new FloatConstantExpression(0);
         }
      }
      return e;
   }

   private boolean isValidNonDisplayExpression(Expression e)
   {
      if (e instanceof BinaryExpression)
      {
         BinaryExpression exp = (BinaryExpression)e;
         return isValidNonDisplayExpression(exp.getLeftOperand()) &&
             isValidNonDisplayExpression(exp.getRightOperand());
      }
      if (e instanceof UnaryExpression)
      {
         return isValidNonDisplayExpression(((UnaryExpression)e).getOperand());
      }
      if (e instanceof StringExpression)
      {
         return false;
      }
      return true;
   }

   private String typeToFormatStr(Expression e)
   {
      if (e instanceof StringExpression)
      {
         return "%s";
      }
      if (e instanceof FloatConstantExpression)
      {
         return "%f";
      }
      if (e instanceof IntegerConstantExpression)
      {
         return "%d";
      }
      System.err.println("Type is not String, float, or int");
      return "";
   }

   private StringBuilder listToComma(List<String> l, String prefix)
   {
      StringBuilder buf = new StringBuilder();
      Iterator<String> iter = l.iterator();
      if (iter.hasNext())
      {
         String s = iter.next();
         buf.append(String.format("%s %s", prefix, s));
         while (iter.hasNext())
         {
            s = iter.next();
            buf.append(String.format(", %s %s", prefix, s));
         }
      }
      return buf;
   }
   private StringBuilder visitableListToComma(List<? extends Visitable> l, String prefix)
   {
      StringBuilder buf = new StringBuilder();
      Iterator<? extends Visitable> iter = l.iterator();
      if (iter.hasNext())
      {
         Visitable s = iter.next();
         buf.append(String.format("%s %s", prefix, s.visit(this)));
         while (iter.hasNext())
         {
            s = iter.next();
            buf.append(String.format(", %s %s", prefix, s.visit(this)));
         }
      }
      return buf;
   }

   private void uniqueID(String id)
   {
      if (currentEnvir.containsKey(id))
      {
         expected("Identifier name of " + id + " already taken");
      }
   }

   private void expected(String s)
   {
      System.err.println(s + " at line " + lineNum);
      System.exit(0);
   }
}
