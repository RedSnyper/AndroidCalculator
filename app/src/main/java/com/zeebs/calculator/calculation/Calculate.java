package com.zeebs.calculator.calculation;

import java.util.List;
import java.util.Stack;

public class Calculate {

    public static String result(List<String> infixToPostfix)
    {
        Stack<Double> operand = new Stack<>();
        Double operand1;
        Double operand2;
        Double result;
        String resultString;

        for (int i = 0; i < infixToPostfix.size(); i++) {
            String getCurrentValue = (String) infixToPostfix.get(i);

            if (getCurrentValue.matches("-?\\d+|-?\\d+\\.\\d+"))  // for all numbers : negative or positive
            {
                operand.push(Double.parseDouble(getCurrentValue));
            }

            else if (getCurrentValue.equals("+")) {
                operand2 = operand.pop();
                operand1 = operand.pop();
                result = operand1 + operand2;
                operand.push(result);

            }
            else if (getCurrentValue.equals("-")) {

                operand2 = operand.pop();
                operand1 = operand.pop();
                result = operand1 - operand2;
                operand.push(result);

            }
            else if (getCurrentValue.equals("*")) {
                operand2 = operand.pop();
                operand1 = operand.pop();
                result = operand1 * operand2;
                operand.push(result);

            }
            else if (getCurrentValue.equals("/")) {
                operand2 = operand.pop();
                operand1 = operand.pop();
                result = operand1 / operand2;
                operand.push(result);

            }
        }

        result = operand.pop();
        resultString =""+result;
        return resultString;
    }

}
