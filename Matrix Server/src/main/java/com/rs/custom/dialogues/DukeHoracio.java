package com.rs.custom.dialogues;

import com.rs.custom.dialogues.handler.FaceAnimation;
import com.rs.game.player.Inventory;
import com.rs.game.player.dialogues.Dialogue;

public class DukeHoracio extends Dialogue {
    int npcId;

    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, FaceAnimation.PLAIN_TALKING.animationID, "Greetings. Welcome to my castle.");
        stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
        System.out.println(stage);
        switch(stage) {
            case 1:
                sendPlayerDialogue(FaceAnimation.PLAIN_TALKING_WITH_BLINK.animationID, "I seek a shield that will protect me from dragonbreath.");
                stage = 2;
                break;
            case 2:
                sendNPCDialogue(npcId, FaceAnimation.PLAIN_TALKING_WITH_BLINK.animationID, "A knight going on a dragon quest, hmm?");
                stage = 3;
                break;
            case 3:
                Inventory playerInventory = player.getInventory();
                if(playerInventory.hasFreeSlots()) {
                    sendDialogue("The duke hands you the shield.");
                    playerInventory.addItem(1540, 1);
                } else
                    player.getPackets().sendSound(4039, 0, 1);
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
