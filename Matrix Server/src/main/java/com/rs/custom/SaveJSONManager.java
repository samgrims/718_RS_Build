package com.rs.custom;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.utils.SerializableFilesManager;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

/**
 * Merges serialization and JSON for loseless gameplay through updates
 */
public class SaveJSONManager {
    protected JSONPlayerSaver jsonPlayerSaver;
    protected JSONPlayerLoader jsonPlayerLoader;

    public static SaveJSONManager startSaveJSONManager(Player player) {
        return new SaveJSONManager(player);
    }

    public SaveJSONManager(Player player) {
        this.jsonPlayerSaver = new JSONPlayerSaver();
        this.jsonPlayerSaver.setPlayer(player);
        this.jsonPlayerLoader = new JSONPlayerLoader();
        this.jsonPlayerLoader.setPlayer(player);
    }

    public void saveJSON() {
        jsonPlayerSaver.savePlayer();
    }

    public void loadJSON() {
        try {
            jsonPlayerLoader.loadPlayer();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean jsonExists(Player player) {
        String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
        File input_json = new File(filePath);
        if(input_json.exists())
            return true;
        return false;
    }
}
