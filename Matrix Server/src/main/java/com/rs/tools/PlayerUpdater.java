package com.rs.tools;

import com.rs.game.player.Player;

import java.io.File;
import java.lang.reflect.Constructor;

import static com.rs.utils.SerializableFilesManager.loadSerializedFile;

public class PlayerUpdater {
//    private static String PLAYER_SAVE_PATH;
//    private static File playerFolder;
//    private static String latestVersion;
//    private static PlayerAbstract playerFileNeedingUpdate;
//
//    private static Class<? extends PlayerAbstract> olderClass;
//    private static Class<? extends PlayerAbstract> newestClass;
//
//    static {
//        try {
//            latestVersion = (String) Player.class.getMethod("getPlayerVersionOfServer").invoke(null);
//            PLAYER_SAVE_PATH = "Matrix718/Matrix server/data/characters/";
//            playerFolder = new File(PLAYER_SAVE_PATH);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public PlayerUpdater(final PlayerAbstract playerFileNeedingUpdate) throws Exception{
//        this.playerFileNeedingUpdate = playerFileNeedingUpdate;
////        olderClass = PlayerV1.class;
//        newestClass = Player.class;
//    }
//
//    public static PlayerAbstract updateFile(final PlayerAbstract playerNeedingUpdate) throws Exception {
//        new PlayerUpdater(playerNeedingUpdate);
//        return migrate();
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        File[] listOfFiles = playerFolder.listFiles();
//
//        for(File player: listOfFiles) {//list files
//            String[] playerFileComponents = parsePlayerFileName(player);
//
//            if(!isProperlyNamed(player))
//                continue;
//            if(isAlreadyUpdated(player)) {
//                System.out.println("Player " + playerFileComponents[1] + " is up to date at " + latestVersion);
//                continue;
//            }
//
//            System.out.println("Player " + playerFileComponents[1] + " is behind at " + playerFileComponents[0]);
//
//
//            /*PlayerAbstract updatedPlayer = (PlayerAbstract) PlayerUpdater.updateFile((PlayerAbstract)*/ loadSerializedFile(player);
////            storeSerializableClass(updatedPlayer, new File(PLAYER_SAVE_PATH + updatedPlayer.getPlayerVersionOfServer() + "." + updatedPlayer.getUsername() + ".p"));
//        }
//
//
//    }
//
//    public static PlayerAbstract migrate() throws Exception {
//        System.out.println("updating player file");
//        Constructor<? extends PlayerAbstract> constr = newestClass.getConstructor(PlayerAbstract.class);
//        return constr.newInstance(playerFileNeedingUpdate);
//    }
//
//    private static boolean isProperlyNamed(File playerFile) {
//        String[] playerFileComponents = parsePlayerFileName(playerFile);
//        if(playerFileComponents.length != 3)
//            return false;
//        if(!playerFileComponents[0].startsWith("v"))
//            return false;
//        if(!playerFileComponents[2].startsWith("p"))
//            return false;
//        return true;
//    }
//
//    private static boolean isAlreadyUpdated(File playerFile) {
//        String[] playerFileComponents = parsePlayerFileName(playerFile);
//        if(playerFileComponents[0].equalsIgnoreCase(latestVersion)) {
//            return true;
//        }
//        return false;
//    }
//
//    private static String[] parsePlayerFileName(File playerFile) {
//        return playerFile.getName().split("\\.");//seperate filename by '.'
//    }
}
