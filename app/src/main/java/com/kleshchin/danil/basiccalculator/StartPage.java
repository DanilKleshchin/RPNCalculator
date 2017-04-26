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
    boolean haveDot = false;
    boolean clickOperationButton = false;

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
            String res = sharedPreferences.getString("value", "");
            if (!res.equals("")) {
                textView.setText(res);
                inputArray.add(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        String res = textView.getText().toString();
        if (!res.equals("")) {
            char[] operations = {'+', '-', '*', '/'};
            for (char c : operations) {
                if (res.contains(String.valueOf(c))) {
                    res = RPNConverter.ConvertRPNToResultString(RPNConverter.convertStringToRPN(inputArray));
                    break;
                }
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("value", res);
        editor.commit();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnDot:
                if (!haveDot) {
                    if (!textView.getText().toString().contains("Infinity") && !textView.getText().toString().contains("NaN")) {
                        if (textView.getText().toString().equals("")) {
                            textView.setText("0.");
                            if (!clickOperationButton) {
                                haveDot = true;
                                if (inputArray.isEmpty()) {
                                    inputArray.add("0.");
                                } else {
                                    inputArray.set(inputArray.size() - 1, inputArray.get(inputArray.size() - 1) + "0.");
                                }
                            }
                        } else {
                            textView.setText(textView.getText().toString() + ".");
                            haveDot = true;
                            inputArray.set(inputArray.size() - 1, inputArray.get(inputArray.size() - 1) + ".");
                        }
                    }
                }
                break;
            case R.id.btnBackspace:
                if (!textView.getText().toString().contains("Infinity") && !textView.getText().toString().contains("NaN") && !textView.getText().equals("")) {
                    textView.setText(textView.getText().toString().substring(0, textView.getText().toString().length() - 1));
                    if (!inputArray.isEmpty()) {
                        if (!clickOperationButton) {
                            if (inputArray.get(inputArray.size() - 1).substring(inputArray.get(inputArray.size() - 1).length() - 1).equals(".")) {
                                haveDot = false;
                            }
                            inputArray.set(inputArray.size() - 1, inputArray.get(inputArray.size() - 1).substring(0, inputArray.get(inputArray.size() - 1).length() - 1));
                        } else {
                            inputArray.remove(inputArray.size() - 1);
                        }
                    }
                } else {
                    textView.setText("");
                    inputArray.clear();
                }
                break;
            case R.id.btnClear:
                textView.setText("");
                inputArray.clear();
                haveDot = false;
                break;
            case R.id.btnEqual:
                textView.setText(RPNConverter.ConvertRPNToResultString(RPNConverter.convertStringToRPN(inputArray)));
                inputArray.clear();
                inputArray.add(textView.getText().toString());
                clickOperationButton = false;
                break;
            default:
                inputString(view);
                break;
        }
    }

    private void inputString(View view) {
        String operations = "+-/*";
        if (operations.contains(((Button) view).getText().toString())) {
            if (!textView.getText().toString().contains("Infinity") && !textView.getText().toString().contains("NaN")) {
                if (inputArray.isEmpty()) {
                    if (((Button) view).getText().toString().equals("-")) {
                        inputArray.add("-");
                        clickOperationButton = false;
                        textView.setText(textView.getText().toString() + ((Button) view).getText().toString());
                    }
                } else {
                    inputArray.add(((Button) view).getText().toString());
                    clickOperationButton = true;
                    textView.setText(textView.getText().toString() + ((Button) view).getText().toString());
                }
            } else {
                //TODO - работать с бесконечностью
                textView.setText("");
                clickOperationButton = true;
            }
            haveDot = false;
        } else {
            if (textView.getText().toString().equals("Infinity") || textView.getText().toString().equals("NaN")) {
                textView.setText("");
            }
            if (clickOperationButton) {
                inputArray.add(((Button) view).getText().toString());
                clickOperationButton = false;
            } else {
                if (inputArray.isEmpty()) {
                    inputArray.add(((Button) view).getText().toString());
                } else {
                    inputArray.set(inputArray.size() - 1, inputArray.get(inputArray.size() - 1) + ((Button) view).getText().toString());
                }
            }
            textView.setText(textView.getText().toString() + ((Button) view).getText().toString());
        }
    }
}