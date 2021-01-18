package com.rs.custom.dialogues.handler;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.game.player.dialogues.DialogueHandler;

/**
 * Ripped from https://www.rune-server.ee/runescape-development/rs-503-client-server/configuration/428291-dialogue-animations.html
 * Edited by Jawarrior1: Duplicate Face Animations are removed.
 */
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
