package com.testtask.calculator.data;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calculator {
    private ArrayList<Operation> mOperationList = new ArrayList<>();

    public void addOperation(double number, char operation, int priority) {
        mOperationList.add(new Operation(number, operation, priority));
    }

    public static void copyOperationList(List<Operation> destinationList, List<Operation> sourceList) {
        for (Operation operation : sourceList) {
            destinationList.add(new Operation(operation.getNumber(), operation.getOperation(), operation.getPriority()));
        }
    }

    public Operation removeOperation(int i) {
        return mOperationList.remove(i);
    }

    public ArrayList<Operation> getOperationList() {
        return mOperationList;
    }

    public double doCalculate(double currentNumber) {
        List<Operation> operations = new ArrayList<>();
        Calculator.copyOperationList(operations, mOperationList);
        for (int i = 0; i < mOperationList.size(); i++) {
            Operation currentOperation = Collections.max(operations);
            int currentIndex = operations.indexOf(currentOperation);
            if (currentIndex == operations.size() - 1)
                currentNumber = currentOperation.calculate(currentNumber);
            else
                operations.get(currentIndex + 1).setNumber(currentOperation.calculate(operations.get(currentIndex + 1).getNumber()));
            operations.remove(currentOperation);

        }
        return currentNumber;
    }


    public void setOperationList(ArrayList<Operation> list) {
        mOperationList = list;
    }
}
