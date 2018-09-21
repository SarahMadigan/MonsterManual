package com.example.sarah.monstermanual;

import android.view.View;

public class AttackOnClickListener implements View.OnClickListener {

    Attack mAttack;
    public AttackOnClickListener(Attack attack) {
        mAttack = attack;
    }

    @Override
    public void onClick(View v) {

    }
}