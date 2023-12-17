package com.example.pokedex3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex3.R;

public class MenuActivity extends AppCompatActivity {
    private ImageView logoImageView;
    private TextView titleTextView;

    private Button pokemonsButton;
    private Button abilitiesButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        logoImageView = findViewById(R.id.logoImageView);
        titleTextView = findViewById(R.id.titleTextView);
        pokemonsButton = findViewById(R.id.pokemonsButton);
        abilitiesButton = findViewById(R.id.abilitiesButton);

        pokemonsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        });

        abilitiesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AbilityActivity.class);
            startActivity(intent);
        });

    }
}
