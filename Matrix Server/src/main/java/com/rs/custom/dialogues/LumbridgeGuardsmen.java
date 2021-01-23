package com.rs.custom.dialogues;

import com.rs.custom.dialogues.handler.FaceAnimation;
import com.rs.game.player.dialogues.Dialogue;

public class LumbridgeGuardsmen extends Dialogue {
    int npcId;

    @Override
    public void start() {
        npcId = (Integer) parameters[0];

        sendNPCDialogue(npcId, FaceAnimation.HAPPY_PLAIN.animationID, "Greetings, adventurer. Duke Horacio has recently provided us guards with advanced" +
                "training, as well as much improved swords!");
        stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
        switch(stage) {
            case 1:
                sendNPCDialogue(npcId, FaceAnimation.HAPPY_PLAIN.animationID, "I feel much more confident in our ability to defend Lumbridge now that" +
                        " we actually have proper equipment and training!");
                stage = 2;
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