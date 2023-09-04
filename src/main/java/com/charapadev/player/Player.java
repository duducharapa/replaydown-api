package com.charapadev.player;

import java.util.HashSet;
import java.util.Set;

import com.charapadev.pokemon.Pokemon;

public class Player {
    
    // Properties
    private String nickname;
    private Set<Pokemon> team = new HashSet<>();

    // Constructors
    public Player(String nickname) {
        this.nickname = nickname;
    }

    public void addPokemon(Pokemon pokemon) {
        if (team.size() < 6) team.add(pokemon);
    }

    // Getters
    public String getNickname() {
        return nickname;
    }

    public Set<Pokemon> getTeam() {
        return team;
    }

    // Setters
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String toString() {
        return String.format("[nickname=%s, team=%s]", nickname, team);
    }

}
