package com.charapadev.pokemon;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pokemon {

    private String name;
    private String variant;
    private String nickname;
    private Set<String> moves = new HashSet<>();

    // Constructors
    public Pokemon(String name, String variant) {
        this.name = name;
        this.variant = variant;
    }

    // Getters
    public String getName() {
        String generalName = name;

        return variant != null ?
            String.join("-", name, variant) :
            generalName;
    }

    @JsonIgnore
    public String getSpeciesName() {
        return name;
    }

    @JsonIgnore
    public String getVariant() {
        return variant;
    }

    public Set<String> getMoves() {
        return moves;
    }

    public String getNickname() {
        return nickname != null ?
            nickname :
            name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addMove(String move) {
        if (moves.size() < 4) moves.add(move);
    }

    public String toString() {
        return String.format("[name=%s, nickname=%s moves=%s]", name, nickname, moves);
    }
    
}
