package com.rs.custom.interfaces;

import com.rs.game.player.Player;

public class Interfaces {

    public static void welcomeScreen(Player player) {
        player.getInterfaceManager().sendInterface(275);
        player.getPackets().sendIComponentText(275, 1, "Welcome!");
        player.getPackets().sendIComponentText(275, 10, "This is a pre-release 718 RSPS");
        player.getPackets().sendIComponentText(275, 11,
                "You will keep your stats and quest accomplishments upon release");
        player.getPackets().sendIComponentText(275, 12, "expect a completley incomplete game.");
        player.getPackets().sendIComponentText(275, 13, "");
        player.getPackets().sendIComponentText(275, 14, "Commands");
        player.getPackets().sendIComponentText(275, 15, ";;welcome - Get this screen");
        player.getPackets().sendIComponentText(275, 16, ";;simdrop [NPC ID] - Shows npc drops");
        player.getPackets().sendIComponentText(275, 17, ";;position - Shows coordinates");
        player.getPackets().sendIComponentText(275, 18, ";;appearence - open starting wardrobe");
        player.getPackets().sendIComponentText(275, 19, "");
        player.getPackets().sendIComponentText(275, 20, "");
        player.getPackets().sendIComponentText(275, 21, "");
        player.getPackets().sendIComponentText(275, 22, "");
        player.getPackets().sendIComponentText(275, 23,
                "");
        player.getPackets().sendIComponentText(275, 24,
                "");
        player.getPackets().sendIComponentText(275, 25, "");
        player.getPackets().sendIComponentText(275, 26, "");
        for (int i = 39; i <= 150; i++)
            player.getPackets().sendIComponentText(275, i, "");
    }
}
