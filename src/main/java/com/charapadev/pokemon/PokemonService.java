package com.charapadev.pokemon;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokemonService {

    public SpeciesVariant resolveSpecificPokemon(String name) {
        boolean hasVariant = name.contains("-");

        if (hasVariant) {
            String[] parts = name.split("-", 2);
            String generalName = parts[0];
            String variant = parts[1];

            return new SpeciesVariant(generalName, variant);
        }

        return new SpeciesVariant(name, null);
    }

}
