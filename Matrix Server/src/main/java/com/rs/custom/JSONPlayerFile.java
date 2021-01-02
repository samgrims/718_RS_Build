package com.rs.custom;

import com.rs.Settings;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

/**
 * Protects player integrity accross player versions
 */
public class JSONPlayerFile {
    //Skills DONE
    //Misc: Spins, Music, minutes played, creation date
    //Bank Inventory
    //Inventory DRAFT ENCODE
    //Equipment Inventory
    //Toolbelt inventory

    public static void savePlayer(Player player) {
        JSONObject playerMeta = new JSONObject();
        playerMeta = encodeSkills(player, playerMeta);
        playerMeta = encodeInventory(player, playerMeta);
//        playerMeta = encodeEquipment(player, playerMeta);

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

    private static void toFile(Player player, JSONObject playerMeta) {
        try {
            String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
            System.out.println(filePath);
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
