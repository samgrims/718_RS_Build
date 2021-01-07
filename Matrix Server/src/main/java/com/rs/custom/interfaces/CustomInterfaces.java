package com.rs.custom.interfaces;

import com.rs.game.player.Player;

public class CustomInterfaces {

    public static void welcomeScreen(Player player) {
        String[] message = new String[]{
                "Welcome!",
                "This is a pre-release 718 RSPS",
                "You will keep your stats and quest accomplishments upon release",
                "expect a completley incomplete game.",
                "----",
                "Come back here with \";;welcome\"",
                "View all commands with \";;commandlist\"",
                "Change your appreance with \";;appearence\""
        };
        make275Screen(player, message);
    }

    public static void debugCommandsListScreen(Player player) {
        String[] message = new String[]{
                "~~All Commands~~",
                "All commands start with \";;\". You can run them in the console",
                "to see previous commands and push \"Page Up\" to go to previous",
                "----",
                "debugcommandlist - shows this screen",
                "coordinaterepeater - repeats coordinates in 2 second intervals",
                "transformid - shows the npc you appear as",
                "debug - Enables/disables debug mode",
                "welcome - shows original welcome screen",
                "simdrop [NPC ID] - not implemented",
                "coordinates - Shows coordinates",
                "appearence - open starting wardrobe",
                "emptyinventory - Empties character inventory",
                "hideicompbetween [Interface ID] [Start Icomp ID] [End Icomp ID]",
                "showicompbetween [Interface ID] [Start Icomp ID] [End Icomp ID]",
                "hideicomp [Interface Id] [Icomp ID] - Hide component",
                "interh [Interface ID] - I have to test this one",
                "interfacecid [Interface ID] - Transforms text icompon to their ID",
                "starter - gives starting items to your character",
                "resetbank - Resets bank of your character",
                "loadjson - Manual JSON load of your character",
                "savejson - Manual JSON save of your character",
                "getcontroller - shows controller for your player",
                "addspins [Amount] - gives you a number of spins",
                "interface [Interface ID] - shows an interface",
                "firespirit - spawns a fire spirit",
                "xprate - shows xp rate",
                "timeplayed - returns total minutes played",
                "datecreated - shows when your account was created",
                "item [item id] [Amount] - gives item",
        };
        make275Screen(player, message);
    }

    private static void make275Screen(Player player, String[] message) {
        player.getInterfaceManager().sendInterface(275);
        int messageRow = 0;
        for(int componentID = 1; componentID <= 150; componentID++) {
            if(componentID >= 2 && componentID <= 9)//Skip container components
                continue;
            if(messageRow < message.length)
                player.getPackets().sendIComponentText(275, componentID, message[messageRow]);
            else
                player.getPackets().sendIComponentText(275, componentID, "");
            messageRow++;
        }
    }

    public static void wardrobeScreen(Player player) {
        player.getInterfaceManager().sendInterface(729);
        player.getPackets().sendIComponentText(729,	21, "Confirm");
        player.getPackets().sendIComponentText(729,	3, "Appearence Wardrobe");

    }

}
