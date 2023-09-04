package com.charapadev.log;

public abstract class Log {
    
    protected LogType type;

    // Constructors
    public Log(LogType type) {
        this.type = type;
    }

    // Getters
    public LogType getType() {
        return type;
    }

    // Setters
    public void setType(LogType type) {
        this.type = type;
    }
    
}
