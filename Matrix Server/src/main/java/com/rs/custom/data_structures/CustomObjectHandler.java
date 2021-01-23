package com.rs.custom.data_structures;

import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.utils.ShopsHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomObjectHandler {
    private static int[] customObjects = new int[] {
        12309
    };

    public static void handleCustomOption1(Player player, WorldObject worldObject) {
        if(worldObject.getId() == 12309)//Culimnancor chest
            player.getInterfaceManager().sendInterface(762);
    }

    public static void handleCustomOption3(Player player, WorldObject worldObject) {
        if(worldObject.getId() == 12309)//Culimnancor chest
            ShopsHandler.openShop(player, 34);
    }

    /**
     * The long return statement is a conversion of an int[] array to a list
     * @param objectId
     * @return is the list contains an objectID
     */
    public static boolean isCustomObject(int objectId) {
        return Arrays.stream(customObjects).boxed().collect(Collectors.toList()).contains(objectId);
    }


}
