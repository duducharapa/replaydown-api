package com.charapadev.log;

import com.charapadev.player.PlayerNumber;
import com.charapadev.pokemon.Pokemon;

public class TeamLog extends Log {
    
    private PlayerNumber player;
    private Pokemon pokemon;

    // Constructors
    public TeamLog(PlayerNumber player, Pokemon pokemon) {
        super(LogType.POKEMON);
        
        this.player = player;
        this.pokemon = pokemon;
    }

    // Getters
    public PlayerNumber getPlayer() {
        return player;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

}
