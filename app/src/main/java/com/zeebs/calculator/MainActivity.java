
package com.zeebs.calculator;

import com.zeebs.calculator.calculation.Calculate;
import com.zeebs.calculator.calculation.ExpressionParser;
import com.zeebs.calculator.calculation.ExpressionSplitter;
import com.zeebs.calculator.calculation.InfixToPostfix;

import java.text.DecimalFormat;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {



    protected String result;

    private Button tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv0, tvMul, tvAdd, tvSub, tvDiv, tvClear, tvOpenBracket, tvCloseBracket, tvDot, tvEquals;

    private boolean multiplyFlag;           // to not have **
    private boolean divideFlag;             // to not have //
    private boolean allowOperatorUse =false; //flag for first time use which forbids the first input to be operators
    private boolean allowCloseBracketUse;
    private boolean allowSubtraction = true;       // for cases like -3 or (-3) in the beginning
    private boolean allowBracketOpenUse = true;
    private TextView tvResult, tvExpression;
    private ImageView tvBack;
    private int maxDotAllowed = 1;   // for cases like 5.5.5
    private int bracketOpenedCount = 0; // to display a toast if all open brackets are not closed
    private boolean showResult = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalizeTextViews();
        eventHandler();

    }


    public void initalizeTextViews() {


        this.tv0 =  findViewById(R.id.btnZero);
        this.tv1 =  findViewById(R.id.btnOne);
        this.tv2 =  findViewById(R.id.btnTwo);
        this.tv3 =  findViewById(R.id.btnThree);
        this.tv4 =  findViewById(R.id.btnFour);
        this.tv5 =  findViewById(R.id.btnFive);
        this.tv6 =  findViewById(R.id.btnSix);
        this.tv7 =  findViewById(R.id.btnSeven);
        this.tv8 =  findViewById(R.id.btnEight);
        this.tv9 =  findViewById(R.id.btnNine);
        this.tvMul =  findViewById(R.id.btnMultiply);
        this.tvAdd = findViewById(R.id.btnAdd);
        this.tvSub = findViewById(R.id.btnMinus);
        this.tvDiv =  findViewById(R.id.btnDivide);
        this.tvClear = findViewById(R.id.btnClear);
        this.tvOpenBracket =  findViewById(R.id.btnOpeningBracket);
        this.tvCloseBracket = findViewById(R.id.btnClosingBracket);
        this.tvBack = (ImageView) findViewById(R.id.btnBack);
        this.tvDot = findViewById(R.id.btnDot);
        this.tvOpenBracket =  findViewById(R.id.btnOpeningBracket);
        this.tvCloseBracket =  findViewById(R.id.btnClosingBracket);
        this.tvExpression = (TextView) findViewById(R.id.expression);
        this.tvResult = (TextView) findViewById(R.id.result);
        this.tvEquals = findViewById(R.id.btnEquals);
        tvBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tvBack.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                resetAll();
                return true;
            }
        });
    }



    public void eventHandler() {
        /* ----------------------------------------  Numbers ----------------------------------------------- */
        tv0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv0.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");

                tvExpression.append(tv0.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;


            }
        });


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                tv1.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");

                tvExpression.append(tv1.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;
            }
        });


        tv2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                tv2.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");

                tvExpression.append(tv2.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;
            }
        });


        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv3.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");
                tvExpression.append(tv3.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;
            }
        });


        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv4.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
                    tvExpression.append("*");
                tvExpression.append(tv4.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;
            }
        });


        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv5.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");
                tvExpression.append(tv5.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;

            }
        });


        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv6.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");
                tvExpression.append(tv6.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;
            }
        });


        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv7.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                tvExpression.append(tv7.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;
            }
        });


        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv8.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");
                tvExpression.append(tv8.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;
            }
        });


        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv9.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                if(hasPrecedingCloseBracket(tvExpression.getText().toString()))  // makes (4+3)3 to (4+3*3
