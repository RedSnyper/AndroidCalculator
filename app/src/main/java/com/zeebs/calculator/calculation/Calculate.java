package com.zeebs.calculator.calculation;



import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Calculate {

    public static String result(List<String> infixToPostfix) throws ArithmeticException, EmptyStackException
    {
        Stack<Double> operand = new Stack<>();
        Double operand1;
        Double operand2;
        Double result;
        String stringResult;

        try {
            for (int i = 0; i < infixToPostfix.size(); i++) {


                String getCurrentValue = (String) infixToPostfix.get(i);

                if (getCurrentValue.matches("-?\\d+|-?\\d+\\.\\d+"))  // for all numbers : negative or positive
                {
                    operand.push(Double.parseDouble(getCurrentValue));
                } else if (getCurrentValue.equals("+")) {
                    operand2 = operand.pop();
                    operand1 = operand.pop();
                    result = operand1 + operand2;
                    operand.push(result);

                } else if (getCurrentValue.equals("-")) {

                    operand2 = operand.pop();
                    operand1 = operand.pop();
                    result = operand1 - operand2;
                    operand.push(result);

                } else if (getCurrentValue.equals("*")) {
                    operand2 = operand.pop();
                    operand1 = operand.pop();
                    result = operand1 * operand2;
                    operand.push(result);

                } else if (getCurrentValue.equals("/")) {
                    operand2 = operand.pop();
                    operand1 = operand.pop();
                    result = operand1 / operand2;
                    operand.push(result);
                }

            }
        }catch (ArithmeticException e)
        {
            System.out.println("catching any ?");
        }catch (EmptyStackException e)
        {
            System.out.println("Supposed empty stack exception lol");
            return "";
        }
        result = operand.pop();

        if(result%1==0)
        {
            int val = result.intValue();
            stringResult = Integer.toString(val);
        }
        else
            stringResult= Double.toString(result);

        return stringResult;
    }
}

