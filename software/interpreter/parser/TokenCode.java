package parser;

import java.util.Map;
import java.util.HashMap;
import java.util.EnumSet;
//do we want to add support for '%'??
public enum TokenCode
{
   TK_DISPLAY("display"),
   TK_WHEN("when"),
   TK_NOT("not"),
   TK_DEFINE("define"),
   TK_VARIABLE("var"),
   TK_ANALOGPININ("analogIn"),
   TK_DIGITALPINSOUT("digitalOut"),
   TK_DIGITALPINSIN("digitalIn"),
   TK_ACCELEROMETER("accel"),
   TK_SERVO("servo"),
   TK_BUTTON("button"),
   TK_LED("led"),
   TK_MOTOR("motor"),
   TK_START("start"),
   TK_GET("get"),
   TK_SET("set"),
   TK_FUNC("func"),
   TK_RETURN("return"),
   TK_BETWEEN("between"),
   TK_CHANGES("changes"),
   TK_CHANGE("change"),
   TK_MODE("mode"),
   TK_IN("in"),
   TK_STOP("stop"),
   TK_OUT("out"),
   TK_CLEAR("clear"),
   TK_SCREEN("screen"),
   TK_CURSOR("cursor"),
   TK_IF("if"),
   TK_TURN("turn"),
   TK_ELSE("else"),
   TK_SLEEP("sleep"),
   TK_REPEAT("repeat"),
   TK_TIMES("times"),
   TK_MOVE("move"),
   TK_FWD("forward"),
   TK_BKWD("backward"),
   TK_LEFT("left"),
   TK_RIGHT("right"),
   TK_LBRACE("{"),
   TK_RBRACE("}"),
   TK_LBRACKET("["),
   TK_RBRACKET("]"),
   TK_LPAREN("("),
   TK_RPAREN(")"),
   TK_COMMA(","),
   TK_AND("and"),
   TK_OR("or"),
   TK_EQ("equals"),
   TK_EQ2("eq"),
   TK_NE("notequals"),
   TK_NE2("noteq"),
   TK_ASSIGN("="),
   TK_LT("<"),
   TK_GT(">"),
   TK_LE("<="),
   TK_GE(">="),
   TK_PLUS("+"),
   TK_MINUS("-"),
   TK_PLUSPLUS("++"),
   TK_MINUSMINUS("--"),
   TK_PLUSEQ("+="),
   TK_MINUSEQ("-="),
   TK_MULTEQ("*="),
   TK_DIVEQ("/="),
   TK_MOD("%"),
   TK_MULT("*"),
   TK_DIVIDE("/"),
   TK_NUM("-- number --"),
   TK_FLOAT("-- number --"),
   TK_ID("-- id --"),
   TK_STRING("-- string --"),
   TK_EOF("eof"),
   TK_NONE("<*none*>");


   TokenCode(String s)
   {
      _s = s;
   }

   public String concreteString()
   {
      return _s;
   }

   private static TokenCode lookup(String s, Map<String,TokenCode> map)
   {
      if (map.containsKey(s))
      {
         return map.get(s);
      }
      else
      {
         return TK_NONE;
      }
   }

   public static TokenCode lookupKeyword(String s)
   {
      return lookup(s, _keywords);
   }

   public static TokenCode lookupSymbol(String s)
   {
      return lookup(s, _symbols);
   }

   private static final TokenCode _firstKeyword = TK_DISPLAY;
   private static final TokenCode _lastKeyword = TK_NONE;

   private static final Map<String,TokenCode> _keywords =
      new HashMap<String,TokenCode>();
   static
   {
      for (TokenCode code : EnumSet.range(_firstKeyword, _lastKeyword))
      {
         _keywords.put(code.concreteString(), code);
      }
   }

   private static final TokenCode _firstSymbol = TK_LBRACE;
   private static final TokenCode _lastSymbol = TK_DIVIDE;
   private static final Map<String,TokenCode> _symbols =
      new HashMap<String,TokenCode>();
   static
   {
      for (TokenCode code : EnumSet.range(_firstSymbol, _lastSymbol))
      {
         _symbols.put(code.concreteString(), code);
      }
   }

   private String _s;
}
