package com.rs.custom;

import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;

import java.io.File;
import java.util.stream.IntStream;

public class CustomUtilities {

    public static boolean isLocationOneOfTheseWorldTiles(WorldTile location, WorldTile ... worldTiles) {
        for(WorldTile worldTile : worldTiles)
            if(location.matches(worldTile))
                return true;
        return false;
    }

    public static boolean isMetalName(String metal) {
        switch(metal.toLowerCase()) {
            case "bronze":
            case "iron":
            case "steel":
            case "black":
            case "mithril":
            case "adamant":
            case "rune":
            case "dragon":
                return true;
            default:
                break;
        }
        return false;
    }

    public static boolean jsonExists(Player player) {
        File input_json = getJSONFile(player);
        if(input_json.exists())
            return true;
        return false;
    }

    public static void createJSON(Player player) {
        try {
            File input_json = getJSONFile(player);
            input_json.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static File getJSONFile(Player player) {
        String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
        return new File(filePath);
    }

    public static <T> T[] removeElementFromArray(T[] arr, int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }

        T[] anotherArray = (T[])new Object[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }
}
