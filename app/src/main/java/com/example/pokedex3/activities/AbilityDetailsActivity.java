package com.example.pokedex3.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex3.models.Ability;
import com.example.pokedex3.R;

public class AbilityDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_ITEM = "com.example.item_prueba2.item";

    public static Intent newIntent(Context packageContext, Ability ability) {
        Intent intent = new Intent(packageContext, AbilityDetailsActivity.class);
        intent.putExtra(EXTRA_ITEM, ability);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability_details);

        Ability ability = (Ability) getIntent().getSerializableExtra(EXTRA_ITEM);

        TextView abilityNameTextView = findViewById(R.id.abilityNameTextView);
        TextView abilityEffectTextView = findViewById(R.id.abilityEffectTextView);
        TextView pokemonNamesTextView = findViewById(R.id.pokemonNamesTextView);


        abilityNameTextView.setText(ability.getName());
        abilityEffectTextView.setText(ability.getEffect());
        pokemonNamesTextView.setText(ability.getPokemonNames());



    }

}