//                    tvExpression.append("*");
                tvExpression.append(tv9.getText().toString());
                result = getResult(tvExpression.getText().toString());
                tvResult.setText(result);
                showResult= true;
                allowOperatorUse = true;

            }
        });

        tvDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvDot.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (!tvExpression.getText().toString().isEmpty()) {
                    if (Character.isDigit(tvExpression.getText().toString().charAt(tvExpression.length() - 1)) && maxDotAllowed == 1) {
                        tvExpression.append(tvDot.getText().toString());
                        maxDotAllowed++;

                    } else {
                        Toast.makeText(MainActivity.this, "Dot operator only for decimal place", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Cant insert . first", Toast.LENGTH_SHORT).show();
            }
        });


        /* ----------------------------------------  Numbers end ----------------------------------------------- */






        /* ----------------------------------------  Operators ----------------------------------------------- */

        tvMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvMul.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (allowOperatorUse) {
                    multiplyFlag = true;                                                   // when set true expessions like 3*/3 || 3**3 are invalid
                    if (canAddConsecutiveOperators(tvExpression.getText().toString()))       //does not allow illegal expressions like 3 +- 3 but allows 3 * -3
                    {
                        tvExpression.append(tvMul.getText().toString());
                        multiplyFlag = false;
                        maxDotAllowed = 1;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvDiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tvDiv.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (allowOperatorUse) {
                    divideFlag = true;
                    if (canAddConsecutiveOperators(tvExpression.getText().toString()) && allowOperatorUse) {
                        tvExpression.append(tvDiv.getText().toString());
                        divideFlag = false;
                        maxDotAllowed = 1;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAdd.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (allowOperatorUse) {
                    if (canAddConsecutiveOperators(tvExpression.getText().toString()) && allowOperatorUse) {
                        tvExpression.append(tvAdd.getText().toString());
                        maxDotAllowed = 1;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSub.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (allowSubtraction || tvExpression.getText().toString().isEmpty()) {
                    tvExpression.append(tvSub.getText().toString());
                    maxDotAllowed = 1;
                    allowSubtraction = false;
                } else if (canAddConsecutiveOperators(tvExpression.getText().toString()) && allowOperatorUse) {
                    tvExpression.append(tvSub.getText().toString());
                    maxDotAllowed = 1;
                } else if ((tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == '(') {
                    tvExpression.append(tvSub.getText().toString());
                    maxDotAllowed = 1;
                } else {
                    Toast.makeText(MainActivity.this, "Insert number first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvOpenBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvOpenBracket.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //  allowOperatorUse = true;
                boolean hasDigitPreceeding = false;
                boolean hasClosingBracketPreceeding = false;
                allowCloseBracketUse = true;
                // boolean hasOpeningBracketPreceeding = false;
//                 if(tvExpression.getText().toString().isEmpty()) // case only for delete button where delete removes everything
//                 {
//                     allowBracketOpenUse = false;
//                 }
                allowSubtraction = true;
                if (allowOperatorUse && allowBracketOpenUse)  // for cases like "(3"    or  "4)" so as to make it (3*( and 4)*(
                {
                    hasDigitPreceeding = Character.isDigit(tvExpression.getText().toString().charAt(tvExpression.length() - 1));
                    hasClosingBracketPreceeding = (tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == ')';

                }

                if (hasDigitPreceeding || hasClosingBracketPreceeding) // for 2 (4) to be 2*(4)
                {
                    tvExpression.append(tvMul.getText().toString());
                    tvExpression.append(tvOpenBracket.getText().toString());
                    bracketOpenedCount++;
                    allowCloseBracketUse = true;
                    allowBracketOpenUse = true;
                } else {
                    bracketOpenedCount++;
                    tvExpression.append(tvOpenBracket.getText().toString());
                    allowCloseBracketUse = true;
                    allowBracketOpenUse = true;
                }
            }
        });

        tvCloseBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCloseBracket.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (allowCloseBracketUse && !tvExpression.getText().toString().isEmpty()) {
                    if ((tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == '(')
                        Toast.makeText(MainActivity.this, "Insert Number first", Toast.LENGTH_SHORT).show();

                    else if (Character.isDigit(tvExpression.getText().toString().charAt(tvExpression.length() - 1)) ||
                            ((tvExpression.getText().toString().charAt(tvExpression.length() - 1)) == ')')) {

                        if (bracketOpenedCount != 0) {
                            tvExpression.append(tvCloseBracket.getText().toString());
                            bracketOpenedCount--;
                        } else {
                            Toast.makeText(MainActivity.this, "Make open brackets first", Toast.LENGTH_SHORT).show();
                            allowCloseBracketUse = false;
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Cant close bracket after operator", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Make open brackets first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* ----------------------------------------  Operators end ----------------------------------------------- */





        /* ----------------------------------------  Action Commands----------------------------------------------- */

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvClear.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                resetAll();
            }
        });


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBack.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                String expression = tvExpression.getText().toString();
                // tvResult.setText("");
                System.out.println("value after removing " + expression);
                if (!expression.isEmpty()) {
                    if (expression.charAt(expression.length() - 1) == ')') {
                        bracketOpenedCount++;
                    } else if (expression.charAt(expression.length() - 1) == '(') {
                        bracketOpenedCount--;
                    } else if (expression.charAt(expression.length() - 1) == '.') {
                        maxDotAllowed = 1;
                    }
                    tvExpression.setText(expression.substring(0, expression.length() - 1));
                    if (tvExpression.getText().toString().isEmpty()) {
                        resetAll();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Nothing to clear", Toast.LENGTH_SHORT).show();
                    resetAll();
                }
            }
        });


        tvEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("brackets remaining" + bracketOpenedCount);
                String expression = tvExpression.getText().toString();
                boolean isValidExpression = checkExecutableExpressions(expression);

                if(isValidExpression && !expression.isEmpty() && bracketOpenedCount==0)
                {

                    tvExpression.setText(tvResult.getText().toString());
                    resultLai();


                }else if(expression.isEmpty()){
                    Toast.makeText(MainActivity.this,"Empty expression",Toast.LENGTH_SHORT).show();
                }else  if(bracketOpenedCount != 0)
                    Toast.makeText(MainActivity.this,"Not all brackets closed",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(MainActivity.this,"Invalid expression",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    /* ----------------------------------------  Action end ---------------------------------------------------- */





    public boolean canAddConsecutiveOperators(String expression)       //does not allow illegal expressions like 3 +- 3 but allows 3 * -3
    {
        boolean isDigit = Character.isDigit(expression.charAt(expression.length() - 1));
        if (isDigit) {
            return true;
        }else if(expression.charAt(expression.length() - 1) == ')')
            return  true;
        else {
            if (((expression.charAt(expression.length() - 1) == '*' || expression.charAt(expression.length() - 1) == '/'))) {
                if ( !divideFlag && !multiplyFlag ) {
                    tvExpression.append(tvOpenBracket.getText().toString());// for expression like 3 * -3 it adds 3 * (-3
                    bracketOpenedCount++;
                    allowCloseBracketUse = true;
                    return true;
                }
                else {
                    Toast.makeText(MainActivity.this, "Division and multiplication cannot be consecutive", Toast.LENGTH_SHORT).show();
                    divideFlag = false;
                    multiplyFlag = false;
                    return false;
                }

            } else {
                Toast.makeText(MainActivity.this, "Invalid operator use", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public void resultLai()
    {
       // allowOperatorUse = false;
        bracketOpenedCount = 0;
        multiplyFlag = false;
        divideFlag = false;

        allowCloseBracketUse = false;
        allowSubtraction = true;
        maxDotAllowed = 1;
        showResult = false;
        tvResult.setText("");
    }

    public void resetAll()
    {
        allowOperatorUse = false;
        bracketOpenedCount = 0;
        multiplyFlag = false;
        divideFlag = false;
        tvExpression.setText("");
        allowCloseBracketUse = false;
        allowSubtraction = true;
        maxDotAllowed = 1;
        showResult = false;
        tvResult.setText("");
    }



    public boolean checkExecutableExpressions(String expression) {
        if (expression.isEmpty()) {
            return false;
        } else {
            char firstCharacter = expression.charAt(0);
            char lastCharacter = expression.charAt(expression.length() - 1);

            if (Character.isDigit(firstCharacter) && Character.isDigit(lastCharacter))
                return true;
            else if (firstCharacter == '(' && lastCharacter == ')')
                return  true;
            else if (Character.isDigit(firstCharacter) && lastCharacter == ')' )
                return true;
            else if (firstCharacter == '(' && Character.isDigit(lastCharacter) )
                return true;
            else if (firstCharacter == '-' && Character.isDigit(lastCharacter))
                return true;
            else if (firstCharacter == '-' && lastCharacter == ')')
                return true;
            else
                return false;


        }
    }

    public boolean hasPrecedingCloseBracket(String expression)
    {
        if(expression.isEmpty())
        {
            return  false;
        }
        else if (expression.charAt(expression.length()-1) ==')')
        {

            return true;
        }
        return  false;
    }

    public String getResult(String expression)
    {

        if(!showResult)
        {
            return "";
        }else {
            List<String> list;
            list = ExpressionSplitter.splitExpression(expression);
            list = ExpressionParser.expressionForComputer(list);    // makes the split expression into readable format
            list = InfixToPostfix.infixToPostfix(list);
            showResult= true;
            this.result = Calculate.result(list);
            return result;
        }
    }


}
