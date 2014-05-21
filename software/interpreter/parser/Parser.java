package parser;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import ast.*;
import java.util.Vector;
import visitor.ASTStringBuilderVisitor;

public class Parser
{
   private Scanner _scanner;
   private Token _currentToken = new Token(TokenCode.TK_NONE);

   public Parser(Scanner scanner)
   {
      _scanner = scanner;
   }

   public Program parse() throws ScannerException
   {
      nextToken();
      Program prog = parseProgram();
      match(TokenCode.TK_EOF);

      return prog;
   }

   private Program parseProgram() throws ScannerException
   {
      List<Machinery> decls = parseDeclarations();
 //     Declarations decls = parseDeclarations();
      List<SourceElement> sourceElements = new LinkedList<SourceElement>();
      while (_currentToken.code() != TokenCode.TK_EOF)
      {
         if (_currentToken.code() == TokenCode.TK_WHEN)
         {
            sourceElements.add(parseWhenStatement());
         }
         else
         {
            sourceElements.add(parseStatement());
         }
      }

      return new Program(decls, sourceElements);
   }

   private SourceElement parseWhenStatement() throws ScannerException {
      WhenExpression condition = null;

      match(TokenCode.TK_WHEN);

      if (_currentToken.code() == TokenCode.TK_START) {
         match(TokenCode.TK_START);
         condition = new StartExpression();
      }
      else {
         condition = parseWhenCondition();
      }

      Statement[] block = parseBlockStatement();
      return new WhenStatement(condition, block);
   }
    
    private WhenExpression parseWhenCondition()
	throws ScannerException {
        return parseWhenOrExpression();
    }

    private WhenExpression parseWhenOrExpression()
	throws ScannerException {
        WhenExpression lft = parseWhenAndExpression();
        WhenExpression exp = parseRptWhenOrExpression(lft);
        return exp;
    }
    
    private WhenExpression parseRptWhenOrExpression(WhenExpression lft)
	throws ScannerException {
        if (_currentToken.equals(TokenCode.TK_OR)) {
            match(TokenCode.TK_OR);
            WhenExpression rht = parseWhenAndExpression();
            return parseRptWhenOrExpression(
             new OrWhenExpression(lft, rht));
        }
        else {
            return lft;
        }
    }

    private WhenExpression parseWhenAndExpression()
	throws ScannerException {
        WhenExpression lft = parseEventExpression();
        return parseRptWhenAndExpression(lft);
    }

    private WhenExpression parseRptWhenAndExpression(WhenExpression lft)
       throws ScannerException {
          if (_currentToken.equals(TokenCode.TK_AND)) {
             match(TokenCode.TK_AND);
             WhenExpression rht = parseEventExpression();
             return parseRptWhenAndExpression(new AndWhenExpression(lft, rht));
          }
          else {
             return lft;
          }
       }

    private WhenExpression parseEventExpression()
       throws ScannerException
       {
          if (_currentToken.equals(TokenCode.TK_ID)) {
             Token taken = _currentToken;
             String id = matchIdentifier();
             switch (_currentToken.code())
             {
                case TK_CHANGES:
                   match(TokenCode.TK_CHANGES);
                   return new ChangesExpression(id);
                case TK_BETWEEN:
                   match(TokenCode.TK_BETWEEN);
                   Expression exp1 = parseRelationalExpression();
                   match(TokenCode.TK_AND);
                   Expression exp2 = parseRelationalExpression();
                   return new BetweenExpression(id, exp1, exp2);
                default:
                   ungetToken(taken);
                   return parseEqualityWhenExpression();
             }
          }
          else if (_currentToken.equals(TokenCode.TK_LPAREN))
          {
             match(TokenCode.TK_LPAREN);
             WhenExpression exp = parseWhenCondition();
             match(TokenCode.TK_RPAREN);
             return exp;
          }
          else
          {
             return parseEqualityWhenExpression();
          }
       }

    private WhenExpression parseEqualityWhenExpression()
	throws ScannerException {
        return new BooleanWhenExpression(parseEqualityExpression());
    }

    

