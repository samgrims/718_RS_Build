package com.rs.custom.dialogues;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.dialogues.DialogueHandler;

public class CustomDialoguesHandler {
    private static final String classPath = "com.rs.custom.dialogues.";

    private static final String[] dialogueFileNames = new String[] {
            "DukeHoracio"
    };

    public static void addCustomDialogues() throws ClassNotFoundException {
        for(String className : dialogueFileNames)
            DialogueHandler.putHandledDialogue(className, (Class<Dialogue>) Class.forName(classPath + className));
    }
}
