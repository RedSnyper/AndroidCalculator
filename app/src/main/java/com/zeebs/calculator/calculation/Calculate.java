package com.zeebs.calculator.calculation;



import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Calculate {

    public static String result(List<String> infixToPostfix) throws ArithmeticException, EmptyStackException
    {
        Stack<Double> operand = new Stack<>();
        double operand1;
        double operand2;
        double res;
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
                    res = operand1 + operand2;
                    operand.push(res);

                } else if (getCurrentValue.equals("-")) {

                    operand2 = operand.pop();
                    operand1 = operand.pop();
                    res = operand1 - operand2;
                    operand.push(res);

                } else if (getCurrentValue.equals("*")) {
                    operand2 = operand.pop();
                    operand1 = operand.pop();
                    res = operand1 * operand2;
                    operand.push(res);

                } else if (getCurrentValue.equals("/")) {
                    operand2 = operand.pop();
                    operand1 = operand.pop();
                    res = operand1 / operand2;
                    operand.push(res);
                }

            }
        }catch (ArithmeticException e)
        {
            return "Cant divde by zero";
        }catch (EmptyStackException e)
        {

            return "";
        }
        result = operand.pop();

        if(result%1==0)
        {
            Integer val = result.intValue();

            if(val>=Integer.MAX_VALUE)
            {
                stringResult = Double.toString(result);

            }else
            stringResult = Integer.toString(val);
        }
        else {

            stringResult = Double.toString(result);
        }

        DecimalFormat df = new DecimalFormat("###,###.#######");
        stringResult = df.format(Double.parseDouble(stringResult));

        return stringResult;
    }
}

