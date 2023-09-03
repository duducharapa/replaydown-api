package com.charapadev.model.logs;

import com.charapadev.LogType;
import com.charapadev.PlayerNumber;
import com.charapadev.model.Pokemon;

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

    // Setters
    public void setPlayer(PlayerNumber player) {
        this.player = player;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

}
