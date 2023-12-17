package com.example.pokedex3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> pokemonList;

    public PokemonAdapter(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_layout, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView pokemonNameTextView;
        private ImageView pokemonImageView;

        public PokemonViewHolder(@NonNull View pokemonView) {
            super(pokemonView);
            pokemonNameTextView = pokemonView.findViewById(R.id.pokemonNameTextView);
            pokemonImageView = pokemonView.findViewById(R.id.pokemonImageView);


            pokemonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Pokemon pokemon = pokemonList.get(position);
                        Intent intent = PokemonDetailsActivity.newIntent(v.getContext(), pokemon);
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }

        public void bind(Pokemon pokemon) {
            pokemonNameTextView.setText(pokemon.getName());

            // Use Glide to load the image from the URL into the ImageView
            Glide.with(itemView.getContext())
                    .load(pokemon.getImageUrl())
                    .into(pokemonImageView);
        }
    }
}