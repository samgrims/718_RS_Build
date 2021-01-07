package com.rs.custom;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Protects player integrity accross player versions
 */
public class JSONPlayerSaver {
    //Bank Inventory DRAFT ENCODE
    //Toolbelt inventory DRAFT ENCODE
    private transient  Player player;
    private JSONObject playerMeta;
    private static int itemName = 0, itemId = 1, itemAmount = 2;

    protected void setPlayer(Player player) {
        this.player = player;
        this.playerMeta = new JSONObject();
    }

    public void savePlayer() {
        encodeCoordinate();
        encodeSkills();
        encodeInventory();
        encodeEquipment();
        encodeDetails();
        encodeMusic();
        encodeToolbelt();
        encodeBank();
        toFile();
    }

    private void encodeCoordinate() {
        JSONObject coordinate = new JSONObject();
        coordinate.put("x", player.getLocation().getX());
        coordinate.put("y", player.getLocation().getY());
        coordinate.put("plane", player.getLocation().getPlane());
        playerMeta.put("coordinate", coordinate);
    }

    private void encodeSkills() {
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
    }

    private void encodeInventory() {
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
    }

    private void encodeEquipment() {
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
    }

    private void encodeMusic() {
        JSONArray musicUnlocked = new JSONArray();

        //Idk why, musicsUnlocked was given the same music list as a loop for 13k lines
        List<Integer> musicsUnlocked = player.getMusicsManager().getUnlockedMusics();
        musicsUnlocked = removeDuplicatesFromList(musicsUnlocked);
        for(int index = 0; index < musicsUnlocked.size(); index++)
            musicUnlocked.add(musicsUnlocked.get(index));
        playerMeta.put("music unlocked", musicUnlocked);
    }

    private void encodeDetails() {
        JSONObject details = new JSONObject();
        details.put("creation date", player.getCreationDate());
        details.put("minutes played", player.getTotalMinutesPlayed());
        details.put("spins", player.getSpins());
        playerMeta.put("details", details);
    }

    /**
     * Taken from Geeksforgeeks
     */
    private static <T> List<T> removeDuplicatesFromList(List<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list)
            if (!newList.contains(element))
                newList.add(element);
        return newList;
    }

    private void encodeToolbelt() {
        JSONArray itemsDepositedJSON = new JSONArray();
        boolean[] itemsDeposited = player.getToolbelt().getItemsDeposited();
        for(boolean isDeposited : itemsDeposited)
            itemsDepositedJSON.add(isDeposited);

        playerMeta.put("toolbelt", itemsDepositedJSON);
    }

    private void encodeBank() {
        JSONObject bankMeta = new JSONObject();
        JSONArray tabMeta = new JSONArray();
        JSONArray itemMeta = new JSONArray();

        Item[][] bankTabs = player.getBank().getBankTabs();//Size of bankTabs changes depending on tabs created
        for(int tabSlot = 0; tabSlot < 9; tabSlot++) {
            if(!(tabSlot >= bankTabs.length)) {//check inquired tab to be non existent in bankTabs
                Item[] tab = bankTabs[tabSlot];
                for (int itemSlot = 0; itemSlot < tab.length; itemSlot++) {
                    Item item = tab[itemSlot];
                    itemMeta.add(item.getName());
                    itemMeta.add(item.getId());
                    itemMeta.add(item.getAmount());
                    tabMeta.add(itemMeta.clone());
                    itemMeta.clear();
                }
            }
            bankMeta.put(Integer.toString(tabSlot), tabMeta.clone());
            tabMeta.clear();
        }
        playerMeta.put("bank", bankMeta);
    }

    private void toFile() {
        try {
            File output_json = CustomUtilities.getJSONFile(player);
            if(!CustomUtilities.jsonExists(player))
                CustomUtilities.createJSON(player);
            FileWriter fileWriter = new FileWriter(output_json);
            fileWriter.write(playerMeta.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
