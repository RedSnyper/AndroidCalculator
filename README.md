# AndroidCalculator

## Andriod calculator implementing infix to postfix notation.

Default calculation for trigonometry function is in Radian.

Consecutive operators not allowed(except for minus).

Equals checks on valid expression before transferring result to expression viewplace to work on it further. A
valid expression includes no operators in the beginning or end. Bracket count checking is not done but () is invalid.

On each operand(numbers) pressed, the expression is calculated to give instantaneous result. It works by 
calling the Evaluator.evaluate fn:

-Evaluator.evaluate(StringBuilder expression, Boolean isRad) is the main calling function for calculation. 
isRad can be changed on button press and the expression is recalculated on every button press for presenting 
the result in degree or radians (users will be notified by a toast message on what angular measurement they are 
working on). 

-The Stringbuilder expression is passed to the SplitExpression class where the expression is split based on 
negative/decimal/normal numbers,e,pi, operators, trig and log fn and stored in an arraylist. A value of 1E10
is not split.  

-The aformentioned arraylist is passed to ExpressionParser class which is responsible for making the expressison
into computer readable format. This includes 

        - changing of e, pi to their respective values (precision to 9 decimals hardcoded).

        - adding '*' in expressions like 4(5), (5)4, (-(4)) to make it 4*(5), (5)*4, (-1*(4)) respecitvely.

        - adding a new operator '$' in case of trig and log fns like sin(30) is converted to sin$(30).

The computer readable expression is stored in a new arraylist and is passed to another class. 

-The parsed expression is passed to InfixToPostfix class which follows **infix to postfix** algorithm
to convert infix expression to postfix . 

            Precedence of operator(in descending order) is as 
            - $, !, sqrt, ^, (/,*), (+,-)  (operators under brackets have the same precedence). 
            
The postfix notation is stored in stack which is passed onto Calculate class 

-The Calculate class calculates operands based on operators in **reverse polish notation** fashion and passes the result to Evaluator class.

-The Evaluator class receives the result and formats it (formatting pattern "###,###.#######" and E notation if output length>=20)
and passes the result to the app for display.
   
