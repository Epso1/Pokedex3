package com.example.pokedex3.repositories;

import android.util.Log;

import com.example.pokedex3.models.Ability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AbilityRepository {
    private static final String API_URL = "https://pokeapi.co/api/v2/ability/";
    private final ExecutorService executorService;

    public AbilityRepository() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void loadAbilities(int offset, int limit, final AbilityRepository.Callback callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Ability> abilitiesList = new ArrayList<>();
                try {
                    URL url = new URL(API_URL + "?offset=" + offset + "&limit=" + limit);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();
                    connection.disconnect();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray results = jsonResponse.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject abilityObject = results.getJSONObject(i);
                        String abilityName = abilityObject.getString("name");
                        String abilityUrl = abilityObject.getString("url");

                        // Fetch additional data from the ability's URL
                        HttpURLConnection abilityConnection = (HttpURLConnection) new URL(abilityUrl).openConnection();
                        BufferedReader abilityReader = new BufferedReader(new InputStreamReader(abilityConnection.getInputStream()));
                        StringBuilder abilityResponse = new StringBuilder();
                        String abilityLine;

                        while ((abilityLine = abilityReader.readLine()) != null) {
                            abilityResponse.append(abilityLine);
                        }

                        abilityReader.close();
                        abilityConnection.disconnect();

                        JSONObject abilityDetails = new JSONObject(abilityResponse.toString());


                        // Obtain effect entries
                        JSONArray effectEntriesArray = abilityDetails.getJSONArray("effect_entries");
                        JSONObject effectEntry = effectEntriesArray.getJSONObject(1); // Get the second element
                        JSONObject language = effectEntry.getJSONObject("language");
                        String effect = "";
                        if (language.getString("name").equals("en")) {
                            effect = effectEntry.getString("effect");
                        }


                        // Obtain pokemon names
                        JSONArray pokemonArray = abilityDetails.getJSONArray("pokemon");
                        ArrayList<String> pokemonNames = new ArrayList<>();
                        for (int j = 0; j < pokemonArray.length(); j++) {
                            JSONObject pokemonEntry = pokemonArray.getJSONObject(j);
                            JSONObject pokemon = pokemonEntry.getJSONObject("pokemon");
                            pokemonNames.add(pokemon.getString("name"));
                        }

                        // Create Ability object and add it to the list
                        Ability ability = new Ability(abilityName, effect, pokemonNames);
                        abilitiesList.add(ability);
                    }

                    callback.onItemsLoaded(abilitiesList);
                } catch (IOException | JSONException e) {
                    Log.e("loadAbilities", "Error fetching data: " + e.getMessage());
                }
            }
        });
    }


    public interface Callback {
        void onItemsLoaded(List<Ability> abilities);
    }
}
