package com.zeebs.calculator.calculation;

import java.util.ArrayList;
import java.util.List;

public class ExpressionSplitter  //this class obj is passed in ExpressionParser class
{


    public static List splitExpression (String expression) {
        List <String> splitExpression = new ArrayList<>();
        char charAtCurrentPosition;
        String decimalAndRepeatingAppend = "";
        String negativeNumberAtFirstAppend = "";
        String negativeNumberAtMidAppend = "";
        boolean negativeNumberAtStart = false;
        boolean negativeNumberAtMid = false;
        boolean canAddAppendStringInArrayList = false;

        for (int i = 0; i < expression.length(); i++) {

            charAtCurrentPosition = expression.charAt(i);

            if (i == 0 && charAtCurrentPosition == '-')         //for cases like -x......
            {

                if (expression.charAt(i + 1) == '(') {

                    //do nothing. Applicable for only cases like -(X.....
                }
                else {

                    negativeNumberAtStart = true;
                    negativeNumberAtFirstAppend = negativeNumberAtFirstAppend.concat("-");

                }

            }
            else if (charAtCurrentPosition == '-')              //for cases like X (*|/) -Y.......
            {
                if (expression.charAt(i + 1) == '(') {

                    // do nothing. Applicable for cases like (-(-(X......
                }

                else if (expression.charAt(i - 1) == '*' || expression.charAt(i - 1) == '(' || expression.charAt(i-1) =='/' || expression.charAt(i-1)=='+' ) //for cases like x * -y.yyy or x + (-x.yy+z)
                {
                    negativeNumberAtMid = true;
                    negativeNumberAtMidAppend = negativeNumberAtMidAppend.concat("-");

                }
            }
            {
                if (! Character.isDigit(charAtCurrentPosition) && charAtCurrentPosition != '.') {
                    if (canAddAppendStringInArrayList)          // adds the string matching above criteria to arraylist
                    {
                        if (negativeNumberAtStart) {
                            splitExpression.add(negativeNumberAtFirstAppend + decimalAndRepeatingAppend);      // for -x
                            negativeNumberAtStart = false;
                            negativeNumberAtFirstAppend = "";

                        }
                        else if (negativeNumberAtMid) {
                            splitExpression.add(negativeNumberAtMidAppend + decimalAndRepeatingAppend);
                            negativeNumberAtMid = false;
                            negativeNumberAtMidAppend = "";

                        }
                        else {
                            splitExpression.add(decimalAndRepeatingAppend);

                        }
                        canAddAppendStringInArrayList = false;
                        decimalAndRepeatingAppend = "";
                        //
                    }
                    if (negativeNumberAtStart || negativeNumberAtMid) // no need to add the expression of '-'  in -x... or -x.xx... or y*-x.xx...
                    // as they are already concatenated in string.
                    {
                        continue;
                    }

                    splitExpression.add(Character.toString(charAtCurrentPosition));     // to add the current string expression in the loop

                }
                else if (Character.isDigit(charAtCurrentPosition) || charAtCurrentPosition == '.') // for numbers having one or more digits and decimal numbers
                {
                    decimalAndRepeatingAppend = decimalAndRepeatingAppend.concat(Character.toString(charAtCurrentPosition));
                    canAddAppendStringInArrayList = true;
                }
            }
            if (i == expression.length() - 1 && Character.isDigit(charAtCurrentPosition)) //adds the last element into the list (Revision needed)
            {
                if (negativeNumberAtMid || negativeNumberAtStart) //for cases with just -x or .....-x
                {
                    splitExpression.add("-" + decimalAndRepeatingAppend);
                }
                else {
                    splitExpression.add(decimalAndRepeatingAppend);
                }
                decimalAndRepeatingAppend = "";
            }
        }
            return splitExpression;
    }


}
