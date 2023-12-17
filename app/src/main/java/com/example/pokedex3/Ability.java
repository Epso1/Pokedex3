package com.example.pokedex3;

import java.io.Serializable;
import java.util.ArrayList;

public class Ability implements Serializable {
    private String name;
    private String effect;

    private ArrayList<String> pokemonNames;

    public Ability(String name, String effect, ArrayList<String> pokemonNames) {
        this.name = name;
        this.effect = effect;
        this.pokemonNames = pokemonNames;
    }

    public String getName() { return name; }

    public String getEffect() { return effect; }

    public String getPokemonNames() {
        String pokemonNames = "";
        for (int i = 0; i < this.pokemonNames.size(); i++) {
            pokemonNames += this.pokemonNames.get(i);
            if (i < this.pokemonNames.size() - 1) {
                pokemonNames += ", ";
            }
        }

        return pokemonNames; }

    @Override
    public String toString() {
        return name;
    }
}
