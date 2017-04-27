/*
 * Copyright (c) 2017. Created by Kleshchin Danil.
 * Site: https://vk.com/danil.kleshchin.
 * E-mail address: danil.kleshchin@gmail.com
 */

package com.kleshchin.danil.basiccalculator;

import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Danil Kleshchin on 24.04.2017.
 */

public class RPNConverter {
    /**
     * Данный класс предназначен для перевода входной последоветельности лексем в ОПЗ, выполнение арифметических оперций по правилам ОПЗ и вывода результата.
     */
    private static final String firstPriority = "+-";
    private static final String secondPriority = "*/";

    /**
     * Перевод входных лексем в ОПЗ
     * @param inputString - массив лексем.
     * @return массив лексем, записанных в ОПЗ.
     */

    public static ArrayList<String> convertStringToRPN(ArrayList<String> inputString) {
        Stack<String> stack = new Stack<>();
        ArrayList<String> outputRPN = new ArrayList<>();
        inputString.add("~");
        for (String str : inputString) {
            if (!str.equals("")) {
                if (str.equals("~")) {
                    while (!stack.isEmpty()) {
                        outputRPN.add(stack.peek());
                        stack.pop();
                    }
                    break;
                }
                if (!firstPriority.contains(str) && !secondPriority.contains(str)) {
                    outputRPN.add(str);
                } else {
                    if (secondPriority.contains(str)) {
                        if (stack.isEmpty()) {
                            stack.push(String.valueOf(str));
                        } else {
                            while (!stack.isEmpty() && secondPriority.contains(stack.peek())) {
                                outputRPN.add(stack.peek());
                                stack.pop();
                            }
                            stack.push(str);
                        }
                    } else {
                        if (firstPriority.contains(str)) {
                            while (!stack.isEmpty()) {
                                outputRPN.add(stack.peek());
                                stack.pop();
                            }
                            stack.push(String.valueOf(str));
                        }
                    }
                }
            }
        }
        return outputRPN;
    }

    /**
     * Подсчет результата
     * @param inputRPN - входные лексемы в ОПЗ
     * @return - результат операций
     */
    public static String ConvertRPNToResultString(ArrayList<String> inputRPN) {
        String result = "";
        inputRPN.add("~");
        Double firstValue, secondValue;
        Stack<String> stack = new Stack<>();
        try {
            for (String str : inputRPN) {
                if (str.equals("~")) {
                    while (!stack.isEmpty()) {
                        result += stack.peek();
                        stack.pop();
                    }
                    break;
                }
                if (!firstPriority.contains(str) && !secondPriority.contains(str)) {
                    stack.push(String.valueOf(str));
                } else {
                    String tmp;
                    String tmpSecondValue = stack.peek();
                    stack.pop();
                    if (!stack.isEmpty()) {
                        if (stack.peek().equals(".")) {
                            stack.pop();
                            tmp = stack.peek() + "." + tmpSecondValue;
                            secondValue = Double.parseDouble(tmp);
                            stack.pop();
                        } else {
                            secondValue = Double.valueOf(tmpSecondValue);
                        }
                    } else {
                        secondValue = Double.valueOf(tmpSecondValue);
                    }
                    String tmpFirstValue = stack.peek();
                    stack.pop();
                    if (!stack.isEmpty()) {
                        if (stack.peek().equals(".")) {
                            stack.pop();
                            tmp = stack.peek() + "." + tmpFirstValue;
                            firstValue = Double.parseDouble(tmp);
                            stack.pop();
                        } else {
                            firstValue = Double.valueOf(tmpFirstValue);
                        }
                    } else {
                        firstValue = Double.valueOf(tmpFirstValue);
                    }
                    switch (String.valueOf(str)) {
                        case "+":
                            stack.push(String.valueOf(firstValue + secondValue));
                            break;
                        case "-":
                            stack.push(String.valueOf(firstValue - secondValue));
                            break;
                        case "*":
                            stack.push(String.valueOf(firstValue * secondValue));
                            break;
                        case "/":
                            stack.push(String.valueOf(firstValue / secondValue));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
