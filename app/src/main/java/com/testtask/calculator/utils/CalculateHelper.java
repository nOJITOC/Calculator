package com.testtask.calculator.utils;

import com.testtask.calculator.data.CalculateDTO;
import com.testtask.calculator.data.Calculator;
import com.testtask.calculator.data.Operation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Иван on 21.10.2016.
 */
public class CalculateHelper {
    public Calculator mCalculator;
    private Double mCurrentNumber = null;
    private Character mCurrentArithmeticAction = null;
    private int mPriority = 0;
    private double multiplier = 10;
    private boolean isPointAvailable = true;

    public void setCurrentNumber(Double currentNumber) {
        mCurrentNumber = currentNumber;
    }

    public Calculator getCalculator() {
        return mCalculator;
    }


    public CalculateHelper() {
        mCalculator = new Calculator();
    }

    public String getResult() {
        if (mCurrentNumber != null)
            return String.format("%f", mCalculator.doCalculate(mCurrentNumber));
        else return null;
    }

    public ArrayList<Operation> getOperationList() {
        return mCalculator.getOperationList();
    }

    public void addOperation(Character operation, StringBuilder operationViewBuilder) {
        if (mCurrentArithmeticAction != null)
            operationViewBuilder.delete(operationViewBuilder.length() - 1, operationViewBuilder.length());
        if (isOperationAvailable()) {
            mCurrentArithmeticAction = operation;
            setPointAvailable();
        }
    }

    public boolean isOperationAvailable() {
        return mCurrentNumber != null;
    }

    public boolean isPointAvailable() {
        return isPointAvailable;
    }

    public void setPointUnavailable() {
        isPointAvailable = false;
    }

    public void setPointAvailable() {
        isPointAvailable = true;
    }

    public void addDigit(double addition) {
        addCurrentOperationToList();
        if (mCurrentNumber == null)
            mCurrentNumber = addition;
        else {

            if (isPointAvailable) {
                mCurrentNumber = mCurrentNumber * multiplier + addition;
            } else {
                StringBuilder sb = new StringBuilder();
                if (mCurrentNumber - Math.floor(mCurrentNumber) == 0) {
                    mCurrentNumber = Double.parseDouble(sb.append((int) mCurrentNumber.floatValue())
                            .append('.').append((int) addition).toString());
                } else {
                    mCurrentNumber = Double.parseDouble(sb.append(mCurrentNumber.doubleValue()).append((int) addition).toString());
                }

            }
        }
    }

    public void addCurrentOperationToList() {
        if (mCurrentNumber != null && mCurrentArithmeticAction != null) {
            mCalculator.addOperation(mCurrentNumber, mCurrentArithmeticAction, checkPriority(mCurrentArithmeticAction));
            mCurrentArithmeticAction = null;
            mCurrentNumber = null;
        }
    }

    public void appendCurrentPriority(int value) {
        mPriority += value;
    }

    public int getPriority() {
        return mPriority;
    }

    private int checkPriority(Character currentArithmeticAction) {
        int arithmeticPriority = 0;
        switch (currentArithmeticAction) {
            case '+':
            case '-':
                arithmeticPriority = 1;
                break;
            case '*':
            case '/':
            case '%':
                arithmeticPriority = 2;
                break;
        }

        return mPriority + arithmeticPriority;
    }


    public void setOperationList(ArrayList<Operation> list) {
        mCalculator.setOperationList(list);
    }

    public void setCalculateInfo(CalculateDTO data) {
        mCurrentNumber = data.getCurrentNumber();
        mCurrentArithmeticAction = data.getCurrentArithmeticAction();
        mPriority = data.getPriority();
        isPointAvailable = data.isPointAvailable();
    }


    public Double getCurrentNumber() {
        return mCurrentNumber;
    }


    public Character getCurrentArithmeticAction() {
        return mCurrentArithmeticAction;
    }


    public void removeChar(Character removedChar) {
        switch (removedChar) {
            case '(':
                appendCurrentPriority(-3);
                return;
            case ')':
                appendCurrentPriority(3);
                return;
            case '.':
                isPointAvailable = true;
                return;
        }
        if (mCurrentArithmeticAction != null) {
            mCurrentArithmeticAction = null;
        } else if (mCurrentNumber != null) {
            eraseDigit();
        } else {
            List<Operation> operations = mCalculator.getOperationList();
            updateCurrents();
        }
    }

    private void eraseDigit() {
        StringBuilder sb = new StringBuilder();
        sb.append(mCurrentNumber);
        sb.delete(sb.length() - 1, sb.length());
        if (mCurrentNumber - Math.floor(mCurrentNumber) == 0 && sb.charAt(sb.length() - 1) == '.')
            sb.delete(sb.length() - 2, sb.length());
        if (sb.length() == 0) {
            mCurrentNumber = null;
            updateCurrents();
        } else
            mCurrentNumber = Double.parseDouble(sb.toString());

    }
    public  void updateCurrents(){
        List<Operation> operations = mCalculator.getOperationList();
        if (operations.size() != 0) {
            Operation operation = mCalculator.removeOperation(operations.size() - 1);
            mCurrentNumber = operation.getNumber();
            mCurrentArithmeticAction = operation.getOperation();
        }
    }
}
