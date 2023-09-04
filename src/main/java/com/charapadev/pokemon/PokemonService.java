package com.charapadev.pokemon;

import java.util.NoSuchElementException;

import com.charapadev.player.Player;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokemonService {

    /**
     * Extract the variants of a Pokémon species.
     * 
     * @param name The complete pokémon name.
     * @return The pokemon species and your variant.
     */
    public SpeciesVariant extractSpeciesVariants(String name) {
        // The pokémon variants name has a - before your variant
        // Example: Samurott-hisui
        boolean hasVariant = name.contains("-");

        if (hasVariant) {
            String[] parts = name.split("-", 2);
            String generalName = parts[0];
            String variant = parts[1];

            return new SpeciesVariant(generalName, variant);
        }

        return new SpeciesVariant(name, null);
    }

    /**
     * Searches an Pokemon who belongs to the given species name on player team.
     * 
     * @param player The player instance.
     * @param species The species name.
     * @return The pokémon found.
     * @throws NoSuchElementException Cannot found any Pokémon with this species on player's team.
     */
    public Pokemon findBySpeciesName(Player player, String species) throws NoSuchElementException {
        return player.getTeam().stream()
            .filter(pkmn -> pkmn.getName().equals(species))
            .findFirst()
            .orElseThrow();
    }

    /**
     * Searches an Pokémon who has the given nickname to player team.
     * 
     * @param player The player.
     * @param nickname The pokémon nickname.
     * @return  The pokémon found.
     * @throws NoSuchElementException Cannot found any Pokémon with this nickname on player's team.
     */
    public Pokemon findByNickname(Player player, String nickname) throws NoSuchElementException {
        return player.getTeam().stream()
            .filter(pkmn -> pkmn.getNickname().equals(nickname))
            .findFirst()
            .orElseThrow();
    }

}
