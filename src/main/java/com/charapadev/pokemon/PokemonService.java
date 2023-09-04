package com.charapadev.pokemon;

import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokemonService {
    
    private Map<String, String> pokemons = Map.of(
        // Arceus variants
        "Arceus-*", "Arceus",

        // Samurott variants
        "Samurott-Hisui", "Samurott",

        // Rotom variants
        "Rotom-Wash", "Rotom",

        // Hoopa variants
        "Hoopa-Unbound", "Hoopa"
    );

    public String resolveSpecificPokemon(String name) {
        String newName = pokemons.get(name);

        return newName != null ? newName : name; 
    }

}
