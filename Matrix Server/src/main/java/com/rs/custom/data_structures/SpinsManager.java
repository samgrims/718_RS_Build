package com.rs.custom.data_structures;

import com.rs.game.player.Player;


public class SpinsManager {
    private transient Player player;

    public SpinsManager(Player player) {
        this.player = player;
    }

    public void addSpin() {
        player.setSpins(player.getSpins() + 1);
        player.getPackets().sendGameMessage("You recieved a free spin from Squeal Of Fortune.");
    }
}

