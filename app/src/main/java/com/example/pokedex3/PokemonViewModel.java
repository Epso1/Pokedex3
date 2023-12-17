package com.example.pokedex3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokemonViewModel extends ViewModel {

    private final PokemonRepository pokemonRepository;
    private final MutableLiveData<List<Pokemon>> pokemonsLiveData = new MutableLiveData<>();

    private PokemonAdapter pokemonAdapter;
    public PokemonViewModel() {

        this.pokemonRepository = new PokemonRepository();
        this.pokemonAdapter = new PokemonAdapter(pokemonsLiveData.getValue());
    }

    public LiveData<List<Pokemon>> getPokemonsList() {
        return pokemonsLiveData;
    }

    public void loadPokemons(int offset, int limit) {
        pokemonRepository.loadItems(offset, limit, new PokemonRepository.Callback() {
            @Override
            public void onItemsLoaded(List<Pokemon> pokemons) {
                if (pokemonsLiveData.getValue() != null) {
                    int startPosition = pokemonsLiveData.getValue().size();
                    pokemonsLiveData.getValue().addAll(pokemons);
                    pokemonsLiveData.postValue(pokemonsLiveData.getValue());
                    pokemonAdapter.notifyItemRangeInserted(startPosition, pokemons.size());
                } else {
                    pokemonsLiveData.postValue(pokemons);
                    pokemonAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}