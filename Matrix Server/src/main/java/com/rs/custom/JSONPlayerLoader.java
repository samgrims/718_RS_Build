package com.rs.custom;

import com.rs.Settings;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONPlayerLoader {
    private static int itemName = 0, itemId = 1, itemAmount = 2;

    public static void loadPlayer(Player player) throws FileNotFoundException, ParseException, IOException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(getPlayerFile(player));
        JSONObject playerMeta = (JSONObject) jsonParser.parse(reader);
        decodeSkills(player, playerMeta);
        decodeInventory(player, playerMeta);
        decodeEquipment(player, playerMeta);
        decodeDetails(player, playerMeta);
//        decodeToolbelt(player, playerMeta);

    }

    private static File getPlayerFile(Player player) throws IOException {
        String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
        File input_json = new File(filePath);
        if (!input_json.exists())
            input_json.createNewFile();
        return input_json;
    }

    private static void decodeSkills(Player player, JSONObject playerMeta) {
        JSONArray skills = (JSONArray)playerMeta.get("skills");
        JSONObject skill;
        for(int skillID = 0; skillID < skills.size(); skillID++) {
            skill = (JSONObject) skills.get(skillID);
            String skillName = Skills.SKILL_NAME[skillID].toLowerCase();
            double xp = ((Long)((JSONArray)skill.get(skillName)).get(1)).doubleValue();
            player.getSkills().setXp(skillID, xp);
            player.getSkills().restoreSkills();
        }
    }

    private static void decodeInventory(Player player, JSONObject playerMeta) {
        JSONArray inventoryMeta = (JSONArray)playerMeta.get("inventory");
        Inventory inventory = player.getInventory();
        inventory.reset();
        for(int slot = 0; slot < 28; slot++){
            JSONArray itemMeta = (JSONArray)inventoryMeta.get(slot);
            if(itemMeta.isEmpty())
                continue;
            int id = ((Long)itemMeta.get(itemId)).intValue();
            int amount = ((Long)itemMeta.get(itemAmount)).intValue();
            Item item = new Item(id, amount);
            inventory.addItemOnSlot(slot, item);
        }
        inventory.refresh();
    }

    private static void decodeEquipment(Player player, JSONObject playerMeta) {
        JSONArray equipmentMeta = (JSONArray) playerMeta.get("equipment");
        Equipment equipment = player.getEquipment();
        equipment.reset();
        for(int slot = 0; slot < 15; slot++) {
            JSONArray itemMeta = (JSONArray)equipmentMeta.get(slot);
            if(itemMeta.isEmpty())
                continue;
            int id = ((Long)itemMeta.get(itemId)).intValue();
            int amount = ((Long)itemMeta.get(itemAmount)).intValue();
            Item item = new Item(id, amount);
            equipment.addItemOnSlot(slot, item);
            equipment.refresh(slot);
        }

    }

    private static void decodeDetails(Player player, JSONObject playerMeta) {
        JSONObject detailsMeta = (JSONObject)playerMeta.get("details");
        long creationDate = (long)detailsMeta.get("creation date");
        int minutesPlayer = ((Long)detailsMeta.get("minutes played")).intValue();
        int spins = ((Long)detailsMeta.get("spins")).intValue();
        player.setCreationDate(creationDate);
        player.setTotalMinutesPlayed(minutesPlayer);
        player.setSpins(spins);

        JSONObject coordinateMeta = (JSONObject)detailsMeta.get("location");
        int x = ((Long)coordinateMeta.get("x")).intValue();
        int y = ((Long)coordinateMeta.get("y")).intValue();
        int plane = ((Long)coordinateMeta.get("plane")).intValue();
        player.setNextWorldTile(new WorldTile(x, y, plane));

        JSONArray musicUnlockedMeta = (JSONArray)detailsMeta.get("music unlocked");
        for(int index = 0; index < musicUnlockedMeta.size(); index++) {
            int musicId = ((Long)musicUnlockedMeta.get(index)).intValue();
            player.getMusicsManager().addMusicNoEmotes(musicId);
        }
    }
}
