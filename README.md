arduino_robot
=============

The following is a
Program -> SourceElement*
SourceElement -> DefineDeclaration | Statement | WhenStatement | Function
Statement -> RepeatStatement | DisplayStatement | MoveStatement |
 SetStatement | SleepStatement | IfStatement |
 VariableDeclaration
Function -> func id(Parameters)
Parameters -> variable id {, variable id}*opt
SetStatement -> set id expression
variableDeclaration -> variable id = assignmentExpression
defineDeclaration -> define variableDeclaration
variableDeclaration -> id = machinery
machinery -> analogPin[expresion] | digitalPinsOut[expression, expression] | digitalPinsIn[expression, expression] | servo[expression] | motor[expression]

ifStatement -> if expression blockStatement {else blockStatement}opt

whenStatement -> when {start | whenCondition} blockStatement
whenCondition -> whenOrExpression
whenOrExpression -> whenAndExpression { or whenAndExpression}*opt
whenAndExpression -> eventExpression { and eventExpression}*opt


eventExpression -> changesExpression | equalityWhenExpression | 
                   betweenExpression
betweenExpression -> expression between expression and expression
equalityWhenExpression -> equalityExpression
changesExpression -> id changes
expression -> assignmentExpression {, assignmentExpression}
assignmentExpression -> conditionalExpression {= assignmentExpression}
conditionalExpression -> logicalORExpression
logicalORExpression -> logicalANDExpression { or logicalANDExpresion }*opt
logicalANDExpression -> equalityExpression { and equalityExpression }*opt
equalityExpression -> relationalExpression { eqOp relationalExpression }opt
relationalExpression -> additiveExpression { relOp additiveExpression }opt
additiveExpression -> multiplicativeExpression {addOp
     multiplicativeExpression}opt
multiplicativeExpression -> unaryExpression { multOp unaryExpression }opt
unaryExpression -> {unaryOP}opt leftHandSideExpression
leftHandSideExpression -> callExpression
/* Hum, we have to decide where to put the getExpression */
callExpression -> getExpression | primaryExpression
getExpression -> get id
primaryExpression -> ( expression ) | string | number | id | getExpression

eqOp -> = | not =
relOp -> < | > | >=  | <=
unaryOP -> not
sleepStatement -> sleep expression
blockStatement -> { statement* } 
repeatStatement -> repeat {expression times}opt blockStatement
moveStatement -> forwardStatement | backwardStatement | leftStatement |
    rightStatement
forwardStatement -> move forward expression
backwardStatement -> move backward expression
leftStatement -> turn left
rightStatement -> turn right
displayStatement -> display expression

addOp -> + | -
multOp -> * | /

