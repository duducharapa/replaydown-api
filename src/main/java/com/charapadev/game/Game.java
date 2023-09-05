package com.charapadev.game;

import com.charapadev.player.Player;

public class Game {

    // Properties
    private String format;
    private int turns = 0;
    private Player player1;
    private Player player2;

    // Constructors
    public Game(String format, Player player1, Player player2) {
        this.format = format;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void addTurn() {
        turns++;
    }

    // Getters
    public String getFormat() {
        return format;
    }

    public int getTurns() {
        return turns;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    // Setters
    public void setFormat(String format) {
        this.format = format;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    
}
