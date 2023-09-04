package com.charapadev.model.logs;

import com.charapadev.LogType;
import com.charapadev.PlayerNumber;

public class MoveLog extends Log {
    
    private PlayerNumber player;
    private String pokemonNickname;
    private String move;

    // Constructor
    public MoveLog(PlayerNumber player, String pokemonNickname, String move) {
        super(LogType.MOVESET);

        this.player = player;
        this.pokemonNickname = pokemonNickname;
        this.move = move;
    }

    // Getters
    public PlayerNumber getPlayer() {
        return player;
    }

    public String getPokemonNickname() {
        return pokemonNickname;
    }

    public String getMove() {
        return move;
    }

    // Setters
    public void setPlayer(PlayerNumber player) {
        this.player = player;
    }

    public void setPokemonNickname(String pokemonNickname) {
        this.pokemonNickname = pokemonNickname;
    }

    public void setMove(String move) {
        this.move = move;
    }

    @Override
    public String toString() {
        return String.format("[player=%s, type=%s, pokemonNickname=%s, move = %s]", player, type, pokemonNickname, move);
    }

}
