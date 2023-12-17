package com.example.pokedex3;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PokemonDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_ITEM = "com.example.item_prueba.item";

    public static Intent newIntent(Context packageContext, Pokemon pokemon) {
        Intent intent = new Intent(packageContext, PokemonDetailsActivity.class);
        intent.putExtra(EXTRA_ITEM, pokemon);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        Pokemon pokemon = (Pokemon) getIntent().getSerializableExtra(EXTRA_ITEM);

        TextView pokemonNameTextView = findViewById(R.id.pokemonNameTextView);
        TextView pokemonAbilitiesTextView = findViewById(R.id.pokemonAbilitiesTextView);
        TextView pokemonColorTextView = findViewById(R.id.pokemonColorTextView);
        TextView pokemonDescriptionTextView = findViewById(R.id.pokemonDescriptionTextView);
        ImageView itemImageView = findViewById(R.id.pokemonImageView);
        TextView pokemonHeightTextView = findViewById(R.id.pokemonHeightTextView);
        TextView pokemonWeightTextView = findViewById(R.id.pokemonWeightTextView);

        pokemonNameTextView.setText(pokemon.getName());
        pokemonAbilitiesTextView.setText(pokemon.getAbilities());
        pokemonDescriptionTextView.setText(pokemon.getFlavorTexts());
        pokemonColorTextView.setText(pokemon.getColor());
        pokemonHeightTextView.setText(String.valueOf(pokemon.getHeight()) + " M");
        pokemonWeightTextView.setText(String.valueOf(pokemon.getWeight()) + " KG");



        // Use Glide to load the image from the URL into the ImageView
        Glide.with(this)
                .load(pokemon.getImageUrl())
                .into(itemImageView);
    }
}