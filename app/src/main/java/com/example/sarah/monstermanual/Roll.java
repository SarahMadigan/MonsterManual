package com.example.sarah.monstermanual;

import android.util.Log;

import java.util.AbstractMap;

/**
 * Created by sarah on 2018-08-21.
 */

public class Roll {
    private int mDieType;
    private int mDieNum;
    private int mModifier;

    public Roll(int dieNum, int dieType, int modifier) {
        mDieNum = dieNum;
        mDieType = dieType;
        mModifier = modifier;
    }

    public int getDieNum() {
        return mDieNum;
    }

    public int getDieType() {
        return mDieType;
    }

    public int getModifier() {
        return mModifier;
    }

    public int getAverageResult() {
        double temp = (double) (mDieNum*((double) mDieType+1)/2);
        Log.e("THIS", "number: " + temp);
        return (int) Math.ceil(temp) + mModifier;
    }

}
