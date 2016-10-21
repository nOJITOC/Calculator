package com.testtask.calculator.ui;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.testtask.calculator.R;
import com.testtask.calculator.data.CalculateDTO;
import com.testtask.calculator.data.Calculator;
import com.testtask.calculator.data.Operation;
import com.testtask.calculator.utils.CalculateHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String OPERATION_LIST = "OPERATION_LIST";
    private static final String CALCULATE_INFO = "CALCULATE_INFO";
    private static final String OPERATION_VIEW = "OPERATION_VIEW";
    private CalculateHelper mCalculateHelper;
    StringBuilder mOperationViewBuilder = new StringBuilder();
    private TextView mOperationTv, mResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOperationTv = (TextView) findViewById(R.id.operations_tv);
        mResultTv = (TextView) findViewById(R.id.result_tv);
        mCalculateHelper = new CalculateHelper();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<Operation> list = savedInstanceState.getParcelableArrayList(OPERATION_LIST);
            mCalculateHelper.setOperationList(list);
            CalculateDTO data = savedInstanceState.getParcelable(CALCULATE_INFO);
            mCalculateHelper.setCalculateInfo(data);
            if (mCalculateHelper.getCurrentNumber() != null)
                fillResultTv(mCalculateHelper.getResult());
            fillOperationTv(savedInstanceState.getString(OPERATION_VIEW));
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(OPERATION_LIST, mCalculateHelper.getOperationList());
        outState.putParcelable(CALCULATE_INFO, new CalculateDTO(mCalculateHelper));
        outState.putString(OPERATION_VIEW, mOperationViewBuilder.toString());
        super.onSaveInstanceState(outState);
    }


    public void fillOperationTv(String s) {
        mOperationViewBuilder.append(s);
        mOperationTv.setText(mOperationViewBuilder.toString());

    }

    public void fillResultTv(String result) {
        if (result != null)
            mResultTv.setText(result);
        else
            mResultTv.setText("");
    }

    public void digitClick(View view) {
        String digit = ((Button) view).getText().toString();
        double addition = Double.parseDouble(digit);
        mCalculateHelper.addDigit(addition);
        fillOperationTv(digit);

        fillResultTv(mCalculateHelper.getResult());

    }


    public void leftBraceClick(View view) {
        if (mCalculateHelper.getCurrentArithmeticAction() != null||mOperationTv.getText().toString().isEmpty()) {
            mCalculateHelper.addCurrentOperationToList();
            fillOperationTv(((Button) view).getText().toString());
            mCalculateHelper.appendCurrentPriority(3);
        }
    }

    public void rightBraceClick(View view) {
        if (mCalculateHelper.getPriority() > 0) {
            fillOperationTv(((Button) view).getText().toString());
            mCalculateHelper.appendCurrentPriority(-3);

        }
    }

    public void eraseClick(View view) {
        if (mOperationViewBuilder.length() != 0) {
            Character removedChar = mOperationViewBuilder.charAt(mOperationViewBuilder.length() - 1);
            mOperationViewBuilder.delete(mOperationViewBuilder.length() - 1, mOperationViewBuilder.length());
            mCalculateHelper.removeChar(removedChar);
            fillOperationTv("");
            fillResultTv(mCalculateHelper.getResult());

        }
    }

    public void multiplierClick(View view) {
        if (mCalculateHelper.isPointAvailable()) {
            mCalculateHelper.setPointUnavailable();
            fillOperationTv(((Button) view).getText().toString());

        }

    }


    public void operationClick(View view) {
        String operation = ((Button) view).getText().toString();
        mCalculateHelper.addOperation(operation.toCharArray()[0], mOperationViewBuilder);
        if (mCalculateHelper.isOperationAvailable()) {
            fillOperationTv(operation);
        }
    }
    public void resultClick(View view){
        mCalculateHelper = new CalculateHelper();
        mOperationViewBuilder =new StringBuilder();
        fillOperationTv("");

    }

}
