package com.zeebs.calculator.calculation;



import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Calculate {

    static Stack<String> operand = new Stack<>();

    public static String result(List<String> infixToPostfix, boolean isRad) throws EmptyStackException, BadExpressionException {
        double res;     // for storing all the results.
        try {
            for (int i = 0; i < infixToPostfix.size(); i++) {


                String getCurrentValue = infixToPostfix.get(i);

                switch (getCurrentValue) {
                    case "+":
                        res = arithmeticCalc("+");
                        operand.push(Double.toString(res));
                        break;
                    case "-":
                        res = arithmeticCalc("-");
                        operand.push(Double.toString(res));
                        break;
                    case "*":
                        res = arithmeticCalc("*");
                        operand.push(Double.toString(res));
                        break;
                    case "/":
                        res = arithmeticCalc("/");
                        operand.push(Double.toString(res));
                        break;
                    case "^":
                        res = arithmeticCalc("^");
                        operand.push(Double.toString(res));
                        break;
                    case "!":
                        try {
                            BigInteger factorial = factorial(Integer.parseInt(operand.pop()));
                            operand.push(factorial.toString());
                            break;
                        }catch(NumberFormatException e)
                        {
                            throw new NumberFormatException("No factorial of decimals");
                        }catch (FactorialException e)
                        {
                            return e.getMessage();
                        }
                    case "$":
                        String number = operand.pop();
                        String expression = operand.pop();
                        res = functionCalc(expression, number, isRad);
                        try {
                            if (res == Double.POSITIVE_INFINITY || res == Double.NEGATIVE_INFINITY)
                                throw new FunctionException("Domain error");
                        } catch (NumberFormatException  | FunctionException e) {
                            return e.getMessage();
                        }
                        operand.push(Double.toString(res));
                        break;


                    case "\u221a":
                        res = Double.parseDouble(operand.pop());
                        res = Math.sqrt(res);
                        operand.push(Double.toString(res));
                        break;
                    case "(":
                        // added this just to give instantaneous result in the app without throwing errors.
                        break;
                    default:
                        operand.push(getCurrentValue);
                        break;
                }

            }
            return operand.pop();
        } catch (EmptyStackException e) {

            throw new BadExpressionException("Bad Expression");
        }

    }


    private static double arithmeticCalc(String sign) throws BadExpressionException {
        try {
            double operand1 = Double.parseDouble(operand.pop());
            double operand2 = Double.parseDouble(operand.pop());
            double res = 0.0;

            switch (sign) {
                case "+":
                    res = operand2 + operand1;
                    break;
                case "-":
                    res = operand2 - operand1;
                    break;
                case "*":
                    res = operand2 * operand1;
                    break;
                case "/":
                    res = operand2 / operand1;
                    break;
                case "^":
                    res = Math.pow(operand2, operand1);
                    break;
            }
            return res;
        } catch (EmptyStackException e) {
            throw new BadExpressionException("Bad Expression");
        }
    }


    private static double functionCalc(String function, String operand, boolean isRad) {

        double res = 0.0;
        double value = Double.parseDouble(operand);

        if (function.equals("log") || function.equals("ln")) {

            if (function.equals("log")) {
                res = Math.log10(value);
            } else {
                res = Math.log(value);
            }
            return res;
        } else if (function.equals("asin") || function.equals("acos") || function.equals("atan")) {

            switch (function) {
                case "asin":
                    res = Math.asin(value);
                    break;

                case "acos":
                    res = Math.acos(value);
                    break;

                case "atan":
                    res = Math.atan(value);
                    break;
            }

            if (!isRad)
                res = Math.toDegrees(res);

            return res;

        } else {

            if (!isRad) {
                value = Math.toRadians(value);     //by default java works in radian. So if degree is given, change to radian first
            }

            DecimalFormat df = new DecimalFormat("#.##########");    // to round off any precision error and to make degree calc accurate

            switch (function) {
                case "sin":
                    res = Math.sin(value);
                    break;

                case "cos":
                    res = Math.cos(value);
                    break;

                case "tan":
                    if (!isRad) {
                        double sinValue = Math.sin(value);             // doing this to remove the issue of tan(90) not being undefined.
                        double cosValue = Math.cos(value);
                        sinValue = Double.parseDouble(df.format(sinValue));
                        cosValue = Double.parseDouble(df.format(cosValue));          //cos(90) is not exact 0 but 6.xxxE-17
                        res = sinValue / cosValue;
                        break;
                    }
                    res = Math.tan(value);
                    break;
            }
            return res;
        }

    }

    static BigInteger factorial(Integer value) throws FactorialException {


            if(value>=0) {
                if (value <= 1000) {
                    BigInteger fact = new BigInteger("1");
                    for (int i = value; i >= 1; i--) {
                        fact = fact.multiply(BigInteger.valueOf(i));
                    }
                    return fact;
                } else {
                    throw new FactorialException("Factorial Limited Only to 1000");
                }
            }else
                throw new FactorialException("No factorial of negative number");
    }
}
