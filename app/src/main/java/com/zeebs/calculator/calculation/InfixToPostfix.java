package com.zeebs.calculator.calculation;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class InfixToPostfix {



    static final int precedence (String operatorPrecedence) {
        switch (operatorPrecedence) {
            case "+":
            case "-":
                return 1;

            case "/":
            case "*":
                return 2;

            case "^":
                return 3;

            case "\u221A":      //square root
                return 4;

            case "!":
                return 5;

            case "$":       // for trig and log functions.
                return 6;
        }
        return - 1;
    }

    public static List infixToPostfix (List<String> expressionForComputer) throws Exception {

        // this.splitExpression(expression);
        List<String> infixToPostfix = new ArrayList<>();      // for getting the postfix operation. Used arraylist to separate every string expression properly.
        Stack<String> operatorStack = new Stack<>();         // for adding numbers in the stack
        String value;

        try {
            for (int i = 0; i < expressionForComputer.size (); i++) {
                value = (String) expressionForComputer.get (i);


                if ( value.matches ("-?\\d+|-?\\d+\\.\\d+|\\w+[a-z]|-?\\.\\d+|-?\\d+\\.[E]\\d+") ) //matches positive numbers or -x.... or 1E10
                {
                    System.out.println("matched at " + value);
                    infixToPostfix.add (value);
                }
                else if ( value.equals ("(") ) {
                    operatorStack.push (value);
                }
                else if ( value.equals (")") ) // pops up all operators in the stack till ( is not reached
                {
                    while (!operatorStack.isEmpty () && !operatorStack.peek ().equals ("(")) {
                        infixToPostfix.add (operatorStack.pop ());
                    }
                    if ( !operatorStack.isEmpty () && !operatorStack.peek ().equals ("(") ) //--------handle an exception here!!---------//
                    {
                        return null;
                    }
                    else
                        operatorStack.pop ();
                }
                else {

                    if(value.equals ("\u221a") && i!=0 )     // for cases having multiple square roots without brackets e.g. sqrt sqrt sqrt 420
                    {
                        if(operatorStack.peek().equals ("\u221a"))
                        {

                        }
                        // push to operator stack and not in infixToPostfix!!
                    }else {
                        while (!operatorStack.isEmpty () && precedence (value) <= precedence (operatorStack.peek ())) {
                            if ( operatorStack.peek ().equals ("(") ) // for cases with mismatch in bracket numbers
                            {
                                return null;
                            }
                            infixToPostfix.add (operatorStack.pop ());
                        }
                    }
                    operatorStack.push (value);
                }

            }
            while (!operatorStack.isEmpty ()) {
                infixToPostfix.add (operatorStack.pop ());
            }
            return infixToPostfix;
        }catch (EmptyStackException e)
        {
            throw new BadExpressionException ("Bad Expression");
        }
    }

}
