package com.rs.custom.data_structures;

import com.rs.game.player.Player;


public class SpinsManager {
    public static void addSpins(Player player, int amount) {
        player.setSpins(player.getSpins() + amount);
        player.getPackets().sendGameMessage("You recieved " + amount + " free spins for Squeal Of Fortune.");
    }
}

