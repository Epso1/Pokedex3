package com.example.pokedex3;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable {
    private String name;
    private String imageUrl;
    private ArrayList<String> abilities;
    private ArrayList<String> flavorTexts;
    private String color;

    private float height;
    private float weight;
    public Pokemon(String name, String imageUrl, ArrayList<String> abilities, ArrayList<String> flavorTexts, String color, float height, float weight) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.abilities = abilities;
        this.flavorTexts = flavorTexts;
        this.color = color;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getAbilities() {
        String abilitiesString = "";
        for (String ability : abilities) {
            abilitiesString += ability + "\n";
        }
        return abilitiesString;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFlavorTexts() {
        String flavorTextsString = "";
        for (String flavorText : flavorTexts) {
            flavorTextsString += flavorText + "\n";
        }
        return flavorTextsString;
    }

    public String getColor() {
        return color;
    }

    public float getHeight() {
        return height;
    }


    public float getWeight() {
        return weight;
    }

    @Override
    public String toString(){
        return name;
    }
}