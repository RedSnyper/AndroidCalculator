package com.zeebs.calculator.calculation;


import java.util.ArrayList;
import java.util.List;

public class ExpressionSplitter  //the returned split list is passed to ExpressionParser class.
{


    public static List splitExpression ( StringBuilder expression ) throws Exception {

        List<String> splitExpression = new ArrayList<> (expression.length ());      // to reduce the amount of list duplication?? (idk just my logic says this is faster lol)

        char charAtCurrentPosition;

        StringBuilder expressionAppend = new StringBuilder ();      // for appending negative cases, positive cases, numbers with decimals, trigonometry letters

        boolean negativeNumberAtStart = false;
        boolean negativeNumberAtMid = false;
        boolean canAddLetterInList = false;
        boolean canAddAppendStringInArrayList = false;


        try {
            for (int i = 0; i < expression.length (); i++) {

                charAtCurrentPosition = expression.charAt (i);


                if(charAtCurrentPosition==',') // removes all comma from the expression string;
                    continue;

                //--------------------------for figuring out negative number at the start or in the middle of expression--------------------------//

                if (i == 0 && charAtCurrentPosition == '-')         //for negative cases in the beginning. Sets a flag negativeNumberAtStart = true
                {

                    if (expression.charAt (i + 1) == '(') {

                        //do nothing. Applicable for only cases like -(X..... Such expressions are later handled by ExpressionParser class
                    }
                    else {

                        negativeNumberAtStart = true;
                        expressionAppend.append ("-");
                        continue;
                    }


                }
                else if (charAtCurrentPosition == '-')              //for negative cases in the mid like x[+/^/*]-y. Sets flag negativeNumberAtMid = true;
                {
                    if (expression.charAt (i + 1) == '(') {


                        // do nothing. Applicable for cases like (-(-(X...... This is handled by ExpressionParser class
                    }

                    else if (expression.charAt (i - 1) == '*' || expression.charAt (i - 1) == '(' ||
                            expression.charAt (i - 1) == '/' || expression.charAt (i - 1) == '+' || expression.charAt (i - 1) == '^'
                            || expression.charAt (i - 1) == '\u221a')    //for cases like x * -y.yyy or x + (-x.yy+z) \u221a is a square root
                    {
                        negativeNumberAtMid = true;
                        expressionAppend.append("-");
                        continue;
                    }

                }

                //----------------------------------------------------------negative cases over-----------------------------------------------------------------------//


                //---------------------------------------------------Handling expressions(numbers or letters)----------------------------------------------------//


                if (Character.isLetter (charAtCurrentPosition)) {
                    if (!negativeNumberAtMid && !negativeNumberAtStart) // case for all positive letter expressions like e, pi, sin, tan....

                    {
                        if (charAtCurrentPosition == 'e' || charAtCurrentPosition == '\u03c0') // hardcoded as sin, cos, log, pi, ln dont have an e.
                        {
                            splitExpression.add (Character.toString(charAtCurrentPosition));
                        }
                        else {
                            expressionAppend = expressionAppend.append (charAtCurrentPosition);      // for positive sin, ln, log, tan, cos
                            canAddLetterInList = true;                 // this string expressionAppend will get added to arraylist when there is a sign
                        }

                        continue;               /* continue as we need not check loop further after having an e or pi as they are already added in list.
                                                Same for expressions like sin, cos, tan, ln, log as they are appended in string which is to be added to arraylist
                                                later on the loop where the character at that current position is a sign. */
                    }

                    else       // case for negative number
                    {
                        if (charAtCurrentPosition == 'e' || charAtCurrentPosition == '\u03c0') {
                            expressionAppend.append (charAtCurrentPosition);
                            splitExpression.add (expressionAppend.toString());
                            expressionAppend.setLength (0);
                            negativeNumberAtMid = false;
                            negativeNumberAtStart = false;
                            continue;                                       // -e, -pi are single term. This also prevents form expressions like -esin into one string.
                        }
                        expressionAppend.append (charAtCurrentPosition);
                        canAddAppendStringInArrayList = true;               //appends the string in the list only on the loop where character is a sign.
                    }
                }


                else if (Character.isDigit (charAtCurrentPosition) || charAtCurrentPosition == '.') // for numbers having one or more digits and decimal numbers
                {
                    expressionAppend.append (charAtCurrentPosition);
                    if (i != expression.length () - 1) {
                        if (Character.isLetter(expression.charAt (i+1)))            // splits expression like 6e to 6, e
                        {
                            splitExpression.add(expressionAppend.toString ());
                            expressionAppend.setLength (0);
                            canAddAppendStringInArrayList=false;
                            continue;
                        }
                    }
                    canAddAppendStringInArrayList = true;
                }


                else if (!Character.isLetterOrDigit (charAtCurrentPosition) && charAtCurrentPosition != '.') {
                    if (canAddAppendStringInArrayList)          // Set true by repeating numbers, numbers with decimals or positive trig and log fn
                    {
                        if (negativeNumberAtStart) {

                            splitExpression.add (expressionAppend.toString());
                            negativeNumberAtStart = false;

                        }
                        else if (negativeNumberAtMid) {
                            splitExpression.add(expressionAppend.toString ());
                            negativeNumberAtMid = false;

                        }
                        else {
                            splitExpression.add (expressionAppend.toString ());         // to add the rest of the positive numbers in the list.
                        }

                        canAddAppendStringInArrayList = false;
                        expressionAppend.setLength (0);                             // cannot use continue as this is the loop for symbols so need to add the symbol too

                    }


                    if (canAddLetterInList) {
                        splitExpression.add (expressionAppend.toString ());
                        expressionAppend.setLength (0);
                        canAddLetterInList = false;
                    }

                    if (negativeNumberAtStart || negativeNumberAtMid)       // no need to add the expression of '-'  in -x... or -x.xx... or y*-x.xx...
                    // as they are already concatenated in string.
                    {
                        continue;
                    }

                    splitExpression.add (Character.toString (charAtCurrentPosition));     // to add the current string expression in arraylist

                }

                if (i == expression.length () - 1 && Character.isLetterOrDigit (charAtCurrentPosition)) /* adds the last element into the list.
                                                                                       The numbers are added in the list only on the loop where the char at that loop
                                                                                       is a sign. Since the string at end is always either a number or bracket or factorial,
                                                                                       this loop is needed to add last value ending with number. */
                {

                    splitExpression.add (expressionAppend.toString ());
                    expressionAppend.setLength (0);
                }
            }


            return splitExpression;



        } catch (Exception e) {
            throw new BadExpressionException ("Bad Expression");
        } finally {

            //was supposed to call splitExpression.clear() but apparently the return in try block would still not return from fn and call the splitExpression.clear()
            // here to make arraylist null and return this null value.

        }
    }
}
