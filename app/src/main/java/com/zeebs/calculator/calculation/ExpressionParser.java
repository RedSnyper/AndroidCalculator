package com.zeebs.calculator.calculation;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser

        /*
            responsible for taking the split expression and parse it into computer readable format
                       e.g 4(20) is converted to 4*(20) and 4*-(20) to 4*-1*(20)

            Also converts (-?)e and (-?)pi to their respective values;
            all trig and log fn are added a new operator like sin,( is parsed to sin,#,(
            The # operator is given the highest precedence.

        */ {

    final static String pi = "3.141592653";
    final static String neg_pi = "-3.141592653;";
    final static String e = "2.718281828";
    final static String neg_e = "-2.718281828";

    public static List expressionForComputer ( List<String> expression ) throws Exception {

        List<String> expressionForComputer = new ArrayList<> ();
        String valueAtCurrentLocation;

        try {
            for (int i = 0; i < expression.size (); i++) {
                valueAtCurrentLocation = expression.get (i);


                if ( valueAtCurrentLocation.matches ("-?\\d+|-?\\d+\\.\\d+|-?[e]|-?[\u03c0]|[!]") && (i != expression.size () - 1) ) // for -xxxx.xx(... to -xxxx*(...
                {                                                                                        //checks the condition if its in last or not so that get(i+1) can never be null as x+y- is invalid in app
                    // in or if one condition true, no need to check another condition
                    if ( valueAtCurrentLocation.matches ("-?[e]") )     // for replacing all e,pi values.
                    {
                        if ( valueAtCurrentLocation.equals ("-e") ) {
                            valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, neg_e);
                        }
                        else
                            valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, e);
                    }

                    else if ( valueAtCurrentLocation.matches ("-?[\u03c0]") ) {
                        if ( valueAtCurrentLocation.matches ("-[\u03c0]") )
                            valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, neg_pi);
                        else
                            valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, pi);
                    }

                    expressionForComputer.add (valueAtCurrentLocation);

                    if ( expression.get (i + 1).equals ("(") || expression.get (i + 1).matches ("\\d+(\\d+\\.\\d+)?|-?[e]|-?[\u03c0]|-?\\w+[a-z]|[\u221a]") ) {
                        expressionForComputer.add ("*");
                    }
                    continue;
                }

                else if ( valueAtCurrentLocation.equals ("-") && expression.get (i + 1).equals ("(") ) // for -(-(-(xxx.xx...
                {                                                                      // this can never be null

                    if ( i != 0 ) // if not in the first element.
                    {
                        if ( expression.get (i - 1).matches ("-?\\d+|-?\\d+\\.\\d+|-?[e]|-?[\u03c0]|[!]") ) //added for letter checking too
                        {
                            expressionForComputer.add (valueAtCurrentLocation);
                            continue;
                        }
                        else {
                            expressionForComputer.add ("-1");
                            expressionForComputer.add ("*");
                            continue;
                        }
                        // this can never be null

                    }
                    else {
                        expressionForComputer.add ("-1");
                        expressionForComputer.add ("*");
                        continue;
                    }

                }

                else if ( valueAtCurrentLocation.equals ((")")) || valueAtCurrentLocation.equals ("!") ) {
                    expressionForComputer.add (valueAtCurrentLocation);
                    if ( i != expression.size () - 1 )                          // if not in the last
                    {
                        if ( expression.get (i + 1).matches ("-?\\d+|-?\\d+\\.\\d+|-?[e]|-?[\u03c0]|\\w+[a-z]|[\u221a]|[(]")) {
                            expressionForComputer.add ("*");
                        }
                    }

                    // now addinng parser for %, e, pi, sin, cos, tan, asin, acos, atan...
                }


                else if ( valueAtCurrentLocation.equals ("%") ) {
                    expressionForComputer.add ("/");
                    expressionForComputer.add ("100");
                    if(i!=expression.size ()-1) //not in last
                    {
                        if(expression.get (i+1).matches ("-?\\d+|-?\\d+\\.\\d+|\\.\\d+|-?[e]|-?[\u03c0]|\\w+[a-z]|[!]|[\u221a]|[(]"))
                            expressionForComputer.add ("*");
                    }
                }



                // cases for trig and log fn
                else if ( valueAtCurrentLocation.matches ("-?\\w+[a-z]") ) {

                    if ( valueAtCurrentLocation.matches ("-\\w+[a-z]") ) // for negative trig and sin expression
                    {
                        StringBuilder negSignRemoval = new StringBuilder (valueAtCurrentLocation);
                        negSignRemoval = negSignRemoval.delete (0, 1);// removes the neg sin;
                        valueAtCurrentLocation = negSignRemoval.toString ();

                        if ( i != 0 && expression.get (i - 1) != "(" )        // for first negative cases -sin(x)+....
                        {
                            //    expressionForComputer.add ("*");
                        }
                        expressionForComputer.add ("-1");
                        expressionForComputer.add ("*");
                    }
                    else         // for positive expressions
                    {
                        if ( i != 0 && expression.get (i - 1) != "(" ) {
                            //   expressionForComputer.add("*");
                        }

                    }
                    expressionForComputer.add (valueAtCurrentLocation);
                    expressionForComputer.add ("$");                // new operator introduced $ having the highest precedence

                    continue;
                }


                else            // for values at the end or for other unchecked signs [Revision needed]
                {
                    if ( i == expression.size () - 1 ) {
                        if ( valueAtCurrentLocation.matches ("-?[e]") ) {
                            if ( valueAtCurrentLocation.matches ("-[e]") )
                                valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, neg_e);
                            else
                                valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, e);
                        }
                        else if ( valueAtCurrentLocation.matches ("-?[\u03c0]") ) {
                            if ( valueAtCurrentLocation.matches ("-[\u03c0]") )
                                valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, neg_pi);
                            else
                                valueAtCurrentLocation = valueAtCurrentLocation.replace (valueAtCurrentLocation, pi);
                        }

                    }

                    expressionForComputer.add (valueAtCurrentLocation);
                }
            }
            return expressionForComputer;
        }catch (Exception e)
        {
            throw  new BadExpressionException ("Bad Expression");
        }

    }


}