   private List<Machinery> parseDeclarations() throws ScannerException
   {
      //Declarations decls = new Declarations();
      List<Machinery> decls = new ArrayList<Machinery>();
      while (_currentToken.equals(TokenCode.TK_DEFINE))
      {
         match(TokenCode.TK_DEFINE);
         String id = matchIdentifier();
         match(TokenCode.TK_ASSIGN);
         Machinery machine = parseMachinery(id);
         decls.add(machine);
      }

      return decls;
   }

   private Machinery parseMachinery(String id) throws ScannerException
   {
      int num, num2;
      switch (_currentToken.code())
      {
         case TK_ANALOGPININ:
            match(TokenCode.TK_ANALOGPININ);
            match(TokenCode.TK_LBRACKET);
            num = Integer.parseInt(_currentToken.toString());
            nextToken();
            match(TokenCode.TK_RBRACKET);
            return new AnalogPin(id, num);
         case TK_DIGITALPINSOUT:
            match(TokenCode.TK_DIGITALPINSOUT);
            match(TokenCode.TK_LBRACKET);
            num = Integer.parseInt(_currentToken.toString());
            nextToken();
            match(TokenCode.TK_COMMA);
            num2 = Integer.parseInt(_currentToken.toString());
            nextToken();
            match(TokenCode.TK_RBRACKET);
            return new DigitalPin(id, num, num2, true);
         case TK_DIGITALPINSIN:
            match(TokenCode.TK_DIGITALPINSIN);
            match(TokenCode.TK_LBRACKET);
            num = Integer.parseInt(_currentToken.toString());
            nextToken();
            match(TokenCode.TK_COMMA);
            num2 = Integer.parseInt(_currentToken.toString());
            nextToken();
            match(TokenCode.TK_RBRACKET);
            return new DigitalPin(id, num, num2, false);
         case TK_SERVO:
            match(TokenCode.TK_SERVO);
            match(TokenCode.TK_LBRACKET);
            num = Integer.parseInt(_currentToken.toString());
            nextToken();
            match(TokenCode.TK_RBRACKET);
            return new Servo(id, num);
         default:
            expected("'analogPinIn | digitalPinsOut | digitalPinsIn | servo'", _currentToken);
            return null;
      }
   }
      
/*
   private Function parseFunction()
   throws ScannerException
   {
      match(TokenCode.TK_FN);
      String id = matchIdentifier();
      match(TokenCode.TK_LPAREN);
      Parameters params = parseParameters();
      match(TokenCode.TK_RPAREN);
      Declarations decls = parseDeclarations();
      Statement s = parseCompoundStatement();

      return new Function(id, params, decls, s);
   }

   private Parameters parseParameters()
   throws ScannerException
   {
      Parameters params = new Parameters();
      if (isParameter(_currentToken))
      {
         params.add(parseParameter());
         while (_currentToken.equals(TokenCode.TK_COMMA))
         {
            match(TokenCode.TK_COMMA);
            params.add(parseParameter());
         }
      }

      return params;
   }

   private Declaration parseParameter()
   throws ScannerException
   {
      String id = matchIdentifier();
      return new Declaration(id);
   }
*/

   private Statement parseIfStatement() throws ScannerException
   {
      match(TokenCode.TK_IF);
      Expression guard = parseExpression();
      Statement[] body = parseBlockStatement();
      Statement[] elseBody = new Statement[0];
      if (_currentToken.code() == TokenCode.TK_ELSE)
      {
         match(TokenCode.TK_ELSE);
         elseBody = parseBlockStatement();
      }
      return new IfStatement(guard, body, elseBody);
   }

   private Statement parseStatement() throws ScannerException
   {
      switch (_currentToken.code())
      {
         case TK_REPEAT:
            return parseRepeat();
         case TK_DISPLAY:
            return parseDisplay();
         case TK_MOVE:
            return parseMove();
         case TK_TURN:
            return parseTurn();
         case TK_ROTATE:
            return parseRotate();
         case TK_SLEEP:
            return parseSleep();
         case TK_IF:
            return parseIfStatement();
         case TK_TURNON:
            return parseTurnOnStatement();
         case TK_TURNOFF:
            return parseTurnOffStatement();
         case TK_VARIABLE:
            return parseVariableDeclaration();
         case TK_ID:
         case TK_NUM:
         case TK_STRING:
            return new ExpressionStatement(parseExpression());

         default:
            expected("'statement'", _currentToken);
            return null;
      }
   }

