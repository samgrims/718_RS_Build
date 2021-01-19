package com.rs.custom.dialogues;

import com.rs.custom.dialogues.handler.FaceAnimations;
import com.rs.game.player.Inventory;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class DukeHoracio extends Dialogue {
    int npcId;

    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, FaceAnimations.PLAIN_TALKING.getId(), "Greetings. Welcome to my castle.");
        stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
        System.out.println(stage);
        switch(stage) {
            case 1:
                sendPlayerDialogue(FaceAnimations.PLAIN_TALKING_WITH_BLINK.getId(), "I seek a shield that will protect me from dragonbreath.");
                stage = 2;
                break;
            case 2:
                sendNPCDialogue(npcId, FaceAnimations.PLAIN_TALKING_WITH_BLINK.getId(), "A knight going on a dragon quest, hmm?");
                stage = 3;
                break;
            case 3:
                Inventory playerInventory = player.getInventory();
                if(playerInventory.hasFreeSlots()) {
                    sendDialogue("The duke hands you the shield.");
                    playerInventory.addItem(1540, 1);
                } else
                    sendDialogue("You need to free your inventory");
                stage = 0;
                break;
            default:
                end();
                break;
        }
    }

    @Override
    public void finish() {


    }

}
