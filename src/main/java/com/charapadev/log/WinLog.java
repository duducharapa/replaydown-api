package com.charapadev.log;

public class WinLog extends Log {
    
    private String winnerName;

    public WinLog(String winnerName) {
        super(LogType.WIN);

        this.winnerName = winnerName;
    }

    // Getters
    public String getWinnerName() {
        return winnerName;
    }

}
