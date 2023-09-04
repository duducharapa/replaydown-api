package com.charapadev.log;

/**
 * Refers to types of each log represents on application.
 * <p><p>
 * Based on each type, the application must proceed with different actions.
 */

public enum LogType {
    // Indicates that a pokemon from one of players is shown
    POKEMON("poke"),
    // Indicate that some pokemon from one of players used a move 
    MOVESET("move");
    
    private final String value;

    private LogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
