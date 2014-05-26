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
   private List<Machinery> allMachinery = new ArrayList<Machinery>();

   private Map<String, Visitable> idToType = new HashMap<String, Visitable>();

   public StringBuilder visit(ASTRoot t)
   {
      return new StringBuilder();
   }
   public StringBuilder visit(Machinery t)
   {
      idToType.put(t.getIdentifier(), t);
      StringBuilder buf = new StringBuilder();
      buf.append("#define " + t.getIdentifier() + " " + t.getMachineNumber() + "\n");
      /*
      buf.append(String.format("int auto_%s = %s;\n", t.getIdentifier(), t.toString()));
      */
      return buf;
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
   public StringBuilder visit(AndExpression t)
   {
      return visitBinary(t, "&&");
   }
   public StringBuilder visit(AssignmentExpression t)
   {
      StringBuilder buf = new StringBuilder();
      String id = t.getTarget().getIdentifier();
      Visitable type = idToType.get(id);

      buf.append(t.getTarget().visit(this));
      buf.append(" = ");
      buf.append(t.getSource().visit(this));
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
   public StringBuilder visit(StringExpression t)
   {
      return new StringBuilder("\"" + t.getString() + "\"");
   }
   public StringBuilder visit(ExpressionStatement t)
   {
      lineNum = t.getLineNum();
      return new StringBuilder(t.getExp().visit(this) + ";\n");
   }
   public StringBuilder visit(BooleanConstantExpression t)
   {
      return new StringBuilder(String.valueOf(t.getValue()));
   }
   public StringBuilder visit(CompoundStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append("{\n");
      for (Statement st : t.getStatements())
      {
         buf.append(st.visit(this));
      }
      buf.append("}\n");
      return buf;
   }
   /*
   public StringBuilder visit(Declarations t)
   {
      StringBuilder buf = new StringBuilder();
      for (Declaration d : t)
      {
         buf.append(d.visit(this));
         buf.append("\n");
      }
      return buf;
   }
   */
   public StringBuilder visit(List<Machinery> t)
   {
      StringBuilder buf = new StringBuilder();
      for (Machinery d : t)
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
   public StringBuilder visit(EqualExpression t)
   {
      return visitBinary(t, "==");
   }
   public StringBuilder visit(Function t)
   {
      StringBuilder buf = new StringBuilder();

      buf.append("fn ");
      buf.append(t.getName());
      buf.append("(");
      buf.append(t.getParameters().visit(this));
      buf.append(")\n");
      buf.append(t.getDeclarations().visit(this));
      buf.append(t.getBody().visit(this));

      return buf;
   }
   public StringBuilder visit(Functions t)
   {
      StringBuilder buf = new StringBuilder();
      for (Function f : t)
      {
         buf.append(f.visit(this));
      }
      return buf;
   }
   public StringBuilder visit(GreaterEqualExpression t)
   {
      return visitBinary(t, ">=");
   }
   public StringBuilder visit(GreaterThanExpression t)
   {
      return visitBinary(t, ">");
   }
   public StringBuilder visit(IdentifierExpression t)
   {
      if (idToType.containsKey(t.getIdentifier()))
      {
         return new StringBuilder(t.getIdentifier());
      }
      else
      {
         System.err.printf("Identifer %s not found\n", t.getIdentifier());
         System.exit(0);
         return null;
      }
   }
   public StringBuilder visit(Statement[] t)
   {
      StringBuilder str = new StringBuilder();
      str.append("{\n");
      for (Statement s : t)
      {
         str.append(s.visit(this));
      }
      str.append("}\n");
      return str;
   }
   public StringBuilder visit(TurnOnStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      str.append("turn_on(");
      str.append(t.getId());
      str.append(");\n");
      return str;
   }
   public StringBuilder visit(TurnOffStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      str.append("turn_off(");
      str.append(t.getId());
      str.append(");\n");
      return str;
   }
   public StringBuilder visit(VariableDeclarationStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder str = new StringBuilder();
      Expression e = t.getExpression();
      if (numberType(e) instanceof FloatConstantExpression)
      {
         idToType.put(t.getId(), new FloatConstantExpression(0));
         str.append("float ");
      }
      if (numberType(e) instanceof IntegerConstantExpression)
      {
         idToType.put(t.getId(), new IntegerConstantExpression(0));
         str.append("int ");
      }
      if (numberType(e) instanceof StringExpression)
      {
         idToType.put(t.getId(), new StringExpression(""));
         str.append("char *");
      }
      str.append(t.getId());
      str.append(" = ");
      str.append(t.getExpression().visit(this));
      str.append(";\n");
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
      str.append("delay_milliseconds(300);\n");
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
      str.append("delay_milliseconds(300);\n");
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
      return new StringBuilder("delay_milliseconds(1000 * (" + t.getDuration().visit(this) + "));\n");
   }
   public StringBuilder visit(RotateStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append("set_servo(");
      buf.append(String.valueOf(t.getServo().getServoNum()));
      if (!isValidNonDisplayExpression(t.getAngle()))
      {
         expected("set_servo can only accept numerical values");
      }
      buf.append(", " + t.getAngle().visit(this));
      buf.append(");\n");

      return buf;
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
         buf.append("{\nint repeatCounter = 0;\n");
         buf.append(String.format("for (repeatCounter = 0; repeatCounter < %s; repeatCounter++)\n", t.getTimes().visit(this)));
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
   public StringBuilder visit(IntegerConstantExpression t)
   {
      return new StringBuilder(String.valueOf(t.getValue()));
   }
   public StringBuilder visit(InvocationExpression t)
   {
      StringBuilder buf = new StringBuilder();
      buf.append(t.getName());
      buf.append("(");
      Vector<Expression> args = t.getArguments();
      if (args.size() > 0)
      {
         Iterator<Expression> iter = args.iterator();
         buf.append(iter.next().visit(this));

         while (iter.hasNext())
         {
            buf.append(", ");
            buf.append(iter.next().visit(this));
         }
      }
      buf.append(")");
      return buf;
   }
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
   /*
   public StringBuilder visit(Parameters t)
   {
      StringBuilder buf = new StringBuilder();
      if (t.size() > 0)
      {
         Iterator<Declaration> iter = t.iterator();
         buf.append(iter.next().visit(this));

         while (iter.hasNext())
         {
            buf.append(", ");
            buf.append(iter.next().visit(this));
         }
      }
      return buf;
   }
   */
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
   public StringBuilder visit(ChangesExpression t)
   {
      StringBuilder buf = new StringBuilder();
      buf.append(String.format("(auto_old = auto_%s, auto_new = auto_%s = %s, auto_new != auto_old)", t.getId(), t.getId(), t.getId()));
      return buf;
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

   public StringBuilder visit(Program t)
   {
      List<SourceElement> elems = t.getBody();
      StringBuilder buf = new StringBuilder();
      buf.append("#include <stdio.h>\n\n");
      //buf.append("int auto_old, auto_new;\n");
      buf.append(visit(t.getDeclarations()));
      buf.append(visitGlobalVars(elems));
      buf.append(visitStart(elems));
      buf.append(visitWhens(elems));
      buf.append(visitRepeat(elems));

      buf.append("int main(void) {\n");
      buf.append("start();\n");

      buf.append(visitNonRepeats(elems));

      buf.append(callWhens(elems));

      // buf.append(t.getBody().visit(this));
      /* Must revisit visiting the program's body */

      buf.append("return 0;\n}");

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
   public StringBuilder visit(DisplayStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder formatStr = new StringBuilder();
      formatStr.append("printf(\"");
      StringBuilder args = new StringBuilder();
      List<Expression> exp = t.getArgs();
      for (Expression e : exp)
      {
         formatStr.append(typeToFormatStr(numberType(e)));
         args.append("," + e.visit(this));
      }
      formatStr.append("\\n\"");
      formatStr.append(args + ");\n");
      return formatStr;
   }
   public StringBuilder visit(SetStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append(t.getMachinery().toString());
      buf.append(t.getValue().visit(this));
      buf.append(");\n");
      return buf;
   }
   public StringBuilder visit(SpinStatement t)
   {
      lineNum = t.getLineNum();
      StringBuilder buf = new StringBuilder();
      buf.append("motor(");
      buf.append(t.getMotor().getIdentifier());
      buf.append(", ");
      if (!isValidNonDisplayExpression(t.getValue()))
      {
         expected("motors can only accept numerical values");
      }
      buf.append(t.getValue().visit(this));
      buf.append(");\n");
      return buf;
   }
   public StringBuilder visit(ReadStatement t)
   {
      StringBuilder buf = new StringBuilder();
      Machinery m = t.getMachinery();
      if (m instanceof Gyroscope)
      {
         Gyroscope g = (Gyroscope)m;
         buf.append("accelerometer(");
         buf.append(String.valueOf(g.getAxis()));
         buf.append(")");
      }
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
      buf.append("(");
      buf.append(op);
      buf.append(t.getOperand().visit(this));
      buf.append(")");

      return buf;
   }

   private Expression numberType(Expression e)
   {
      if (e instanceof IdentifierExpression)
      {
         String id = ((IdentifierExpression)e).getIdentifier();
         Visitable v = idToType.get(id);
         if (v instanceof Machinery)
         {
            return new IntegerConstantExpression(0);
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

   private void expected(String s)
   {
      System.err.println(s + " at line " + lineNum);
      System.exit(0);
   }
}