   private Statement parseTurnOnStatement() throws ScannerException
   {
      match(TokenCode.TK_TURNON);
      String id = matchIdentifier();
      return new TurnOnStatement(id);
   }

   private Statement parseTurnOffStatement() throws ScannerException
   {
      match(TokenCode.TK_TURNOFF);
      String id = matchIdentifier();
      return new TurnOffStatement(id);
   }

   private Statement parseVariableDeclaration() throws ScannerException
   {
      match(TokenCode.TK_VARIABLE);
      String id = matchIdentifier();
      match(TokenCode.TK_ASSIGN);
      Expression assignment = parseExpression();
      return new VariableDeclarationStatement(id, assignment);
   }


   private TokenCode matchDirection() throws ScannerException
   {
      switch(_currentToken.code()) {
         case TK_FWD:
            match(TokenCode.TK_FWD);
            return TokenCode.TK_FWD;
         case TK_BKWD:
            match(TokenCode.TK_BKWD);
            return TokenCode.TK_BKWD;
         case TK_LEFT:
            match(TokenCode.TK_LEFT);
            return TokenCode.TK_LEFT;
         case TK_RIGHT:
            match(TokenCode.TK_RIGHT);
            return TokenCode.TK_RIGHT;
         default:
            expected("'direction'", _currentToken);
            return null;
      }
   }

   private Statement[] parseBlockStatement() throws ScannerException
   {
      List<Statement> statements = new ArrayList<Statement>();
      match(TokenCode.TK_LBRACE);
      while (_currentToken.code() != TokenCode.TK_RBRACE)
      {
         statements.add(parseStatement());
      }
      match(TokenCode.TK_RBRACE);
      return statements.toArray(new Statement[statements.size()]);
   }


   private Statement parseRepeat() throws ScannerException
   {
      match(TokenCode.TK_REPEAT);
      Expression repeatTimes = null;
      if (_currentToken.code() != TokenCode.TK_LBRACE)
      {
         repeatTimes = parseExpression();
         match(TokenCode.TK_TIMES);
      }
      return new RepeatStatement(repeatTimes, parseBlockStatement());
   }

