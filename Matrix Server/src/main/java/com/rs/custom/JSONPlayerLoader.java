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
    private transient  Player player;
    private JSONObject playerMeta;
    private static byte itemName = 0, ITEMID = 1, ITEMAMOUNT = 2;

    protected void setPlayer(Player player) {
        this.player = player;
    }

    public void loadPlayer() {
        try {
            this.playerMeta = getPlayerFile();
        } catch(Exception e) {
            DebugLine.print("Player loaded without a JSON file");
            return;
        }
        decodeCoordinate();
        decodeSkills();
        decodeInventory();
        decodeEquipment();
        decodeDetails();
//        decodeMusic();
        decodeToolbelt();
        decodeBank();
        decodeAppearance();
    }

    /**
     * Retrieves the file as a JSONObject
     * @return
     * @throws Exception
     */
    private JSONObject getPlayerFile() throws Exception {
        File input_json = CustomUtilities.getJSONFile(player);
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(input_json);
        return (JSONObject) jsonParser.parse(reader);
    }

    public void decodeCoordinate() {
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
            int id = ((Long)itemMeta.get(ITEMID)).intValue();
            int amount = ((Long)itemMeta.get(ITEMAMOUNT)).intValue();
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
            int id = ((Long)itemMeta.get(ITEMID)).intValue();
            int amount = ((Long)itemMeta.get(ITEMAMOUNT)).intValue();
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

    private void decodeBank() {
        JSONObject bankMeta = (JSONObject)playerMeta.get("bank");
        player.getBank().reset();
        for(int bankTab = 0; bankTab < 9; bankTab++) {
            JSONArray tabMeta = (JSONArray)bankMeta.get((Integer.toString(bankTab)));
            if(tabMeta.isEmpty())
                continue;
            for(int slotInsideTab = 0; slotInsideTab < tabMeta.size(); slotInsideTab++) {
                JSONArray itemMeta = (JSONArray)tabMeta.get(slotInsideTab);
                int itemId = ((Long)itemMeta.get(ITEMID)).intValue();
                int itemAmount = ((Long)itemMeta.get(ITEMAMOUNT)).intValue();
                player.getBank().addItem(itemId, itemAmount, slotInsideTab, true);
            }
        }
    }

    private void decodeAppearance() {
        JSONObject appearanceMeta = (JSONObject)playerMeta.get("appearance");
        JSONArray bodyStylesMeta = (JSONArray)appearanceMeta.get("bodystyles");
        JSONArray bodyColorsMeta = (JSONArray)appearanceMeta.get("bodycolors");
        JSONArray appearenceBlocksMeta = (JSONArray)appearanceMeta.get("appearanceblocks");

        for(int i = 0; i < bodyStylesMeta.size(); i++)
           player.getAppearence().setBodyStyle(i, ((Long)bodyStylesMeta.get(i)).intValue());
        for(int i = 0; i < bodyColorsMeta.size(); i++)
            player.getAppearence().setBodyColor(i, ((Long)bodyColorsMeta.get(i)).intValue());
        for(int i = 0; i < appearenceBlocksMeta.size(); i++)
            player.getAppearence().setAppearanceBlocks(i, ((Long)appearenceBlocksMeta.get(i)).intValue());

        boolean isMale = (Boolean)appearanceMeta.get("ismale");
        player.getAppearence().setMale(isMale);
        player.getAppearence().generateAppearenceData();
    }
}
