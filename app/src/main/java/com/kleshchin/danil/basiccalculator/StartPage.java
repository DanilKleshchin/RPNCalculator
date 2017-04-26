/*
 * Copyright (c) 2017. Created by Kleshchin Danil.
 * Site: https://vk.com/danil.kleshchin.
 * E-mail address: danil.kleshchin@gmail.com
 */

package com.kleshchin.danil.basiccalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StartPage extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAdd, btnMult, btnSub, btnDiv, btnClear, btnBackspace, btnDot, btnEqual;
    TextView textView;
    ArrayList<String> inputArray = new ArrayList<>();
    String tmp = "";
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        textView = (TextView) findViewById(R.id.textView);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnSub = (Button) findViewById(R.id.btnSubtraction);
        btnAdd = (Button) findViewById(R.id.btnAddition);
        btnDiv = (Button) findViewById(R.id.btnDivision);
        btnMult = (Button) findViewById(R.id.btnMultiplication);
        btnBackspace = (Button) findViewById(R.id.btnBackspace);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnEqual = (Button) findViewById(R.id.btnEqual);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnBackspace.setOnClickListener(this);
        btnEqual.setOnClickListener(this);

        try {
            sharedPreferences = getPreferences(MODE_PRIVATE);
            textView.setText(sharedPreferences.getString("value", ""));
            tmp = sharedPreferences.getString("value", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("value", textView.getText().toString());
        editor.commit();
    }

    String operations = "+-/*";
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnDot:
                if (!textView.getText().toString().contains(".")) {
                    if (textView.getText().toString().equals("")) {
                        textView.setText("0.");
                        inputArray.add("0.");
                    } else {
                        textView.setText(textView.getText().toString() + ".");
                        inputArray.add(".");
                    }
                }
                break;
            case R.id.btnSubtraction:
                /*if (tmp.equals("")) {
                    tmp = textView.getText().toString();
                    operation = "Subtraction";
                    textView.setText("");
                }*/
                if (!operations.contains(textView.getText().toString().substring(textView.getText().toString().length() - 1))) {
                    if (textView.getText().toString().equals("Infinity") || textView.getText().toString().equals("NaN")) {
                        textView.setText("");
                    }
                    if (textView.getText().toString().contains("=")) {
                        inputArray.clear();
                        inputArray.add(result);
                        inputArray.add("-");
                        textView.setText(result + "-");
                        tmp = "";
                    } else {
                        if (!textView.getText().toString().equals("")) {
                            //operation = "Subtraction";
                            textView.setText(textView.getText().toString() + "-");
                            inputArray.add(tmp);
                            inputArray.add("-");
                            tmp = "";
                        }
                    }
                }
                break;
            case R.id.btnAddition:
                /*if (tmp.equals("")) {
                    tmp = textView.getText().toString();
                    operation = "Addition";
                    textView.setText("");
                }*/
                if (!operations.contains(textView.getText().toString().substring(textView.getText().toString().length() - 1))) {
                    if (textView.getText().toString().equals("Infinity") || textView.getText().toString().equals("NaN")) {
                        textView.setText("");
                    }
                    if (textView.getText().toString().contains("=")) {
                        inputArray.clear();
                        inputArray.add(result);
                        inputArray.add("+");
                        textView.setText(result + "+");
                        tmp = "";
                    } else {
                        if (!textView.getText().toString().equals("")) {
                            //operation = "Addition";
                            textView.setText(textView.getText().toString() + "+");
                            inputArray.add(tmp);
                            inputArray.add("+");
                            tmp = "";
                        }
                    }
                }
                break;
            case R.id.btnMultiplication:
                /*if (tmp.equals("")) {
                    tmp = textView.getText().toString();
                    operation = "Multiplication";
                    textView.setText("");
                }*/
                if (!operations.contains(textView.getText().toString().substring(textView.getText().toString().length() - 1))) {
                    if (textView.getText().toString().equals("Infinity")) {
                        textView.setText("");
                    }
                    if (textView.getText().toString().contains("=")) {
                        inputArray.clear();
                        inputArray.add(result);
                        inputArray.add("*");
                        textView.setText(result + "*");
                        tmp = "";
                    } else {
                        if (!textView.getText().toString().equals("")) {
                            //operation = "Multiplication";
                            textView.setText(textView.getText().toString() + "*");
                            inputArray.add(tmp);
                            inputArray.add("*");
                            tmp = "";
                        }
                    }
                }
                break;
            case R.id.btnDivision:
                /*if (tmp.equals("")) {
                    tmp = textView.getText().toString();
                    operation = "Division";
                    textView.setText("");
                }*/
                if (!operations.contains(textView.getText().toString().substring(textView.getText().toString().length() - 1))) {
                    if (textView.getText().toString().equals("Infinity")) {
                        textView.setText("");
                    }
                    if (textView.getText().toString().contains("=")) {
                        inputArray.clear();
                        inputArray.add(result);
                        inputArray.add("/");
                        textView.setText(result + "/");
                        tmp = "";
                    } else {
                        if (!textView.getText().toString().equals("")) {
                            //operation = "Division";
                            textView.setText(textView.getText().toString() + "/");
                            inputArray.add(tmp);
                            inputArray.add("/");
                            tmp = "";
                        }
                    }
                }
                break;
            case R.id.btnBackspace:
                if (!textView.getText().toString().equals("")) {
                    if (textView.getText().equals("Infinity")) {
                        textView.setText("");
                    } else {
                        if (!textView.getText().toString().contains("=")) {
                            if(operations.contains(textView.getText().toString().substring(textView.getText().toString().length() - 1))) {
                                inputArray.remove(textView.getText().toString().length() - 1);
                                textView.setText(textView.getText().toString().substring(0, textView.getText().toString().length() - 1));
                            } else {
                                textView.setText(textView.getText().toString().substring(0, textView.getText().toString().length() - 1));
                                tmp = tmp.substring(0, tmp.length() - 1);
                            }
                        }
                    }
                }
                break;
            case R.id.btnClear:
                textView.setText("");
                inputArray.clear();
                tmp = "";
                break;
            case R.id.btn0:
                if (textView.getText().toString().equals("Infinity") || textView.getText().toString().equals("NaN")) {
                    textView.setText("");
                }
                if (!textView.getText().toString().equals("0")) {
                    textView.setText(textView.getText().toString() + "0");
                    tmp += "0";
                }
                break;
            case R.id.btnEqual:
                /*if (!tmp.equals("") && !textView.getText().toString().equals("")) {
                    textView.setText(calculateResult(operation));
                }*/
                inputArray.add(tmp);
                result = RPNConverter.ConvertRPNToResultString(RPNConverter.convertStringToRPN(inputArray));
                if (result.equals("Infinity")) {
                    textView.setText(result);
                    tmp = "";
                    inputArray.clear();
                } else {
                    textView.setText(textView.getText().toString() + " = " + result);
                }
                break;
            default:
                if (textView.getText().toString().equals("Infinity") || textView.getText().toString().equals("NaN")) {
                    textView.setText("");
                }
                textView.setText(textView.getText().toString() + ((Button) view).getText().toString());
                tmp += ((Button) view).getText().toString();
                break;
        }
    }

}

//TODO сделать отдельный класс для RPN и знаки в константы
