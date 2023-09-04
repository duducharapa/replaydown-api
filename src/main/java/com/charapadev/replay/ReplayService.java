package com.charapadev.replay;

import com.charapadev.game.Game;
import com.charapadev.player.Player;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReplayService {

    public Game getBasicGameData(ReplayData data) {
        Player p1 = new Player(data.p1());
        Player p2 = new Player(data.p2());
        
        return new Game(data.format(), p1, p2);
    }

}
