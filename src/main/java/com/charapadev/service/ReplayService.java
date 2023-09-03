package com.charapadev.service;

import com.charapadev.model.Game;
import com.charapadev.model.Player;
import com.charapadev.model.ReplayData;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReplayService {

    public Game getBasicGameData(ReplayData data) {
        Player p1 = new Player(data.p1());
        Player p2 = new Player(data.p2());
        
        return new Game(data.format(), p1, p2);
    }

}
