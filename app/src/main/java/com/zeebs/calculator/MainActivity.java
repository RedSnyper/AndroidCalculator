
package com.zeebs.calculator;

import com.zeebs.calculator.calculation.Evaluator;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button tv0;
    private Button tv1;
    private Button tv2;
    private Button tv3;
    private Button tv4;
    private Button tv5;
    private Button tv6;
    private Button tv7;
    private Button tv8;
    private Button tv9;


    private Button tvAdd;
    private Button tvSub;
    private Button tvMul;
    private Button tvDiv;
    private Button tvPow;
    private Button tvFactorial;
    private Button tvPercentage;
    private Button tvSqrt;
    private Button tvPi;

    private Button tvOpenBracket;
    private Button tvCloseBracket;
    private Button tvDot;
    private Button tvEquals;
    private Button tvClear;


    private Button tvSin;
    private Button tvCos;
    private Button tvTan;
    private Button tvLog;
    private Button tvLn;
    private Button tvExp;

    private Button degRad;

    StringBuilder expressionString = new StringBuilder();


    private boolean multiplyFlag;           // to not have **
    private boolean divideFlag;             // to not have //
    private boolean allowOperatorUse = false; //flag for first time use which forbids the first input to be operators
    private boolean allowCloseBracketUse;
    private boolean allowSubtraction = true;       // for cases like -3 or (-3) in the beginning
    private boolean allowBracketOpenUse = true;
    private boolean isRad = true;
    private TextView tvResult;
    private EditText tvExpression;
    private ImageView tvBack;
    private int maxDotAllowed = 1;   // for cases like 5.5.5
    private int bracketOpenedCount = 0; // to display a toast if all open brackets are not closed
    private boolean showResult = false;


    private boolean dotFlag;            // to disallow for factorial

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.tv0 = (Button) findViewById(R.id.btnZero);
        this.tv1 = (Button) findViewById(R.id.btnOne);
        this.tv2 = (Button) findViewById(R.id.btnTwo);
        this.tv3 = (Button) findViewById(R.id.btnThree);
        this.tv4 = (Button) findViewById(R.id.btnFour);
        this.tv5 = (Button) findViewById(R.id.btnFive);
        this.tv6 = (Button) findViewById(R.id.btnSix);
        this.tv7 = (Button) findViewById(R.id.btnSeven);
        this.tv8 = (Button) findViewById(R.id.btnEight);
        this.tv9 = (Button) findViewById(R.id.btnNine);

        this.tvMul = (Button) findViewById(R.id.btnMultiply);
        this.tvAdd = (Button) findViewById(R.id.btnAdd);
        this.tvSub = (Button) findViewById(R.id.btnMinus);
        this.tvDiv = (Button) findViewById(R.id.btnDivide);
        this.tvPercentage = (Button) findViewById(R.id.btnPercentage);
        this.tvFactorial = (Button) findViewById(R.id.btnFact);
        this.tvPow = (Button) findViewById(R.id.btnPow);
        this.tvSqrt = (Button) findViewById(R.id.btnSqrt);
        this.tvPi = (Button) findViewById(R.id.btnPi);
        this.tvExp = (Button) findViewById(R.id.btnExponent);


        this.tvClear = (Button) findViewById(R.id.btnClear);

        this.tvOpenBracket = (Button) findViewById(R.id.btnOpeningBracket);
        this.tvCloseBracket = (Button) findViewById(R.id.btnClosingBracket);
        this.tvBack = (ImageView) findViewById(R.id.btnBack);
        this.tvDot = (Button) findViewById(R.id.btnDot);
        this.tvEquals = (Button) findViewById(R.id.btnEquals);


        this.tvExpression = (EditText) findViewById(R.id.expression);
        this.tvResult = (TextView) findViewById(R.id.result);


        this.tvSin = (Button) findViewById(R.id.btnSin);
        this.tvCos = (Button) findViewById(R.id.btnCos);
        this.tvTan = (Button) findViewById(R.id.btnTan);
        this.tvLog = (Button) findViewById(R.id.btnLog);
        this.tvLn = (Button) findViewById(R.id.btnln);


        this.degRad = (Button) findViewById(R.id.btnDEGRAD);

        //---------------------------------------------------------------------------------------------------------------------------------------//


        this.tv0.setOnClickListener(this);
        this.tv1.setOnClickListener(this);
        this.tv2.setOnClickListener(this);
        this.tv3.setOnClickListener(this);
        this.tv4.setOnClickListener(this);
        this.tv5.setOnClickListener(this);
        this.tv6.setOnClickListener(this);
        this.tv7.setOnClickListener(this);
        this.tv8.setOnClickListener(this);
        this.tv9.setOnClickListener(this);

        this.tvAdd.setOnClickListener(this);
        this.tvSub.setOnClickListener(this);
        this.tvMul.setOnClickListener(this);
        this.tvDiv.setOnClickListener(this);
        this.tvDot.setOnClickListener(this);
        this.tvSqrt.setOnClickListener(this);
        this.tvPow.setOnClickListener(this);
        this.tvPercentage.setOnClickListener(this);
        this.tvFactorial.setOnClickListener(this);
        this.tvExp.setOnClickListener(this);
        this.tvPi.setOnClickListener(this);

        this.tvOpenBracket.setOnClickListener(this);
        this.tvCloseBracket.setOnClickListener(this);
        this.tvBack.setOnClickListener(this);
        this.tvClear.setOnClickListener(this);
        this.tvEquals.setOnClickListener(this);

        this.tvSin.setOnClickListener(this);
        this.tvCos.setOnClickListener(this);
        this.tvTan.setOnClickListener(this);
        this.tvLog.setOnClickListener(this);
        this.tvLn.setOnClickListener(this);


        this.degRad.setOnClickListener(this);

        this.tvBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvBack.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                resetAll();
             return true;
            }
        });

        this.tvSin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvSin.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertTrigFunction("asin");

                return true;
            }
        });

        this.tvCos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvCos.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertTrigFunction("acos");

                return true;
            }
        });

        this.tvTan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tvTan.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                insertTrigFunction("atan");
                return true;
            }
        });

    }


    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            //------------------------------------------------------cases for numbers-------------------------------------------------------------------//
            case R.id.btnZero:
                tv0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("0");
                break;
            case R.id.btnOne:
                tv1.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("1");
                break;
            case R.id.btnTwo:
                tv2.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("2");
                break;
            case R.id.btnThree:
                tv3.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("3");
                break;
            case R.id.btnFour:
                tv4.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("4");
                break;
            case R.id.btnFive:
                tv5.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("5");
                break;
            case R.id.btnSix:
                tv6.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("6");
                break;
            case R.id.btnSeven:
                tv7.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("7");
                break;
            case R.id.btnEight:
                tv8.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("8");
                break;
            case R.id.btnNine:
                tv9.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("9");
                break;

            case R.id.btnExponent:
                tvExp.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("e".toLowerCase());
                break;

            case R.id.btnPi:
                tvPi.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertNumber("\u03c0".toLowerCase());
                break;

            case R.id.btnDot:
                tvDot.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertDot(".");
                break;
            //----------------------------------------------------------------------------------------------------------------------------------------//


            //--------------------------------------------------------cases for operators----------------------------------------------------------------//
            case R.id.btnAdd:
                tvAdd.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertSign("+");
                break;
            case R.id.btnMinus:
                tvSub.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertSign("-");
                break;
            case R.id.btnMultiply:
                tvMul.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertSign("*");
                break;
            case R.id.btnDivide:
                tvDiv.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertSign("/");
                break;
            case R.id.btnPercentage:
                tvPercentage.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertSign("%");
                tvResult.setText(calculateResult(expressionString));        // as percentage is number / 100 which is a number too
                break;
            case R.id.btnFact:
                tvFactorial.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertSign("!");
                break;
            case R.id.btnPow:
                tvPow.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertSign("^");
                break;
            case R.id.btnSqrt:
                tvSqrt.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                allowOperatorUse = true;        // square roots much like - can be used in the beginning as well
                insertSign("sqrt");
                break;
            //--------------------------------------------------------------------------------------------------------------------------------------//


            //-------------------------------------------------------case for brackets--------------------------------------------------------------//
            case R.id.btnOpeningBracket:
                tvOpenBracket.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertBrackets("(");
                break;

            case R.id.btnClosingBracket:
                tvCloseBracket.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertBrackets(")");
                break;

            //-----------------------------------------------------------------------------------------------------------------------------------//

            //------------------------------------------------------cases for trig fn--------------------------------------------------------------
            //
            case R.id.btnSin:
                tvSin.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertTrigFunction("sin");
                break;
            case R.id.btnCos:
                tvCos.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertTrigFunction("cos");
                break;
            case R.id.btnTan:
                tvTan.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertTrigFunction("tan");
                break;
            case R.id.btnLog:
                tvLog.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertTrigFunction("log");
                break;
            case R.id.btnln:
                tvLn.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                insertTrigFunction("ln");
                break;

            case R.id.btnDEGRAD:
                degRad.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if(isRad) {
                    degRad.setText("DEG");
                    Toast.makeText(this,"Changed To Degree",Toast.LENGTH_SHORT).show();
                    isRad = false;
                }
                else
                {
                    degRad.setText("RAD");
                    Toast.makeText(this,"Changed To Radian",Toast.LENGTH_SHORT).show();
                    isRad = true;
                }

                tvResult.setText(calculateResult(expressionString));
                break;

            //--------------------------------------------------------------------------------------------------------------------------------------//


            case R.id.btnClear:
                tvClear.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                resetAll();


        }

    }

    public void resetAll() {
        allowOperatorUse = false;
        bracketOpenedCount = 0;
        allowCloseBracketUse = false;
        tvExpression.setText("");
        tvResult.setText("");
        expressionString.setLength(0);
        maxDotAllowed = 1;
        //  showResult = false;
        // tvResult.setText("");
    }

    public void insertTrigFunction(String text) {

        expressionString.append(text + "(");
        afterBracketOpened();
        tvExpression.setText(expressionString);

    }


    public void insertDot(String text) {

        {                              //can only add . after there is a number;
//            boolean hasDigitPreceding = Character.isDigit(expressionString.charAt(expressionString.length() - 1));
            if (maxDotAllowed == 1) {
                expressionString.append(text);
                allowOperatorUse = false;           //disallow the use of operators after .
                dotFlag = true;
                maxDotAllowed++;
            }
        }
        tvExpression.setText(expressionString);

    }


    public void insertNumber(String text) {

        allowOperatorUse = true;                        //this is to not allow operators except - in the beginning or  dots
        allowCloseBracketUse = true;                    // this is to not allow brackets to be closed like ()
        expressionString.append(text);
        tvExpression.setText(expressionString);
        tvResult.setText(calculateResult(expressionString));

    }


    public void insertSign(String text) {

        switch (text) {
            case "-":
                insertNegativeSign(text);
                break;

            case "+":
            case "*":
            case "/":
            case "^":
                if (allowOperatorUse) {

                    expressionString.append(text);
                    allowOperatorUse = false;          // no repeating signs after these operators except for minus.
                }
                break;

            case "%":
                if (allowOperatorUse) {

                    expressionString.append(text);
                    allowOperatorUse = true;         // as x% + y or x% / y is valid
                }
                break;

            case "!":
                if (allowOperatorUse) {
                    if (!dotFlag) {
                        expressionString.append(text);
                        tvResult.setText(calculateResult(expressionString));

                        allowOperatorUse = true;                 // as x! + y or x! / y is valid
                    } else {
                        Toast.makeText(this, "Factorial only for positive integers", Toast.LENGTH_SHORT).show();
                    }
                }
                break;


            case "sqrt":
                expressionString.append("\u221A");
                allowOperatorUse = false;
                break;


        }

        maxDotAllowed = 1;              // resetting the dot allowed
        allowCloseBracketUse = false;       // to stop closing brackets like (x*/
        tvExpression.setText(expressionString);

    }


    public void insertNegativeSign(String text) {

        if (expressionString.length() == 0)       // for first starting cases;
        {
            expressionString.append(text);
        } else if (expressionString.charAt(expressionString.length() - 1) == '-' || expressionString.charAt(expressionString.length() - 1) == '.') {
            // do nothing for cases like x - - x or x.- which are ambiguous
        } else if (expressionString.charAt(expressionString.length() - 1) == '/') {

            expressionString.append("(");           // for cases like x/-y  to make x/(-y Adding this bracket cause the backend does not give correct out for cases like x/-y(a+b)

            bracketOpenedCount++;

            expressionString.append(text);
        } else {
            expressionString.append(text);
        }
    }


    public void insertBrackets(String text) {

        switch (text) {
            case "(":
                expressionString.append("(");
                afterBracketOpened();       // setting operator use to false as (*x, (/x or even (+x are ambiguous
                // an opening bracket will only allow close bracket use if there are numbers inside it. The numbers trigger allow close bracket use to true
                break;

            case ")":
                if (bracketOpenedCount != 0 && allowCloseBracketUse)// you can only use close bracket if there is an open bracket and if there is a number and not a sign before
                {
                    expressionString.append(")");
                    bracketOpenedCount--;
                } else {
                    Toast.makeText(this, "No open brackets to close", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        tvExpression.setText(expressionString);


    }

    public void afterBracketOpened()                // to not allow signs except minus, not allow closing brackets without numbers and increasting the bracket opened count
    {
        bracketOpenedCount++;
        allowCloseBracketUse = false;
        allowOperatorUse = false;

    }

    public String calculateResult(StringBuilder expression) {
        try {
            String result = Evaluator.evaluate(expression, isRad);
            return result;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    public void initalizeTextViews() {
//
//
//
////        tvBack.setOnLongClickListener(new View.OnLongClickListener() {
////            @Override
////            public boolean onLongClick(View view) {
////                tvBack.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                resetAll();
////                return true;
////            }
////        });
//    }


//    public void eventHandler() {
//        /* ----------------------------------------  Numbers ----------------------------------------------- */
//        tv0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//
//                tvExpression.append(tv0.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//
//
//            }
//        });
//
//
//        tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////
//                tv1.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//
//                tvExpression.append(tv1.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//            }
//        });
//
//
//        tv2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//
//                tv2.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//
//                tvExpression.append(tv2.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//            }
//        });
//
//
//        tv3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv3.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//                tvExpression.append(tv3.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//            }
//        });
//
//
//        tv4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv4.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");
//                tvExpression.append(tv4.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//            }
//        });
//
//
//        tv5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv5.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//                tvExpression.append(tv5.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//
//            }
//        });
//
//
//        tv6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv6.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//                tvExpression.append(tv6.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//            }
//        });
//
//
//        tv7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv7.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                tvExpression.append(tv7.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//            }
//        });
//
//
//        tv8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv8.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//                tvExpression.append(tv8.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//            }
//        });
//
//
//        tv9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tv9.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
////                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
////                    tvExpression.append("*");
//                tvExpression.append(tv9.getText().toString());
//                result = getResult(tvExpression.getText().toString());
//                tvResult.setText(result);
//                showResult= true;
//                allowOperatorUse = true;
//
//            }
//        });
//
//        tvDot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvDot.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if (!tvExpression.getText().toString().isEmpty()) {
//
//                    if (Character.isDigit(tvExpression.getText().toString().charAt(tvExpression.length() - 1)) && maxDotAllowed == 1) {
//
//                        tvExpression.append(tvDot.getText().toString());
//                        maxDotAllowed++;
//
//                    } else {
//                        Toast.makeText(MainActivity.this, "Dot operator only for decimal place", Toast.LENGTH_SHORT).show();
//                    }
//                } else
//                    Toast.makeText(MainActivity.this, "Cant insert . first", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        /* ----------------------------------------  Numbers end ----------------------------------------------- */
//
//
//
//
//
//
//        /* ----------------------------------------  Operators ----------------------------------------------- */
//
//        tvMul.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvMul.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if (allowOperatorUse) {
//                    multiplyFlag = true;                                                   // when set true expessions like 3*/3 || 3**3 are invalid
//                    if (canAddConsecutiveOperators(tvExpression.getText().toString()))       //does not allow illegal expressions like 3 +- 3 but allows 3 * -3
//                    {
//                        System.out.println("working till here?");
//                        tvExpression.append(tvMul.getText().toString());
//                        multiplyFlag = false;
//                        maxDotAllowed = 1;
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//        tvDiv.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                tvDiv.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if (allowOperatorUse) {
//                    divideFlag = true;
//                    if (canAddConsecutiveOperators(tvExpression.getText().toString()) && allowOperatorUse) {
//                        tvExpression.append(tvDiv.getText().toString());
//                        divideFlag = false;
//                        maxDotAllowed = 1;
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//        tvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvAdd.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if (allowOperatorUse) {
//                    if (canAddConsecutiveOperators(tvExpression.getText().toString()) && allowOperatorUse) {
//                        tvExpression.append(tvAdd.getText().toString());
//                        maxDotAllowed = 1;
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//        tvSub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvSub.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if (allowSubtraction || tvExpression.getText().toString().isEmpty()) {
//                    tvExpression.append(tvSub.getText().toString());
//                    maxDotAllowed = 1;
//                    allowSubtraction = false;
//                } else if (canAddConsecutiveOperators(tvExpression.getText().toString()) && allowOperatorUse) {
//                    tvExpression.append(tvSub.getText().toString());
//                    maxDotAllowed = 1;
//                } else if ((tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == '(') {
//                    tvExpression.append(tvSub.getText().toString());
//                    maxDotAllowed = 1;
//                } else {
//                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        tvOpenBracket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvOpenBracket.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                //  allowOperatorUse = true;
//                boolean hasDigitPreceeding = false;
//                boolean hasClosingBracketPreceeding = false;
//                allowCloseBracketUse = true;
//                // boolean hasOpeningBracketPreceeding = false;
////                 if(tvExpression.getText().toString().isEmpty()) // case only for delete button where delete removes everything
////                 {
////                     allowBracketOpenUse = false;
////                 }
//                allowSubtraction = true;
//                if (allowOperatorUse && allowBracketOpenUse)  // for cases like "(3"    or  "4)" so as to make it (3*( and 4)*(
//                {
//                    hasDigitPreceeding = Character.isDigit(tvExpression.getText().toString().charAt(tvExpression.length() - 1));
//                    hasClosingBracketPreceeding = (tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == ')';
//
//                }
//
//                if (hasDigitPreceeding || hasClosingBracketPreceeding) // for 2 (4) to be 2*(4)
//                {
//                    tvExpression.append(tvMul.getText().toString());
//                    tvExpression.append(tvOpenBracket.getText().toString());
//                    bracketOpenedCount++;
//                    allowCloseBracketUse = true;
//                    allowBracketOpenUse = true;
//                } else {
//                    bracketOpenedCount++;
//                    tvExpression.append(tvOpenBracket.getText().toString());
//                    allowCloseBracketUse = true;
//                    allowBracketOpenUse = true;
//                }
//            }
//        });
//
//        tvCloseBracket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvCloseBracket.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if (allowCloseBracketUse && !tvExpression.getText().toString().isEmpty()) {
//                    if ((tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == '(')
//                        Toast.makeText(MainActivity.this, "Insert Number first", Toast.LENGTH_SHORT).show();
//
//                    else if (Character.isDigit(tvExpression.getText().toString().charAt(tvExpression.length() - 1)) ||
//                            ((tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == ')')) {
//
//                        if (bracketOpenedCount != 0) {
//                            tvExpression.append(tvCloseBracket.getText().toString());
//                            bracketOpenedCount--;
//                        } else {
//                            Toast.makeText(MainActivity.this, "Make open brackets first", Toast.LENGTH_SHORT).show();
//                            allowCloseBracketUse = false;
//                        }
//                    } else {
//                        Toast.makeText(MainActivity.this, "Cant close bracket after operator", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Make open brackets first", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        /* ----------------------------------------  Operators end ----------------------------------------------- */
//
//
//
//
//
//        /* ----------------------------------------  Action Commands----------------------------------------------- */
//
//        tvClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvClear.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//
//                resetAll();
//            }
//        });
//
//
//        tvBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tvBack.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                String expression = tvExpression.getText().toString();
//                // tvResult.setText("");
//                System.out.println("value after removing " + expression);
//                if (!expression.isEmpty()) {
//                    if (expression.charAt(expression.length() - 1) == ')') {
//                        bracketOpenedCount++;
//                    } else if (expression.charAt(expression.length() - 1) == '(') {
//                        bracketOpenedCount--;
//                    } else if (expression.charAt(expression.length() - 1) == '.') {
//                        maxDotAllowed = 1;
//                    }
//                    tvExpression.setText(expression.substring(0, expression.length() - 1));
//                    if (tvExpression.getText().toString().isEmpty()) {
//                        resetAll();
//                    }
//
//                } else {
//                    Toast.makeText(MainActivity.this, "Nothing to clear", Toast.LENGTH_SHORT).show();
//                    resetAll();
//                }
//            }
//        });
//
//
//        tvEquals.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("brackets remaining" + bracketOpenedCount);
//                String expression = tvExpression.getText().toString();
//                boolean isValidExpression = checkExecutableExpressions(expression);
//
//                if(isValidExpression && !expression.isEmpty() && bracketOpenedCount==0)
//                {
//
//                    tvExpression.setText(tvResult.getText().toString());
//                    resultLai();
//
//
//                }else if(expression.isEmpty()){
//                    Toast.makeText(MainActivity.this,"Empty expression",Toast.LENGTH_SHORT).show();
//                }else  if(bracketOpenedCount != 0)
//                    Toast.makeText(MainActivity.this,"Not all brackets closed",Toast.LENGTH_SHORT).show();
//                else {
//                    Toast.makeText(MainActivity.this,"Invalid expression",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }
//
//
//    /* ----------------------------------------  Action end ---------------------------------------------------- */
//
//
//
//
//
//    public boolean canAddConsecutiveOperators(String expression)       //does not allow illegal expressions like 3 +- 3 but allows 3 * -3
//    {
//        boolean isDigit = Character.isDigit(expression.charAt(expression.length() - 1));
//        if (isDigit) {
//            return true;
//        }else if(expression.charAt(expression.length() - 1) == ')')
//            return  true;
//        else {
//            if (((expression.charAt(expression.length() - 1) == '*' || expression.charAt(expression.length() - 1) == '/'))) {
//                if ( !divideFlag && !multiplyFlag ) {
//                    tvExpression.append(tvOpenBracket.getText().toString());// for expression like 3 * -3 it adds 3 * (-3
//                    bracketOpenedCount++;
//                    allowCloseBracketUse = true;
//                    return true;
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Division and multiplication cannot be consecutive", Toast.LENGTH_SHORT).show();
//                    divideFlag = false;
//                    multiplyFlag = false;
//                    return false;
//                }
//
//            } else {
//                Toast.makeText(MainActivity.this, "Invalid operator use", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        }
//    }
//
//    public void resultLai()
//    {
//       // allowOperatorUse = false;
//        bracketOpenedCount = 0;
//        multiplyFlag = false;
//        divideFlag = false;
//
//        allowCloseBracketUse = false;
//        allowSubtraction = true;
//        maxDotAllowed = 1;
//        showResult = false;
//        tvResult.setText("");
//    }
//

//
//
//
//    public boolean checkExecutableExpressions(String expression) {
//        if (expression.isEmpty()) {
//            return false;
//        } else {
//            char firstCharacter = expression.charAt(0);
//            char lastCharacter = expression.charAt(expression.length() - 1);
//
//            if (Character.isDigit(firstCharacter) && Character.isDigit(lastCharacter))
//                return true;
//            else if (firstCharacter == '(' && lastCharacter == ')')
//                return  true;
//            else if (Character.isDigit(firstCharacter) && lastCharacter == ')' )
//                return true;
//            else if (firstCharacter == '(' && Character.isDigit(lastCharacter) )
//                return true;
//            else if (firstCharacter == '-' && Character.isDigit(lastCharacter))
//                return true;
//            else if (firstCharacter == '-' && lastCharacter == ')')
//                return true;
//            else
//                return false;
//
//
//        }
//    }
//
//    public boolean hasPrecedingCloseBracket(String expression)
//    {
//        if(expression.isEmpty())
//        {
//            return  false;
//        }
//        else if (expression.charAt(expression.length()-1) ==')')
//        {
//
//            return true;
//        }
//        return  false;
//    }
//
//    public String getResult(String expression)
//    {
//
//        if(!showResult)
//        {
//            return "";
//        }else {
//            List<String> list;
//            list = ExpressionSplitter.splitExpression(expression);
//            list = ExpressionParser.expressionForComputer(list);    // makes the split expression into readable format
//            list = InfixToPostfix.infixToPostfix(list);
//            showResult= true;
//            this.result = Calculate.result(list);
//            return result;
//        }
//    }


    }
