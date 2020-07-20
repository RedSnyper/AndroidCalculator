package com.zeebs.calculator.calculation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    public static String evaluate(StringBuilder expression, Boolean isRad) throws Exception
    {
        try {
            List<String> expressionList = new ArrayList<> ();
            String result = "";
            Double value;
            DecimalFormat decimalFormat = new DecimalFormat("###,###.##########");



            expressionList = ExpressionSplitter.splitExpression (expression);
            expressionList = ExpressionParser.expressionForComputer (expressionList);
            expressionList = InfixToPostfix.infixToPostfix (expressionList);
            result = Calculate.result (expressionList, isRad);
            value = Double.parseDouble (result);
            result = decimalFormat.format (value);
            if(result.length()>=20)
            {
                decimalFormat = new DecimalFormat ("0.E00");
                result = decimalFormat.format (value);
            }


            System.gc();
            return result;


        }catch (Exception e)
        {
            return e.getMessage ();
        }







    }


}
