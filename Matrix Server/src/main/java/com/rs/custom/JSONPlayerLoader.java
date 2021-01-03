package com.rs.custom;

import com.rs.Settings;
import com.rs.custom.data_structures.Toolbelt;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.*;
import com.rs.tools.DebugLine;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class JSONPlayerLoader {
    private Player player;
    private JSONObject playerMeta;
    private static byte itemName = 0, itemId = 1, itemAmount = 2;

    protected void setPlayer(Player player) {
        this.player = player;
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(getPlayerFile());
            this.playerMeta = (JSONObject) jsonParser.parse(reader);
        } catch(Exception e) {
            DebugLine.print("Could not find " + player.getUsername() + "JSON file");
        }
    }

    public void loadPlayer() {
        decodeCoordinate();
        decodeSkills();
        decodeInventory();
        decodeEquipment();
        decodeDetails();
//        decodeMusic();
        decodeToolbelt();
//        decodeBank();
    }

    private File getPlayerFile() throws Exception {
        String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
        File input_json = new File(filePath);
        if (!input_json.exists())
                input_json.createNewFile();
        return input_json;
    }

    private void decodeCoordinate() {
        JSONObject coordinateMeta = (JSONObject)playerMeta.get("coordinate");
        int x = ((Long)coordinateMeta.get("x")).intValue();
        int y = ((Long)coordinateMeta.get("y")).intValue();
        int plane = ((Long)coordinateMeta.get("plane")).intValue();
        player.setNextWorldTile(new WorldTile(x, y, plane));
    }

    private void decodeSkills() {
        JSONArray skills = (JSONArray)playerMeta.get("skills");
        JSONObject skill;
        for(byte skillID = 0; skillID < skills.size(); skillID++) {
            skill = (JSONObject) skills.get(skillID);
            String skillName = Skills.SKILL_NAME[skillID].toLowerCase();
            double xp = ((Long)((JSONArray)skill.get(skillName)).get(1)).doubleValue();
            player.getSkills().setXp(skillID, xp);
            player.getSkills().restoreSkills();
        }
    }

    private void decodeInventory() {
        JSONArray inventoryMeta = (JSONArray)playerMeta.get("inventory");
        Inventory inventory = player.getInventory();
        inventory.reset();
        for(byte slot = 0; slot < 28; slot++){
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

    private void decodeEquipment() {
        JSONArray equipmentMeta = (JSONArray) playerMeta.get("equipment");
        Equipment equipment = player.getEquipment();
        equipment.reset();
        for(byte slot = 0; slot < 15; slot++) {
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

    private void decodeDetails() {
        JSONObject detailsMeta = (JSONObject)playerMeta.get("details");
        long creationDate = (long)detailsMeta.get("creation date");
        int minutesPlayer = ((Long)detailsMeta.get("minutes played")).intValue();
        int spins = ((Long)detailsMeta.get("spins")).intValue();
        player.setCreationDate(creationDate);
        player.setTotalMinutesPlayed(minutesPlayer);
        player.setSpins(spins);
    }

    /**
     * Unused: regional music has not been implemented
     */
    private void decodeMusic() {
        JSONArray musicUnlockedMeta = (JSONArray)playerMeta.get("music unlocked");
        MusicsManager musicManager = player.getMusicsManager();
        musicManager.reset();
        for(int index = 0; index < musicUnlockedMeta.size(); index++) {
            int musicId = ((Long)musicUnlockedMeta.get(index)).intValue();
            musicManager.addMusicNoEmotes(musicId);
        }
    }

    /**
     * Does not reset the toolbelt, therefore you can only gain tools.
     */
    private void decodeToolbelt() {
        JSONArray itemsDepositedJSON = new JSONArray();
        itemsDepositedJSON = (JSONArray)playerMeta.get("toolbelt");

        int toolbeltSize = itemsDepositedJSON.size();
        List<Integer> toolbeltItems = Toolbelt.getToolbeltItems();
        for(int slot = 0; slot < toolbeltSize; slot++) {
            boolean isFilled = (boolean) itemsDepositedJSON.get(slot);
            if(isFilled) {
                int itemId = toolbeltItems.get(slot);
                Item item = new Item(itemId);
                player.getToolbelt().addItemForce(item);
            }
        }
    }
}
