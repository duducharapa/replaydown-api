package com.charapadev.player;

import com.charapadev.game.Game;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlayerService {
    
    /**
     * Retrieves the participant of an game using the given enumeration.
     * 
     * @param game The game instance.
     * @param number The player enumeration.
     * @return The player enumerated.
     */
    public Player getPlayerFromGame(Game game, PlayerNumber number) {
        return number == PlayerNumber.P1 ?
            game.getPlayer1() :
            game.getPlayer2();
    }

}