   private Statement parseDisplay() throws ScannerException
   {
      match(TokenCode.TK_DISPLAY);
      Expression e;
      boolean finishParse = false;
      boolean readyInput = true;
      List<Expression> elements = new ArrayList<Expression>();
      while (!finishParse)
      {
         switch (_currentToken.code())
         {
            case TK_STRING:
               if (readyInput)
               {
                  elements.add(new StringExpression(_currentToken.toString()));
                  nextToken();
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_NUM:
               if (readyInput)
               {
                  elements.add(new IntegerConstantExpression(Integer.valueOf(_currentToken.toString())));
                  nextToken();
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_FLOAT:
               if (readyInput)
               {
                  elements.add(new FloatConstantExpression(Float.valueOf(_currentToken.toString())));
                  nextToken();
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_ID:
               if (readyInput)
               {
                  elements.add(new IdentifierExpression(_currentToken.toString()));
                  nextToken();
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_LPAREN:
               if (readyInput)
               {
                  nextToken();
                  elements.add(parseExpression());
                  match(TokenCode.TK_RPAREN);
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_PLUS:
               if (!readyInput)
               {
                  readyInput = true;
                  nextToken();
                  break;
               }
               else
               {
                  expected("'printable element'", _currentToken);
                  return null;
               }
            default:
               finishParse = true;
               break;
         }
      }
      DisplayStatement s = new DisplayStatement(elements);
      return s;
   }

   /*
   private Statement parseDisplay() throws ScannerException
   {
      match(TokenCode.TK_DISPLAY);
      Expression e;
      boolean finishParse = false;
      boolean readyInput = true;
      Vector<String> args = new Vector<String>();
      StringBuilder format = new StringBuilder("\"");
      while (!finishParse)
      {
         switch (_currentToken.code())
         {
            case TK_STRING:
               if (readyInput)
               {
                  format.append("%s");
                  args.add("\"" + _currentToken.toString() + "\"");
                  nextToken();
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_NUM:
            case TK_ID:
               if (readyInput)
               {
                  format.append("%d");
                  args.add(_currentToken.toString());
                  nextToken();
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_LPAREN:
               if (readyInput)
               {
                  nextToken();
                  e = parseExpression();
                  match(TokenCode.TK_RPAREN);
                  format.append("%d");
                  args.add(e.visit(new ASTStringBuilderVisitor()).toString());
                  readyInput = false;
               }
               else
               {
                  finishParse = true;
               }
               break;
            case TK_PLUS:
               if (!readyInput)
               {
                  readyInput = true;
                  nextToken();
                  break;
               }
               else
               {
                  expected("'printable element'", _currentToken);
                  return null;
               }
            default:
               finishParse = true;
               break;
         }
      }
      format.append("\"");
      DisplayStatement s = new DisplayStatement(format.toString(), args);
      return s;
   }
   */

   private Statement parseMove() throws ScannerException
   {
      match(TokenCode.TK_MOVE);
      TokenCode dir = matchDirection();
      switch (dir) {
         case TK_FWD:
            return new ForwardStatement(parseExpression());
         case TK_BKWD:
            return new BackwardStatement(parseExpression());
         default:
            expected("'forward or backard'", _currentToken);
            return null;
      }
   }

   private Statement parseTurn() throws ScannerException
   {
      match(TokenCode.TK_TURN);
      TokenCode dir = matchDirection();
      switch (dir)
      {
         case TK_LEFT:
            return new LeftStatement();
         case TK_RIGHT:
            return new RightStatement();
         default:
            expected("'left or right'", _currentToken);
            return null;
      }
   }

   private Statement parseRotate() throws ScannerException
   {
      match(TokenCode.TK_ROTATE);
      String id = matchIdentifier();
      Machinery m = parseMachinery(id);
      Expression exp = parseExpression();
      return new RotateStatement(m, exp);
   }

   private Statement parseSleep() throws ScannerException
   {
      match(TokenCode.TK_SLEEP);
      return new SleepStatement(parseExpression());
   }

   private Statement parseState() throws ScannerException
   {
      String id = matchIdentifier();
      switch (_currentToken.code()) {
         case TK_TURNON:
            return new OnStatement(id);
         case TK_TURNOFF:
            return new OffStatement(id);
         default:
            expected("'on' or 'off'", _currentToken);
            return null;
      }
   }

   private Statement parseCompoundStatement() throws ScannerException
   {
      Vector<Statement> stmts = new Vector<Statement>();
      match(TokenCode.TK_LBRACE);
      while (isStatement(_currentToken))
      {
         stmts.add(parseStatement());
      }
      match(TokenCode.TK_RBRACE);
      return new CompoundStatement(stmts);
   }

   private Expression parseExpression() throws ScannerException
   {
       Expression e = parseAssignExpression();
       return parseRptExpression(e);
   }

   private Expression parseRptExpression(Expression lft)
      throws ScannerException {
         if (_currentToken.equals(TokenCode.TK_COMMA)) {
            match(TokenCode.TK_COMMA);
            Expression rht = parseAssignExpression();
            return parseRptExpression(new CommaExpression(lft, rht));
         }
         else {
            return lft;
         }
      }

   private Expression parseAssignExpression()
      throws ScannerException {
         Expression lft = parseConditionalExpression();
         return parseRptAssignExpression(lft);
      }

   private Expression parseRptAssignExpression(Expression lft)
      throws ScannerException {
         if (_currentToken.equals(TokenCode.TK_ASSIGN)) {
            match(TokenCode.TK_ASSIGN);
            Expression rht = parseAssignExpression();
            return parseRptAssignExpression(new AssignmentExpression(lft, rht));
         }
         else {
            return lft;
         }
      }

    private Expression parseConditionalExpression()
	throws ScannerException {
        return parseLogicalORExpression();
    }

   private Expression parseLogicalORExpression() 
    throws ScannerException {
      Expression e = parseLogicalAndExpression();
      return parseRptLogicalOrExpression(e);
   }

   private Expression parseRptLogicalOrExpression(Expression lft)
    throws ScannerException{
      if (_currentToken.equals(TokenCode.TK_OR)) {
         match(TokenCode.TK_OR);
         Expression e = parseLogicalAndExpression();
         return parseRptLogicalOrExpression(new OrExpression(lft, e));
      }
      else {
         return lft;
      }
   }

   private Expression parseLogicalAndExpression() throws ScannerException {
      Expression e = parseEqualityExpression();
      return parseRptLogicalAndExpression(e);
   }

   private Expression parseRptLogicalAndExpression (Expression lft) 
    throws ScannerException {
      if (_currentToken.equals(TokenCode.TK_AND)) {
         match(TokenCode.TK_AND);
         Expression e = parseEqualityExpression();
         return parseRptLogicalAndExpression(new AndExpression(lft, e));
      }
      else {
         return lft;
      }
   }

   private Expression parseEqualityExpression() 
    throws ScannerException {
      Expression e = parseRelationalExpression();
      return parseRptEqualityExpression(e);
   }

   private Expression parseRptEqualityExpression(Expression lft) 
    throws ScannerException {
      TokenCode token = _currentToken.code();
      if (isEqOp(_currentToken)) {
         nextToken();
         Expression e = parseEqualityExpression();

         switch(token) {
            case TK_EQ:
               return parseRptEqualityExpression(new EqualExpression(lft, e));
            case TK_NE:
               return parseRptEqualityExpression(new NotEqualExpression(lft, e));
         }

      }
      else {
         return lft;
      }
      return lft;
   }

   private Expression parseRelationalExpression() 
    throws ScannerException {
      Expression e = parseAdditiveExpression();
      return parseRptRelationalExpression(e);
   }

   private Expression parseRptRelationalExpression(Expression lft) 
    throws ScannerException {
      TokenCode token = _currentToken.code();
      if (isRelOp(_currentToken)) {
         nextToken();
         Expression e = parseAdditiveExpression();
         switch(token) {
            case TK_LT:
               return parseRptRelationalExpression(new LessThanExpression(lft, e));
            case TK_GT:
               return parseRptRelationalExpression(new GreaterThanExpression(lft, e));
            case TK_LE:
               return parseRptRelationalExpression(new LessEqualExpression(lft, e));
            case TK_GE:
               return parseRptRelationalExpression(new GreaterEqualExpression(lft, e));
         }

      }
      else {
         return lft;
      }
      return lft;
   }

   private Expression parseAdditiveExpression() throws ScannerException
   {
      Expression e = parseMultiplicativeExpression();
      return parseRptAddExpression(e);
   }

   private Expression parseRptAddExpression(Expression lft) throws ScannerException
   {
      TokenCode token = _currentToken.code();
      if(isAddOp(_currentToken)) {
         nextToken();

         Expression e = parseMultiplicativeExpression();
         switch(token) {
            case TK_PLUS:
               return parseRptAddExpression(new AddExpression(lft, e));
            case TK_MINUS:
               return parseRptAddExpression(new SubtractExpression(lft, e));
         }

      }
      else {
         return lft;
      }
      return lft;
   }

   private Expression parseMultiplicativeExpression() throws ScannerException
   {
      Expression e = parseUnaryExpression();
      return parseRptMultExpression(e);
   }

   private Expression parseRptMultExpression(Expression lft) throws ScannerException
   {
      TokenCode token = _currentToken.code();

      if (isMultOp(_currentToken)) {
         nextToken();
         Expression e = parseUnaryExpression();

         switch(token) {
            case TK_MULT:
               return parseRptMultExpression(new MultiplyExpression(lft, e));
            case TK_DIVIDE:
               return parseRptMultExpression(new DivideExpression(lft, e));
         }

      }
      else {
         return lft;
      }
      return lft;
   }

   private Expression parseUnaryExpression() throws ScannerException
   {
      if (isUnaryOp(_currentToken)) {
         match(TokenCode.TK_NOT);
         return new NotExpression(parseLeftHandSideExp());
      }
      else
      {
         return parseLeftHandSideExp();
      }

   }

   private Expression parseLeftHandSideExp() throws ScannerException
   {
      return parsePrimaryExpression();
   }

   private Expression parsePrimaryExpression() throws ScannerException
   {
      Expression e = null;
      if (_currentToken.code() == TokenCode.TK_LPAREN) {
         match(TokenCode.TK_LPAREN);
         e = parseExpression();
         match(TokenCode.TK_RPAREN);
      }
      else {
         switch(_currentToken.code()) {
            case TK_STRING:
               e = new StringExpression(_currentToken.toString());
               break;
            case TK_NUM:
               e = new IntegerConstantExpression(Integer.parseInt(_currentToken.toString()));
               break;
            case TK_FLOAT:
               e = new FloatConstantExpression(Float.parseFloat(_currentToken.toString()));
               break;
            case TK_ID:
               e = new IdentifierExpression(_currentToken.toString());
               break;
         }
         nextToken();
      }
      return e;
   }

   private Expression parseBoolTerm() throws ScannerException
   {
      Expression e = parseSimple();
      if (isRelOp(_currentToken))
      {
         Token tk = parseBinaryOp();
         e = newBinaryOp(tk, e, parseSimple());
      }
      return e;
   }

   private Expression parseSimple() throws ScannerException
   {
      Expression e = parseTerm();
      while (isAddOp(_currentToken))
      {
         Token tk = parseBinaryOp();
         e = newBinaryOp(tk, e, parseTerm());
      }
      return e;
   }

   private Expression parseTerm() throws ScannerException
   {
      Expression e = parseUnary();
      while (isMultOp(_currentToken))
      {
         Token tk = parseBinaryOp();
         e = newBinaryOp(tk, e, parseUnary());
      }

      return e;
   }

   private Expression parseUnary() throws ScannerException
   {
      Expression e;
      switch (_currentToken.code())
      {
         case TK_NOT:
            e = parseUnaryNot();
            break;
         default:
            e = parseFactor();
            break;
      }
      return e;
   }

   private Expression parseUnaryNot() throws ScannerException
   {
      match(TokenCode.TK_NOT);
      return new NotExpression(parseFactor());
   }

   private Expression parseFactor() throws ScannerException
   {
      Expression e;
      switch (_currentToken.code())
      {
         case TK_LPAREN:
            match(TokenCode.TK_LPAREN);
            e = parseExpression();
            match(TokenCode.TK_RPAREN);
            break;
         case TK_ID:
            String id = matchIdentifier();
            if (_currentToken.equals(TokenCode.TK_LPAREN))
            {
               match(TokenCode.TK_LPAREN);
               Vector<Expression> args = parseArguments();
               match(TokenCode.TK_RPAREN);
               e = new InvocationExpression(id, args);
            }
            else
            {
               e = new IdentifierExpression(id);
            }
            break;
         case TK_NUM:
            e = new IntegerConstantExpression(
                  Integer.parseInt(_currentToken.toString()));
            nextToken();
            break;
            /*
               case TK_TRUE:
               e = new BooleanConstantExpression(true);
               nextToken();
               break;
               case TK_FALSE:
               e = new BooleanConstantExpression(false);
               nextToken();
               break;
            */
         default:
            expected("'id or value'", _currentToken);
            e = null;
      }
      return e;
   }

   private Vector<Expression> parseArguments() throws ScannerException
   {
      Vector<Expression> args = new Vector<Expression>();

      if (isExpression(_currentToken))
      {
         args.add(parseExpression());

         while (_currentToken.equals(TokenCode.TK_COMMA))
         {
            match(TokenCode.TK_COMMA);
            args.add(parseExpression());
         }
      }

      return args;
   }

   private static boolean isBoolOp(Token tk)
   {
      return tk.equals(TokenCode.TK_AND) || tk.equals(TokenCode.TK_OR);
   }

   private static boolean isEqOp(Token tk)
   {
      return tk.equals(TokenCode.TK_EQ) || tk.equals(TokenCode.TK_NE);
   }

   private static boolean isRelOp(Token tk)
   {
      return 
         tk.equals(TokenCode.TK_LT) ||
         tk.equals(TokenCode.TK_GT) ||
         tk.equals(TokenCode.TK_LE) ||
         tk.equals(TokenCode.TK_GE);
   }

   private static boolean isAddOp(Token tk)
   {
      return tk.equals(TokenCode.TK_PLUS) || tk.equals(TokenCode.TK_MINUS);
   }

   private static boolean isMultOp(Token tk)
   {
      return tk.equals(TokenCode.TK_MULT) || tk.equals(TokenCode.TK_DIVIDE);
   }

   private static boolean isUnaryOp(Token tk) {
      return tk.equals(TokenCode.TK_NOT);
   }

   private Token parseBinaryOp() throws ScannerException
   {
      Token tk = _currentToken;
      nextToken();
      return tk;
   }

   private static Expression newBinaryOp(Token tk,
         Expression lft, Expression rht)
   {
      Expression e = null;
      switch (tk.code())
      {
         case TK_AND:
            e = new AndExpression(lft, rht);
            break;
         case TK_OR:
            e = new OrExpression(lft, rht);
            break;
         case TK_EQ:
            e = new EqualExpression(lft, rht);
            break;
         case TK_LT:
            e = new LessThanExpression(lft, rht);
            break;
         case TK_GT:
            e = new GreaterThanExpression(lft, rht);
            break;
         case TK_NE:
            e = new NotEqualExpression(lft, rht);
            break;
         case TK_LE:
            e = new LessEqualExpression(lft, rht);
            break;
         case TK_GE:
            e = new GreaterEqualExpression(lft, rht);
            break;
         case TK_PLUS:
            e = new AddExpression(lft, rht);
            break;
         case TK_MINUS:
            e = new SubtractExpression(lft, rht);
            break;
         case TK_MULT:
            e = new MultiplyExpression(lft, rht);
            break;
         case TK_DIVIDE:
            e = new DivideExpression(lft, rht);
            break;
      }
      return e;
   }

   /************************
     auxiliary methods
    ************************/
   private static boolean isStatement(Token tk)
   {
      return tk.equals(TokenCode.TK_ID)
         || tk.equals(TokenCode.TK_DISPLAY)
         || tk.equals(TokenCode.TK_LBRACE)
         || tk.equals(TokenCode.TK_IF)
         || tk.equals(TokenCode.TK_REPEAT)
         || tk.equals(TokenCode.TK_MOVE)
         || tk.equals(TokenCode.TK_TURNON)
         || tk.equals(TokenCode.TK_TURNOFF)
         || tk.equals(TokenCode.TK_ROTATE)
         || tk.equals(TokenCode.TK_SLEEP)
         ;
   }

   private static boolean isExpression(Token tk)
   {
      return tk.equals(TokenCode.TK_NOT)
         || tk.equals(TokenCode.TK_LPAREN)
         || tk.equals(TokenCode.TK_ID)
         || tk.equals(TokenCode.TK_NUM)
         || tk.equals(TokenCode.TK_STRING)
         ;
   }

   private static boolean isParameter(Token tk)
   {
      return tk.equals(TokenCode.TK_ID);
   }

   private void ungetToken(Token t)
   {
      _scanner.ungetToken(_currentToken);
      _currentToken = t;
   }

   private void nextToken() throws ScannerException
   {
      _currentToken = _scanner.nextToken();
   }

   /************************
     expectation methods
    ************************/
   private String matchIdentifier() throws ScannerException
   {
      if (!_currentToken.equals(TokenCode.TK_ID))
      {
         expected("'identifier'", _currentToken);
      }
      String id = _currentToken.toString();
      nextToken();
      return id;
   }

   private void match(TokenCode code) throws ScannerException
   {
      if (!_currentToken.equals(code))
      {
         expected("'" + code.concreteString() + "'", _currentToken);
      }
      nextToken();
   }

   /************************
     error handling
    ************************/
   private static void expected(String msg)
   {
      error("expected " + msg);
   }
   private static void expected(String msg, Token tk)
   {
      error("expected " + msg + ", found '" + tk + "'");
   }

   private static void error(String msg)
   {
      System.err.println(msg);
      System.exit(1);
   }
}
