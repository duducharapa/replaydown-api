package com.charapadev.model;

import java.util.HashSet;
import java.util.Set;

public class Pokemon {

    private String name;
    private Set<String> moves = new HashSet<>();

    // Constructors
    public Pokemon(String name) {
        this.name = name;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Set<String> getMoves() {
        return moves;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void addMove(String move) {
        if (moves.size() < 4) moves.add(move);
    }

    public String toString() {
        return String.format("[name=%s, moves=%s]", name, moves);
    }
    
}
