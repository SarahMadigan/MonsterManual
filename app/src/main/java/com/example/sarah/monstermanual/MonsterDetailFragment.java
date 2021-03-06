package com.example.sarah.monstermanual;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sarah.monstermanual.dummy.DummyContent;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * A fragment representing a single Monster detail screen.
 * This fragment is either contained in a {@link MonsterListActivity}
 * in two-pane mode (on tablets) or a {@link MonsterDetailActivity}
 * on handsets.
 */

public class MonsterDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private PojoMonster monster;
    private String plus = "+";
    private String minus = "-";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MonsterDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            monster = new PojoMonster();

            Activity activity = this.getActivity();
            Toolbar toolbar = (Toolbar) activity.findViewById(R.id.detail_toolbar);
            if (toolbar != null) {
                toolbar.setTitle(monster.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.monster_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.monster_detail)).setText(mItem.details);
//            ((TextView) rootView.findViewById(R.id.monster_name)).setText(kobold.name);
            ((TextView) rootView.findViewById(R.id.monster_str)).setText(String.format("%d", monster.getAbilityModifier(PojoMonster.Ability.STR)));
            ((TextView) rootView.findViewById(R.id.monster_dex)).setText(String.format("%d", monster.getAbilityModifier(PojoMonster.Ability.DEX)));
            ((TextView) rootView.findViewById(R.id.monster_con)).setText(String.format("%d", monster.getAbilityModifier(PojoMonster.Ability.CON)));
            ((TextView) rootView.findViewById(R.id.monster_int)).setText(String.format("%d", monster.getAbilityModifier(PojoMonster.Ability.INT)));
            ((TextView) rootView.findViewById(R.id.monster_wis)).setText(String.format("%d", monster.getAbilityModifier(PojoMonster.Ability.WIS)));
            ((TextView) rootView.findViewById(R.id.monster_cha)).setText(String.format("%d", monster.getAbilityModifier(PojoMonster.Ability.CHA)));
        }

        View strButton = rootView.findViewById(R.id.str_layout);
        strButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRollPopup(PojoMonster.Ability.STR);
            }
        });

        View dexButton = rootView.findViewById(R.id.dex_layout);
        dexButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRollPopup(PojoMonster.Ability.DEX);
            }
        });

        View conButton = rootView.findViewById(R.id.con_layout);
        conButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRollPopup(PojoMonster.Ability.CON);
            }
        });

        View intButton = rootView.findViewById(R.id.int_layout);
        intButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRollPopup(PojoMonster.Ability.INT);
            }
        });

        View wisButton = rootView.findViewById(R.id.wis_layout);
        wisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRollPopup(PojoMonster.Ability.WIS);
            }
        });

        View chaButton = rootView.findViewById(R.id.cha_layout);
        chaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRollPopup(PojoMonster.Ability.CHA);
            }
        });

        int numAttacks = monster.getNumberOfAttacks();

        LayoutInflater vi = getLayoutInflater();

        for (int i = 0; i < numAttacks; i++) {
            View attackView = vi.inflate(R.layout.card_attack, null);
            setAttackDetails(monster.getAttack(i), attackView);
            ((ViewGroup)rootView).addView(attackView, 1 + i, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            attackView.setOnClickListener(new AttackOnClickListener(monster.getAttack(i)) {
                @Override
                public void onClick(View v) {
                    int index = ((ViewGroup) v.getParent()).indexOfChild(v) - 1;
                    showAttackRollPopup(monster.getAttack(index));
                }

            });
        }

        return rootView;
    }

    private void setAttackDetails(Attack attack, View attackView) {
        ((TextView) attackView.findViewById(R.id.attack_name)).setText(attack.getAttackName());
        ((TextView) attackView.findViewById(R.id.attack_modifier)).setText("+" + attack.getAttackModifier() + " " + attack.getAttackType());
        ((TextView) attackView.findViewById(R.id.attack_average_damage)).setText(String.format("%d", attack.getAverageDamage()));
        ((TextView) attackView.findViewById(R.id.attack_damage_type)).setText(attack.getDamageType());
        Roll roll = attack.getDamageRoll(0);
        String operator = roll.getModifier() >= 0 ? plus : minus;
        ((TextView) attackView.findViewById(R.id.attack_damage)).setText(String.format("(%dd%d%s%d)", roll.getDieNum(), roll.getDieType(), operator, Math.abs(roll.getModifier())));
    }

    public void showRollPopup(PojoMonster.Ability ability) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_roll_result, null);
        TextView breakdown = popupView.findViewById(R.id.roll_breakdown);
        Roll roll = new Roll(1, 20, monster.getAbilityModifier(ability));
        onRoll(breakdown, roll);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        TextView dismissButton = popupView.findViewById(R.id.roll_dismiss_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void showAttackRollPopup(Attack attack) {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_attack_roll_result, null);
        ((TextView) popupView.findViewById(R.id.attack_roll_header)).setText(String.format("%s attack", attack.getAttackName()));
        Roll roll = new Roll(1, 20, attack.getAttackModifier());
        onRoll((TextView) popupView.findViewById(R.id.attack_roll_breakdown), roll);
        ((TextView) popupView.findViewById(R.id.damage_roll_header)).setText(String.format("%s attack", attack.getDamageType()));
        onRoll((TextView) popupView.findViewById(R.id.damage_roll_breakdown), attack.getDamageRoll(0));
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        TextView dismissButton = popupView.findViewById(R.id.attack_roll_dismiss_button);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void onRoll(TextView breakdownView, Roll roll) {
        int result = rollDie(roll);
        int modifier = roll.getModifier();
        String symbol = modifier < 0 ? "-" : "+";
        String breakdown = String.format(Locale.getDefault(), "%d %s %d = %d", result, symbol, Math.abs(modifier), result + modifier);
        breakdownView.setText(breakdown);
    }

    public int rollDie(Roll roll) {
        int result = 0;
        for (int i = 0; i < roll.getDieNum(); i++) {
            result += (int) Math.floor(Math.random() * roll.getDieType() + 1);
        }
        return result;
    }
}
