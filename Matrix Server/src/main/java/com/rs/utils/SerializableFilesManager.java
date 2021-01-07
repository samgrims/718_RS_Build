package com.rs.utils;

import java.io.*;
import java.util.ConcurrentModificationException;

import com.rs.Settings;
import com.rs.game.player.Player;
import com.rs.tools.DebugLine;

public class SerializableFilesManager {

	public synchronized static final boolean containsPlayer(String username) {
		File folder = new File(Settings.PLAYER_FOLDER_DIR);
		File[] listOfFiles = folder.listFiles();

		if(listOfFiles.length == 0) {
			return false;
		}
		for(File player: listOfFiles) {
			String usernameOfFile = player.getName().split("\\.")[1];
			if(usernameOfFile.equalsIgnoreCase(username)) {
				DebugLine.print("Contains " + username);
				return true;
			}
		}

		return false;
	}

	public synchronized static Player loadPlayer(String username) {
		try {
			String playerPath = Settings.PLAYER_FOLDER_DIR + Player.getPlayerVersionOfServer() + "." + username + ".p";
			Player loadedPlayerFile = (Player) loadSerializedFile(new File(playerPath));
			DebugLine.print(playerPath);
			if(loadedPlayerFile == null)
				DebugLine.print("There is likely an update!");
			return loadedPlayerFile;
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}

	public synchronized static void savePlayer(Player player) {
		try {
			String playerPath = Settings.PLAYER_FOLDER_DIR + Player.getPlayerVersionOfServer() + "." + player.getUsername() + ".p";
			storeSerializableClass(player, new File(playerPath));
			DebugLine.print(playerPath);
		} catch (ConcurrentModificationException e) {
			//happens because saving and logging out same time
			DebugLine.print("This happened");
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static final Object loadSerializedFile(File f) throws IOException, ClassNotFoundException {
		if (!f.exists())
			return null;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
		Object object = in.readObject();
		in.close();
		return object;
	}

	public static final void storeSerializableClass(Serializable o, File f) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
		out.writeObject(o);
		out.close();
	}

	private SerializableFilesManager() {

	}

}
