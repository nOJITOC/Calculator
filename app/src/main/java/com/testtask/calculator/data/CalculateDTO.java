package com.testtask.calculator.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.testtask.calculator.utils.CalculateHelper;

public class CalculateDTO implements Parcelable {
    private Double mCurrentNumber;
    private Byte mCurrentArithmeticAction;
    private int mPriority;
    private boolean isPointAvailable;


    public Double getCurrentNumber() {
        return mCurrentNumber;
    }

    public Character getCurrentArithmeticAction() {
        if (mCurrentArithmeticAction == null)
            return null;
        else return (char) mCurrentArithmeticAction.byteValue();
    }

    public int getPriority() {
        return mPriority;
    }


    public boolean isPointAvailable() {
        return isPointAvailable;
    }

    public CalculateDTO(CalculateHelper helper) {
        mCurrentNumber = helper.getCurrentNumber();
        if (helper.getCurrentArithmeticAction() == null)
            mCurrentArithmeticAction = null;
        else mCurrentArithmeticAction = (byte) helper.getCurrentArithmeticAction().charValue();
        mPriority = helper.getPriority();
        isPointAvailable = helper.isPointAvailable();
    }

    protected CalculateDTO(Parcel in) {
        mCurrentNumber = in.readByte() == 0x00 ? null : in.readDouble();
        mCurrentArithmeticAction = in.readByte() == 0x00 ? null : in.readByte();
        mPriority = in.readInt();
        isPointAvailable = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mCurrentNumber == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(mCurrentNumber);
        }
        if (mCurrentArithmeticAction == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeByte(mCurrentArithmeticAction);
        }
        dest.writeInt(mPriority);
        dest.writeByte((byte) (isPointAvailable ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CalculateDTO> CREATOR = new Parcelable.Creator<CalculateDTO>() {
        @Override
        public CalculateDTO createFromParcel(Parcel in) {
            return new CalculateDTO(in);
        }

        @Override
        public CalculateDTO[] newArray(int size) {
            return new CalculateDTO[size];
        }
    };
}