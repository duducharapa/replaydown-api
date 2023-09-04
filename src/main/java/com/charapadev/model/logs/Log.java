package com.charapadev.model.logs;

import com.charapadev.LogType;

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
