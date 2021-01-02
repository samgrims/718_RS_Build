package com.rs.custom;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.List;

/**
 * Protects player integrity accross player versions
 */
public class JSONPlayerFile {
    //Skills DONE
    //Misc: Spins, Music, minutes played, creation date
    //Bank Inventory DRAFT ENCODE
    //Inventory DRAFT ENCODE
    //Equipment Inventory DRAFT ENCODE
    //Toolbelt inventory

    public static void savePlayer(Player player) {
        JSONObject playerMeta = new JSONObject();
        playerMeta = encodeSkills(player, playerMeta);
        playerMeta = encodeInventory(player, playerMeta);
        playerMeta = encodeEquipment(player, playerMeta);
        playerMeta = encodeDetails(player, playerMeta);
        playerMeta = encodeToolbelt(player, playerMeta);

        toFile(player, playerMeta);
    }

    public static void loadPlayer(Player player) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        try {
            String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
            File input_json = new File(filePath);
            if(!input_json.exists())
                input_json.createNewFile();

            //Read JSON file
            FileReader reader = new FileReader(input_json);
            Object obj = jsonParser.parse(reader);
            JSONObject playerContainer = (JSONObject) obj;
            decodeSkills(player, playerContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject decodeSkills(Player player, JSONObject playerContainer) {
        JSONArray skills = (JSONArray)playerContainer.get("skills");
        JSONObject skill;
        for(int skillID = 0; skillID < skills.size(); skillID++) {
            skill = (JSONObject) skills.get(skillID);
            String skillName = Skills.SKILL_NAME[skillID].toLowerCase();
            Long xp = (long)((JSONArray)skill.get(skillName)).get(1);
            player.getSkills().setXp(skillID, xp.doubleValue());
            player.getSkills().restoreSkills();
//            System.out.println(skill);
        }
        return playerContainer;
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
        JSONArray inventory = new JSONArray();
        JSONObject itemJSON = new JSONObject();
        JSONArray itemMeta = new JSONArray();
        playerMeta.put("inventory", inventory);

        for(int slot = 0; slot < 28; slot++) {
            Item item = player.getInventory().getItem(slot);
            if(item != null) {
                itemMeta.add(item.getId());
                itemMeta.add(item.getAmount());
                itemJSON.put(item.getName().toLowerCase(), itemMeta.clone());
            }
            inventory.add(itemJSON.clone());
            itemJSON.clear();
            itemMeta.clear();
        }
        return playerMeta;
    }

    private static JSONObject encodeEquipment(Player player, JSONObject playerMeta) {
        JSONArray equipment = new JSONArray();
        JSONObject itemJSON = new JSONObject();
        JSONArray itemMeta = new JSONArray();

        for(int slot = 0; slot < 15; slot++) {
            Item item = player.getEquipment().getItem(slot);
            if(item != null) {
                itemMeta.add(item.getId());
                itemMeta.add(item.getAmount());
                itemJSON.put(item.getName().toLowerCase(), itemMeta.clone());
            }
            equipment.add(itemJSON.clone());
            itemJSON.clear();
            itemMeta.clear();;
        }

        playerMeta.put("equipment", equipment);
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
