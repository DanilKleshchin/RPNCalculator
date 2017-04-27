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
    TextView mainTextView; /**Текстовое поле для отображения вводимых операции и результата*/
    TextView smallTextView;/**Текстовое поле для отображения введенных действий после нажатия на "="*/
    ArrayList<String> inputArray = new ArrayList<>();
    boolean haveDot = false; /**Флаг для проверки количества ввода точки*/
    boolean clickOperationButton = false; /**Флаг для проверки нажатия на знак операции, для того чтобы записывать нажатые цифры в одну лексему*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        mainTextView = (TextView) findViewById(R.id.mainTextView);
        smallTextView = (TextView) findViewById(R.id.smallTextView);
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
                mainTextView.setText(res);
                inputArray.add(res);
            }
            res = sharedPreferences.getString("expression", "");
            smallTextView.setText(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        String res = mainTextView.getText().toString();
        String expression = "";
        if (!res.equals("")) {
            char[] operations = {'+', '-', '*', '/'};
            for (char c : operations) {
                if (res.contains(String.valueOf(c))) {
                    res = RPNConverter.ConvertRPNToResultString(RPNConverter.convertStringToRPN(inputArray));
                    expression = mainTextView.getText().toString();
                    break;
                }
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("value", res);
        editor.putString("expression", expression);
        editor.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("value", mainTextView.getText().toString());
        outState.putString("expression", smallTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mainTextView.setText(savedInstanceState.getString("value"));
        smallTextView.setText(savedInstanceState.getString("expression"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnDot:
                if (!haveDot) {
                    if (!mainTextView.getText().toString().contains("Infinity") && !mainTextView.getText().toString().contains("NaN")) {
                        if (mainTextView.getText().toString().equals("")) {
                            mainTextView.setText("0.");
                            if (!clickOperationButton) {
                                haveDot = true;
                                if (inputArray.isEmpty()) {
                                    inputArray.add("0.");
                                } else {
                                    inputArray.set(inputArray.size() - 1, inputArray.get(inputArray.size() - 1) + "0.");
                                }
                            }
                        } else {
                            mainTextView.setText(mainTextView.getText().toString() + ".");
                            haveDot = true;
                            inputArray.set(inputArray.size() - 1, inputArray.get(inputArray.size() - 1) + ".");
                        }
                    }
                    smallTextView.setText("");
                }
                break;
            case R.id.btnBackspace:
                if (!mainTextView.getText().toString().contains("Infinity") && !mainTextView.getText().toString().contains("NaN") && !mainTextView.getText().equals("")) {
                    mainTextView.setText(mainTextView.getText().toString().substring(0, mainTextView.getText().toString().length() - 1));
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
                    smallTextView.setText("");
                } else {
                    mainTextView.setText("");
                    inputArray.clear();
                    smallTextView.setText("");
                }
                break;
            case R.id.btnClear:
                mainTextView.setText("");
                smallTextView.setText("");
                inputArray.clear();
                haveDot = false;
                break;
            case R.id.btnEqual:
                smallTextView.setText(mainTextView.getText().toString());
                mainTextView.setText(RPNConverter.ConvertRPNToResultString(RPNConverter.convertStringToRPN(inputArray)));
                inputArray.clear();
                inputArray.add(mainTextView.getText().toString());
                clickOperationButton = false;
                break;
            default:
                smallTextView.setText("");
                inputString(view);
                break;
        }
    }

    private void inputString(View view) {
        String operations = "+-/*";
        if (operations.contains(((Button) view).getText().toString())) { /**Если входной параметр операция*/
            if (!mainTextView.getText().toString().contains("Infinity") && !mainTextView.getText().toString().contains("NaN")) {
                if (inputArray.isEmpty()) {
                    if (((Button) view).getText().toString().equals("-")) { /**Если самое первое число отрицательное, то знак "-" записывается вместе с числом как одна лексема*/
                        inputArray.add("-");
                        clickOperationButton = false;
                        mainTextView.setText(mainTextView.getText().toString() + ((Button) view).getText().toString());
                    }
                } else {
                    inputArray.add(((Button) view).getText().toString());
                    clickOperationButton = true;
                    mainTextView.setText(mainTextView.getText().toString() + ((Button) view).getText().toString());
                }
            } else {
                //TODO - работать с бесконечностью
                mainTextView.setText("");
                clickOperationButton = true;
            }
            haveDot = false;
        } else {
            if (mainTextView.getText().toString().equals("Infinity") || mainTextView.getText().toString().equals("NaN")) {
                mainTextView.setText("");
            }
            if (clickOperationButton) {
                inputArray.add(((Button) view).getText().toString()); /**Начинаем новую лексему и добаляем в неё цифру*/
                clickOperationButton = false;
            } else {
                if (inputArray.isEmpty()) {
                    inputArray.add(((Button) view).getText().toString()); /**Начинаем новую лексему и добаляем в неё цифру т.к. массив лексем пуст*/
                } else {
                    inputArray.set(inputArray.size() - 1, inputArray.get(inputArray.size() - 1) + ((Button) view).getText().toString()); /**Добавляем входную цифру в лексему*/
                }
            }
            mainTextView.setText(mainTextView.getText().toString() + ((Button) view).getText().toString());
        }
    }
}