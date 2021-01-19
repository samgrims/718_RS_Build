package com.rs.custom.dialogues;

import com.rs.custom.dialogues.handler.FaceAnimations;
import com.rs.game.player.dialogues.Dialogue;

import java.util.List;

public class LumbridgeCook extends Dialogue {
    int npcId;
    List<Integer> dialogueState;

    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        dialogueState = player.getDialoguesStates().get("LumbridgeCook");
        if(dialogueState.get(0) == 0)
            sendNPCDialogue(npcId, FaceAnimations.SADDER_CRYING.getId(), "What am I to do?");
        else if(dialogueState.get(0) == 1)
            sendNPCDialogue(npcId, FaceAnimations.SADDER_CRYING.getId(), "Hey, it's you! Have you come back to help me?");
        stage = 1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
        switch(stage) {
            case 1:
                if(dialogueState.get(0) == 0)
                    sendPlayerDialogue(FaceAnimations.HAPPY_PLAIN.getId(), "What's wrong?");
                else if(dialogueState.get(0) == 1)
                    sendPlayerDialogue(FaceAnimations.HAPPY_PLAIN.getId(), "Help you with what?");
                stage = 2;
                break;
            case 2:
                sendNPCDialogue(npcId, FaceAnimations.DISPAIR.getId(), "Oh dear, oh dear, oh dear, I'm in a terrible mess! It\'s the Duke's birthday today, " +
                        "and I should be making him a lovely, big birthday cake using special ingredients");
                stage = 3;
                break;
            case 3:
                sendNPCDialogue(npcId, FaceAnimations.REGULAR_SAD_CRYING.getId(), "but I've forgotten to get the ingredients. I'll never get them in time now. " +
                        "He'll sack me! Whatever will I do? I have four children and a goat to look after. Would you help me? Please?");
                stage = 4;
                break;
            case 4:
                dialogueState.set(0, 1);
                player.setDialogueState("LumbridgeCook", dialogueState);
                sendPlayerDialogue(FaceAnimations.ANNOYED.getId(), "Sorry quests are not implemented yet");
                stage = 5;
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
