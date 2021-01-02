package com.rs.custom;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;

/**
 * Protects player integrity accross player versions
 */
public class JSONPlayerFileManager {
    //Skills DONE
    //Misc: Spins, Music, minutes played, creation date
    //Bank Inventory
    //Inventory
    //Equipment Inventory
    //Toolbelt inventory

    public static void savePlayer(Player player) {
        JSONObject playerContainer = new JSONObject();
        playerContainer = saveSkills(player, playerContainer);

        System.out.println(playerContainer);
        saveToFile(player, playerContainer);
    }

    public static void loadPlayer(Player player) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try {
            String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
            File input_json = new File(filePath);
            if(!input_json.exists())
                input_json.createNewFile();

            FileReader reader = new FileReader(input_json);

            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONObject playerContainer = (JSONObject) obj;

            readSkills(player, playerContainer);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject readSkills(Player player, JSONObject playerContainer) {
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

    private static JSONObject saveSkills(Player player, JSONObject playerContainer) {
        JSONArray skills = new JSONArray();
        JSONObject skillContainer = new JSONObject();
        JSONArray skill = new JSONArray();

        for(short skillID = 0; skillID < 25; skillID++) {
            skill.add(player.getSkills().getLevel(skillID));
            skill.add((int)player.getSkills().getXp(skillID));
            skillContainer.put(Skills.SKILL_NAME[skillID].toLowerCase(), skill.clone());
            skills.add(skillContainer.clone());
            skillContainer.clear();
            skill.clear();
        }
        playerContainer.put("skills", skills);
        return playerContainer;
    }

    private static void saveToFile(Player player, JSONObject playerContainer) {
        try {
            String filePath = Settings.PLAYER_JSON_FOLDER_DIR + player.getUsername().toLowerCase() + ".json";
            System.out.println(filePath);
            File output_json = new File(filePath);
            if(!output_json.exists())
                output_json.createNewFile();

            FileWriter fileWriter = new FileWriter(output_json);
            fileWriter.write(playerContainer.toJSONString());
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
