package com.example.sarah.monstermanual;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sarah on 2018-08-16.
 */

public class PojoMonster {
    public String name;
    private int[] monsterAbilities;
    private ArrayList<Attack> attacks;

    public enum Ability {
            STR, DEX, CON, INT, WIS, CHA
    }



    public PojoMonster() {
        name = "Kobold";
        monsterAbilities = new int[6];
        monsterAbilities[0] = 10;
        monsterAbilities[1] = 11;
        monsterAbilities[2] = 8;
        monsterAbilities[3] = 13;
        monsterAbilities[4] = 14;
        monsterAbilities[5] = 15;
        attacks = new ArrayList<>();
        Roll roll = new Roll(1, 6, -1);
        Roll roll2 = new Roll(2, 10, 2);
        ArrayList<Roll> damageRolls = new ArrayList<>(Arrays.asList(roll));
        ArrayList<Roll> damageRolls2 = new ArrayList<>(Arrays.asList(roll2));
        attacks.add(new Attack("Claw", 9, damageRolls, "melee", "slashing"));
        attacks.add(new Attack("Bite", 5, damageRolls2, "ranged", "piercing"));
    }

    int getAbilityScore(Ability ability) {
        return monsterAbilities[ability.ordinal()];
    }

    int getAbilityModifier(Ability ability) {
        return (monsterAbilities[ability.ordinal()] - 10) /2;
    }

    int getNumberOfAttacks() {
        return attacks.size();
    }

    Attack getAttack(int index) {
        return attacks.get(index);
    }
}
