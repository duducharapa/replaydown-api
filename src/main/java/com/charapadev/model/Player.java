package com.charapadev.model;

import java.util.HashSet;
import java.util.Set;

public class Player {
    
    // Properties
    private String nickname;
    private Set<Pokemon> team = new HashSet<>();

    // Constructors
    public Player(String nickname) {
        this.nickname = nickname;
    }

    public void addPokemon(Pokemon pokemon) {
        team.add(pokemon);
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

}
