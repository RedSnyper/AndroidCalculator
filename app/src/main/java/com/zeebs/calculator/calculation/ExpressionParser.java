package com.zeebs.calculator.calculation;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser  //responsible for taking the split expression and parse it into computer readable format
        // e.g 4(20) is converted to 4*(20) and 4*-(20) to 4*-1*(20)

{

    public static List expressionForComputer(List<String> expression)
    {

        List<String> expressionForComputer = new ArrayList<>();
        String valueAtCurrentLocation;
        for(int i = 0; i<expression.size();i++) {
            valueAtCurrentLocation = expression.get(i);
            if (valueAtCurrentLocation.matches("-?\\d+|-?\\d+\\.\\d+") && ( i!=expression.size()-1)) // for -xxxx.xx(... to -xxxx*(...
            {                                                                     //checks the condition if its in last or not so that get(i+1) can never be null as x+y- is invalid in app
                // in or if one condition true, no need to check another condition

                expressionForComputer.add(valueAtCurrentLocation);
                if(expression.get(i+1).equals("("))
                {
                    expressionForComputer.add("*");
                }
                continue;
            }else if (valueAtCurrentLocation.equals("-") && expression.get(i+1).equals("(")) // for -(-(-(xxx.xx...
            {                                                                      // this can never be null

                if(i!=0) // if not in the first element.
                {
                    if (expression.get(i - 1).matches("-?\\d+|-?\\d+\\.\\d+")) {
                        expressionForComputer.add(valueAtCurrentLocation);
                        continue;
                    }
                    else {
                        expressionForComputer.add("-1");
                        expressionForComputer.add("*");
                        continue;
                    }
                    // this can never be null

                }
                else{
                    expressionForComputer.add("-1");
                    expressionForComputer.add("*");
                    continue;
                }

            }else if (valueAtCurrentLocation.equals((")")))
            {
                expressionForComputer.add(valueAtCurrentLocation);
                if(i!=expression.size()-1)
                {
                    if(expression.get(i+1).matches("-?\\d+|-?\\d+\\.\\d+"))
                    {
                        expressionForComputer.add("*");
                    }
                }
            }
            else{
                expressionForComputer.add(valueAtCurrentLocation);
            }
        }
        System.out.println("the value is " + expressionForComputer.toString());
        return expressionForComputer;
    }


}
