package com.charapadev;

public enum LogType {
    POKEMON("poke");
    
    private final String value;

    private LogType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
