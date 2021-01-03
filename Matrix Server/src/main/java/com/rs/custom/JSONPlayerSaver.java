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

import java.io.*;
import java.util.List;

/**
 * Protects player integrity accross player versions
 */
public class JSONPlayerSaver {
    //Bank Inventory DRAFT ENCODE
    //Toolbelt inventory DRAFT ENCODE

    private static int itemName = 0, itemId = 1, itemAmount = 2;

    public static void savePlayer(Player player) {
        JSONObject playerMeta = new JSONObject();
        playerMeta = encodeSkills(player, playerMeta);
        playerMeta = encodeInventory(player, playerMeta);
        playerMeta = encodeEquipment(player, playerMeta);
        playerMeta = encodeDetails(player, playerMeta);
        playerMeta = encodeToolbelt(player, playerMeta);
        playerMeta = encodeBank(player, playerMeta);

        toFile(player, playerMeta);
    }



    private static JSONObject encodeSkills(Player player, JSONObject playerMeta) {
        JSONArray skills = new JSONArray();
        JSONObject skill = new JSONObject();
        JSONArray skillMeta = new JSONArray();

        for(short skillID = 0; skillID < 25; skillID++) {
            skillMeta.add(player.getSkills().getLevel(skillID));
            skillMeta.add((int)player.getSkills().getXp(skillID));
            skill.put(Skills.SKILL_NAME[skillID].toLowerCase(), skillMeta.clone());
            skills.add(skill.clone());
            skill.clear();
            skillMeta.clear();
        }
        playerMeta.put("skills", skills);
        return playerMeta;
    }

    private static JSONObject encodeInventory(Player player, JSONObject playerMeta) {
        JSONArray inventoryMeta = new JSONArray();
        JSONArray itemMeta = new JSONArray();
        playerMeta.put("inventory", inventoryMeta);

        for(int slot = 0; slot < 28; slot++) {
            Item item = player.getInventory().getItem(slot);
            if(item != null) {
                itemMeta.add(item.getName());
                itemMeta.add(item.getId());
                itemMeta.add(item.getAmount());
            }
            inventoryMeta.add(itemMeta.clone());
            itemMeta.clear();
        }
        return playerMeta;
    }

    private static JSONObject encodeEquipment(Player player, JSONObject playerMeta) {
        JSONArray equipmentMeta = new JSONArray();
        JSONArray itemMeta = new JSONArray();

        for(int slot = 0; slot < 15; slot++) {
            Item item = player.getEquipment().getItem(slot);
            if(item != null) {
                itemMeta.add(item.getName());
                itemMeta.add(item.getId());
                itemMeta.add(item.getAmount());
            }
            equipmentMeta.add(itemMeta.clone());
            itemMeta.clear();;
        }

        playerMeta.put("equipment", equipmentMeta);
        return playerMeta;
    }

    private static JSONObject encodeDetails(Player player, JSONObject playerMeta) {
        JSONObject details = new JSONObject();
        JSONArray musicUnlocked = new JSONArray();
        details.put("creation date", player.getCreationDate());
        details.put("minutes played", player.getTotalMinutesPlayed());
        details.put("spins", player.getSpins());

        JSONObject coordinate = new JSONObject();
        coordinate.put("x", player.getLocation().getX());
        coordinate.put("y", player.getLocation().getY());
        coordinate.put("plane", player.getLocation().getPlane());
        details.put("location", coordinate);

        List<Integer> musicsUnlocked = player.getMusicsManager().getUnlockedMusics();
        for(int index = 0; index < musicsUnlocked.size(); index++)
            musicUnlocked.add(musicsUnlocked.get(index));
        details.put("music unlocked", musicUnlocked);

        playerMeta.put("details", details);
        return playerMeta;
    }

    private static JSONObject encodeToolbelt(Player player, JSONObject playerMeta) {
        JSONArray itemsDepositedJSON = new JSONArray();
        boolean[] itemsDeposited = player.getToolbelt().getItemsDeposited();
        for(boolean isDeposited : itemsDeposited)
            itemsDepositedJSON.add(isDeposited);

        playerMeta.put("toolbelt", itemsDepositedJSON);
        return playerMeta;
    }

    private static JSONObject encodeBank(Player player, JSONObject playerMeta) {
        JSONObject bank = new JSONObject();
        JSONArray tabsMeta = new JSONArray();
        JSONObject itemsJSON = new JSONObject();
        JSONArray itemsMeta = new JSONArray();

        Item[][] bankTabs = player.getBank().getBankTabs();
        for(int tabSlot = 0; tabSlot < 9; tabSlot++) {
            if(tabSlot > bankTabs.length-1) {
                bank.put(tabSlot, tabsMeta.clone());
                tabsMeta.clear();
                continue;
            }
            Item[] tab = bankTabs[tabSlot];
            for(int itemSlot = 0; itemSlot < tab.length; itemSlot++) {
                Item item = tab[itemSlot];
                itemsMeta.add(item.getId());
                itemsMeta.add(item.getAmount());
                itemsJSON.put(item.getName(), itemsMeta.clone());
                tabsMeta.add(itemsJSON.clone());
                itemsMeta.clear();
                itemsJSON.clear();;
            }
            bank.put(tabSlot, tabsMeta.clone());
            tabsMeta.clear();
        }

        playerMeta.put("bank", bank);
        return playerMeta;
    }

    private static void toFile(Player player, JSONObject playerMeta) {
        try {
            String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";

            File output_json = new File(filePath);
            if(!output_json.exists())
                output_json.createNewFile();

            FileWriter fileWriter = new FileWriter(output_json);
            fileWriter.write(playerMeta.toJSONString());
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
