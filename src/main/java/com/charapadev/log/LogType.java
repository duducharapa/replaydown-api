package com.charapadev.log;

/**
 * Refers to types of each log represents on application.
 * <p><p>
 * Based on each type, the application must proceed with different actions.
 */

public enum LogType {
    // Indicates that a pokemon from one of players is shown
    POKEMON("poke"),
    // Indicates that some pokemon from one of players used a move 
    MOVESET("move"),
    // Indicates that some pokemon is entering to battle
    SWITCH("switch"),
    // Indicates a new turn count
    TURN("turn"),
    // Indicates that one player winned the game
    WIN("win");
    
    private final String value;

    private LogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
