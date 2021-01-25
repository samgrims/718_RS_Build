package com.rs.custom.data_structures;

import com.rs.custom.CustomUtilities;
import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.utils.ShopsHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomObjectHandler {
    private static int[] customObjects = new int[] {
        12309, 12348, 12349, 29355, 36687
    };

    public static void handleCustomOption1(Player player, WorldObject worldObject) {
        int worldObjectID = worldObject.getId();
        switch(worldObjectID) {
            case 12309:
                player.getBank().openBank();
                break;
            case 12348:
            case 12349:
                player.getPackets().sendGameMessage("The door is locked");
                break;
            case 29355: //Lumbridge cellar -> kitchen
                if(CustomUtilities.isLocationOneOfTheseWorldTiles(player.getLocation(), new WorldTile(3208, 9616, 0)))
                    player.useObjectToMove(828, new WorldTile(3210, 3216, 0), 1, 2);
                break;
            case 36687: //lumbridge kitchen -> Cellar
                if(CustomUtilities.isLocationOneOfTheseWorldTiles(player.getLocation(), new WorldTile(3210, 3216, 0)))
                    player.useObjectToMove(828, new WorldTile(3208, 9616, 0), 2, 2);
                break;
            default:
                break;
        }
    }

    public static void handleCustomOption2(Player player, WorldObject worldObject) {
        if(worldObject.getId() == 12309)//Culimnancor chest
            ShopsHandler.openShop(player, 35);
    }

    public static void handleCustomOption3(Player player, WorldObject worldObject) {
        if(worldObject.getId() == 12309)//Culimnancor chest
            ShopsHandler.openShop(player, 34);
    }

    public static void handleCustomItemOnObject(Player player, WorldObject worldObject, Item item) {
        int worldObjectID = worldObject.getId();
        String worldObjectName = worldObject.getDefinitions().name.toLowerCase();
        switch(worldObjectID) {
            case 26966:
                if(CustomUtilities.isItemAWaterContainer(item))
                    player.getInventory().removeItems(new Item(229));
                    player.animate(new Animation(832));
                    player.getInventory().addItem(CustomUtilities.retrieveWaterFilledItemContatinerOfItem(item));

                break;
            default:
                break;
        }
    }

    /**
     * The long return statement is a conversion of an int[] array to a list
     * @param objectId
     * @return is the list contains an objectID
     */
    public static boolean isCustomObject(int objectId) {
        boolean isCustomObject = false;
        if(Arrays.stream(customObjects).boxed().collect(Collectors.toList()).contains(objectId))
            isCustomObject = true;
        return isCustomObject;
    }


}
