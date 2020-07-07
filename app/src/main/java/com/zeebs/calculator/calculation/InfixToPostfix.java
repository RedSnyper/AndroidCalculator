package com.zeebs.calculator.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InfixToPostfix {



    public static final int precedence (String operatorPrecedence) {
        switch (operatorPrecedence) {
            case "+":
            case "-":
                return 1;
            case "/":
            case "*":
                return 2;
        }
        return - 1;
    }

    public static List infixToPostfix (List<String> expressionForComputer) {

        // this.splitExpression(expression);
       List<String> infixToPostfix = new ArrayList<>();      // for getting the postfix operation. Used arraylist to separate every string expression properly.
        Stack<String> operatorStack = new Stack<>();         // for adding numbers in the stack
        String value;

        for (int i = 0; i < expressionForComputer.size(); i++) {
            value = (String) expressionForComputer.get(i);


            if (value.matches("-?\\d+|-?\\d+\\.\\d+")) //matches positive numbers or -x....
            {
                infixToPostfix.add(value);
            }
            else if (value.equals("(")) {
                operatorStack.push(value);
            }
            else if (value.equals(")")) // pops up all operators in the stack till ( is not reached
            {
                while (! operatorStack.isEmpty() && ! operatorStack.peek().equals("(")) {
                    infixToPostfix.add(operatorStack.pop());
                }
                if (! operatorStack.isEmpty() && ! operatorStack.peek().equals("(")) //--------handle an exception here!!---------//
                {
                    System.out.println("error in the stack. Type mismatch maybe here 1");
                    // return null;
                }
                else
                    operatorStack.pop();
            }
            else {
                while (!operatorStack.isEmpty() && precedence(value) <= precedence(operatorStack.peek())) {
                    if (operatorStack.peek().equals("(")) // for cases with mismatch in bracket numbers
                    {
                        System.out.println("error in the stack. Type mismatch maybe here 2");
                        //   return null;
                    }
                    infixToPostfix.add(operatorStack.pop());
                }
                operatorStack.push(value);
            }

        }
        while (! operatorStack.isEmpty()) {
            infixToPostfix.add(operatorStack.pop());
        }
        System.out.println("infix to postifx" + infixToPostfix.toString());
        return infixToPostfix;
    }

}
