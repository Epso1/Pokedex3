package com.example.pokedex3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    private PokemonViewModel pokemonViewModel;
    private int offset = 0;
    private final int limit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Get the ViewModel
        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        // Observe the LiveData
        pokemonViewModel.getPokemonsList().observe(this, pokemons -> {
            // Update the UI
            if (pokemonAdapter == null) {
                pokemonAdapter = new PokemonAdapter(pokemons);
                recyclerView.setAdapter(pokemonAdapter);
            } else {
                pokemonAdapter.notifyDataSetChanged();
            }
        });

        // Load the items
        pokemonViewModel.loadPokemons(offset, limit);

        // Set the scroll listener to load more items when the end of the list is reached
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) { //check for scroll down
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        offset += limit;
                        pokemonViewModel.loadPokemons(offset, limit);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


}