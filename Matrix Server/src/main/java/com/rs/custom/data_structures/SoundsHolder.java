package com.rs.custom.data_structures;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.custom.CustomUtilities;
import com.rs.game.Entity;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;

import static com.rs.game.player.actions.PlayerCombat.sendSoundToPlayer;

public class SoundsHolder {
    public static void playWeaponAttackSoundFromPlayer(Player player, Entity attackTarget, int weaponId, int attackStyle) {
        int soundId = -1;
        String weaponName = "unverified weapon";
        if (weaponId != -1) {
            weaponName = ItemDefinitions.getItemDefinitions(weaponId).getName().toLowerCase();
            if (weaponName.contains("dart") || weaponName.contains("knife"))
//				if(attackstyle == 0)
//					return 123;
                soundId = 2707;
            if(weaponName.contains("sword")) {
                String[] weaponWords = weaponName.split(" ");
                if(weaponWords.length == 2 && CustomUtilities.isMetalName(weaponWords[0]))
                    soundId = 2500;
            }
        }
        if(soundId == -1)
            player.notifyDebugMessage(weaponName + " has no attack sound");
        else
            PlayerCombat.sendSoundToPlayer(soundId, player, attackTarget);
    }

    public static void sendEntityDeathSoundToPlayer(Entity soundSource, Player player) {
        if(soundSource instanceof NPC){
            NPC deadNPC = ((NPC) soundSource);
            String nameNPC = deadNPC.getName();
////			if(soundSource instanceof Boss) {//or above lvl 300
////				if(soundSource.withinDistance(attacker, 5)) {
////					//playsound
////						//return
////				}
            switch (nameNPC.toLowerCase()) {
                case "imp":
                    PlayerCombat.sendSoundToPlayer(535, player, deadNPC);
                    break;
                default:
                    player.notifyDebugMessage(nameNPC + " Has no death sounds");
            }
        }
    }

    public static void sendEntityAttackSoundToPlayer(Entity soundSource, Player player) {
        if(soundSource instanceof NPC) {
            NPC source = (NPC) soundSource;
            String entityName = source.getName().toLowerCase();
            switch (entityName) {
                case "imp":
                    sendSoundToPlayer(534, player, source);
                    break;
                case "man":
                case "chicken":
                default:
                    player.notifyDebugMessage(entityName + " has no attack sounds!");
            }
        }
    }

    public static void sendEntityDefenceSoundToPlayer(Entity soundSource, Player player) {
        if(soundSource instanceof NPC) {
            NPC source = ((NPC)soundSource);
            String entityName = source.getName();
            switch (entityName.toLowerCase()) {
                case "imp":
                    sendSoundToPlayer(536, player, source);
                    break;
                case "man":
                case "chicken":
                default:
                    player.notifyDebugMessage(entityName + " has no defence sounds");
                    break;
            }
        }
    }
}
