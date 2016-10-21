package com.testtask.calculator.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Иван on 19.10.2016.
 */
public class Operation implements Comparable<Operation>,Parcelable{
    private Integer priority;
    private char operation;
    private double number;

    public Operation(double number, char operation, int priority) {
        this.number = number;
        this.operation = operation;
        this.priority = priority;
    }
    public double calculate(double currentNumber){
        switch (operation){
            case '+':
                currentNumber+=number;
                break;
            case '-':
                currentNumber=number-currentNumber;
                break;
            case '*':
                currentNumber*=number;
                break;
            case '/':
                currentNumber=number/currentNumber;
                break;
            case '%':
                currentNumber=number%currentNumber;
                break;
        }
        return currentNumber;
    }

    @Override
    public int compareTo(Operation operation1) {
        return priority.compareTo(operation1.priority);
    }

    public int getPriority() {
        return priority;
    }


    public char getOperation() {
        return operation;
    }


    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
    protected Operation(Parcel in) {
        priority = in.readByte() == 0x00 ? null : in.readInt();
        operation = (char) in.readValue(char.class.getClassLoader());
        number = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (priority == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(priority);
        }
        dest.writeValue(operation);
        dest.writeDouble(number);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Operation> CREATOR = new Parcelable.Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
        }
    };
}
