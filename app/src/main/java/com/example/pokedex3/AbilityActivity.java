package com.example.pokedex3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AbilityActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private AbilityAdapter abilityAdapter;
    private AbilityViewModel abilityViewModel;
    private int offset = 0;
    private final int limit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Get the ViewModel
        abilityViewModel = new ViewModelProvider(this).get(AbilityViewModel.class);

        // Observe the LiveData
        abilityViewModel.getAbilities().observe(this, abilities -> {
            if (abilityAdapter == null) {
                abilityAdapter = new AbilityAdapter(abilities);
                recyclerView.setAdapter(abilityAdapter);
            } else {
                abilityAdapter.notifyDataSetChanged();
            }
        });

        // Load the items
        abilityViewModel.loadAbilities(offset, limit);

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
                        abilityViewModel.loadAbilities(offset, limit);
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
