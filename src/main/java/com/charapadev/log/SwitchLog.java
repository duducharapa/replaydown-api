package com.charapadev.log;

import com.charapadev.player.PlayerNumber;

public class SwitchLog extends Log {
    
    private PlayerNumber player;
    private String pokemonName;
    private String pokemonNickname;

    public SwitchLog(PlayerNumber player, String pokemonName, String pokemonNickname) {
        super(LogType.SWITCH);

        this.player = player;
        this.pokemonName = pokemonName;
        this.pokemonNickname = pokemonNickname;
    }

    // Getters
    public PlayerNumber getPlayer() {
        return player;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public String getPokemonNickname() {
        return pokemonNickname;
    }

}
