package com.example.pokedex3.repositories;

import android.util.Log;

import com.example.pokedex3.models.Pokemon;

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

public class PokemonRepository {

    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon/";
    private final ExecutorService executorService;

    public PokemonRepository() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void loadItems(int offset, int limit, final Callback callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final List<Pokemon> pokemonList = new ArrayList<>();
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
                        JSONObject itemObject = results.getJSONObject(i);
                        String itemName = itemObject.getString("name");
                        String itemUrl = itemObject.getString("url");

                        // Fetch additional data from the item's URL
                        HttpURLConnection itemConnection = (HttpURLConnection) new URL(itemUrl).openConnection();
                        BufferedReader itemReader = new BufferedReader(new InputStreamReader(itemConnection.getInputStream()));
                        StringBuilder itemResponse = new StringBuilder();
                        String itemLine;

                        while ((itemLine = itemReader.readLine()) != null) {
                            itemResponse.append(itemLine);
                        }

                        itemReader.close();
                        itemConnection.disconnect();

                        JSONObject itemDetails = new JSONObject(itemResponse.toString());

                        // Obtain abilities details
                        JSONArray abilitiesArray = itemDetails.getJSONArray("abilities");
                        ArrayList<String> abilities = new ArrayList<>();
                        for (int j = 0; j < abilitiesArray.length(); j++) {
                            JSONObject abilityEntry = abilitiesArray.getJSONObject(j);
                            JSONObject ability = abilityEntry.getJSONObject("ability");
                            abilities.add(ability.getString("name"));
                        }

                        // Obtain height
                        int height = itemDetails.getInt("height");


                        // Obtain weight
                        int weight = itemDetails.getInt("weight");

                        // Obtain official artwork image URL
                        JSONObject spritesObject = itemDetails.getJSONObject("sprites");
                        JSONObject otherObject = spritesObject.getJSONObject("other");
                        JSONObject officialArtworkObject = otherObject.getJSONObject("official-artwork");
                        String imageUrl = officialArtworkObject.getString("front_default");

                        // Fetch species details
                        JSONObject speciesObject = itemDetails.getJSONObject("species");
                        String speciesUrl = speciesObject.getString("url");

                        HttpURLConnection speciesConnection = (HttpURLConnection) new URL(speciesUrl).openConnection();
                        BufferedReader speciesReader = new BufferedReader(new InputStreamReader(speciesConnection.getInputStream()));
                        StringBuilder speciesResponse = new StringBuilder();
                        String speciesLine;

                        while ((speciesLine = speciesReader.readLine()) != null) {
                            speciesResponse.append(speciesLine);
                        }

                        speciesReader.close();
                        speciesConnection.disconnect();

                        JSONObject speciesDetails = new JSONObject(speciesResponse.toString());

                        // Obtain color
                        JSONObject colorObject = speciesDetails.getJSONObject("color");
                        String color = colorObject.getString("name");

                        // Obtain flavor text entries
                        JSONArray flavorTextEntriesArray = speciesDetails.getJSONArray("flavor_text_entries");
                        ArrayList<String> flavorTexts = new ArrayList<>();
                        for (int k = 0; k < flavorTextEntriesArray.length(); k++) {
                            JSONObject flavorTextEntry = flavorTextEntriesArray.getJSONObject(k);
                            JSONObject language = flavorTextEntry.getJSONObject("language");
                            if (language.getString("name").equals("en")) {
                                String flavorText = flavorTextEntry.getString("flavor_text");
                                flavorText = flavorText.replace("\n", " "); // Replace \n with a space
                                if (!flavorTexts.contains(flavorText)) { // Check if flavorTexts already contains the flavorText
                                    flavorTexts.add(flavorText);
                                }
                            }
                        }

                        // Create Pokemon object and add it to the list
                        Pokemon pokemon = new Pokemon(itemName, imageUrl, abilities, flavorTexts, color, (float)height/10, (float)weight/10);
                        pokemonList.add(pokemon);
                    }

                    callback.onItemsLoaded(pokemonList);
                } catch (IOException | JSONException e) {
                    Log.e("LoadItems", "Error fetching data: " + e.getMessage());
                }
            }
        });
    }


    public interface Callback {
        void onItemsLoaded(List<Pokemon> pokemons);
    }
}