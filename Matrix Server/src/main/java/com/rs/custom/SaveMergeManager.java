package com.rs.custom;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;

import java.io.File;

/**
 * Merges serialization and JSON for loseless gameplay through updates
 */
public class SaveMergeManager {
    public boolean loadedFromJSON;

    public static void saveJsonSerial(Player player) {
        JSONPlayerFileManager.savePlayer(player);
        SerializableFilesManager.savePlayer(player);
    }

    public static void loadJSON(Player player) {
        JSONPlayerFileManager.loadPlayer(player);
    }

    public static boolean jsonExists(Player player) {
        String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
        File input_json = new File(filePath);
        if(input_json.exists())
            return true;
        return false;
    }
}
