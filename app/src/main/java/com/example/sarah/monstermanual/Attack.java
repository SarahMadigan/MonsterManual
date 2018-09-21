package com.example.sarah.monstermanual;

import java.util.ArrayList;

/**
 * Created by sarah on 2018-08-21.
 */

public class Attack {
    private int mAttackModifier;
    private ArrayList<Roll> mDamageRolls;
    private String mAttackName;
    private String mAttackType;
    private String mDamageType;

    public Attack(String attackName, int attackModifier, ArrayList<Roll> damageRolls, String attackType, String damageType) {
        mAttackModifier = attackModifier;
        mDamageRolls = new ArrayList<>();
        mDamageRolls.addAll(damageRolls);
        mAttackName = attackName;
        mAttackType = attackType;
        mDamageType = damageType;
    }

    public int getAttackModifier() {
        return mAttackModifier;
    }

    public Roll getDamageRoll(int index) {
        return mDamageRolls.get(index);
    }

    public String getAttackName() {
        return mAttackName;
    }

    public String getAttackType() {
        return mAttackType;
    }

    public String getDamageType() {
        return mDamageType;
    }

    public int getAverageDamage() {
        return mDamageRolls.get(0).getAverageResult();
    }

}
