package com.rs;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.alex.store.Index;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.ItemsEquipIds;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Region;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.npc.combat.CombatScriptsHandler;
import com.rs.game.player.Player;
import com.rs.game.player.content.FishingSpotsHandler;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.controlers.ControlerHandler;
import com.rs.game.player.cutscenes.CutscenesHandler;
import com.rs.game.player.dialogues.DialogueHandler;
import com.rs.net.ServerChannelHandler;
import com.rs.utils.*;
import com.rs.utils.huffman.Huffman;

public final class Server {
	public static String getHostIP() throws Exception {
		InetAddress localhost = InetAddress.getLocalHost();

		String systemipaddress = "";
		try
		{
			URL url_name = new URL("http://bot.whatismyipaddress.com");
			BufferedReader sc =	new BufferedReader(new InputStreamReader(url_name.openStream()));
			systemipaddress = sc.readLine().trim();
		}
		catch (Exception e)
		{
			systemipaddress = "Cannot Execute Properly";
		}
		return systemipaddress;
	}

	public static void main(String[] args) throws Exception {
		if(getHostIP().equalsIgnoreCase("72.191.29.70"))
			Settings.XP_RATE = 100;

		long currentTime = Utils.currentTimeMillis();
		Logger.log("Launcher", "Initing Cache...");
		Cache.init();
		ItemsEquipIds.init();
		Huffman.init();
		Logger.log("Launcher", "Initing Data Files...");
		DisplayNames.init();
		IPBanL.init();
		PkRank.init();
		DTRank.init();
		MapArchiveKeys.init();
		MapAreas.init();
		ObjectSpawns.init();
		NPCSpawns.init();
		NPCCombatDefinitionsLoader.init();
		NPCBonuses.init();
		NPCDrops.init();
		ItemExamines.init();
		ItemBonuses.init();
		MusicHints.init();
		ShopsHandler.init();
		Logger.log("Launcher", "Initing Fishing Spots...");
		FishingSpotsHandler.init();
		Logger.log("Launcher", "Initing NPC Combat Scripts...");
		CombatScriptsHandler.init();
		Logger.log("Launcher", "Initing Dialogues...");
		DialogueHandler.init();
		Logger.log("Launcher", "Initing Controlers...");
		ControlerHandler.init();
		Logger.log("Launcher", "Initing Cutscenes...");
		CutscenesHandler.init();
		Logger.log("Launcher", "Initing Friend Chats Manager...");
		FriendChatsManager.init();
		Logger.log("Launcher", "Initing Cores Manager...");
		CoresManager.init();
		Logger.log("Launcher", "Initing World...");
		World.init();
		Logger.log("Launcher", "Initing Region Builder...");
		RegionBuilder.init();
		Logger.log("Launcher", "Initing Server Channel Handler...");
		try {
			ServerChannelHandler.init();
		} catch (Throwable e) {
			Logger.handle(e);
			Logger.log("Launcher", "Failed initing Server Channel Handler. Shutting down...");
			System.exit(1);
			return;
		}
		Logger.log("Launcher", "Server took " + (Utils.currentTimeMillis() - currentTime) + " milli seconds to launch.");
		addAccountsSavingTask();
		addCleanMemoryTask();
	}

	private static void setWebsitePlayersOnline(int amount) throws IOException {
		URL url = new URL("https://warbycode.com/");
		url.openStream().close();
	}

	private static void addUpdatePlayersOnlineTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					setWebsitePlayersOnline(World.getPlayers().size());
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 2, 2, TimeUnit.MINUTES);
	}

	private static void addCleanMemoryTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					cleanMemory(Runtime.getRuntime().freeMemory() < Settings.MIN_FREE_MEM_ALLOWED);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 10, TimeUnit.MINUTES);
	}

	private static void addAccountsSavingTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					saveFiles();
				} catch (Throwable e) {
					Logger.handle(e);
				}

			}
		}, 15, 5, TimeUnit.MINUTES);
	}

	public static void saveFiles() {
		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished())
				continue;
			player.getSaveJSONManager().saveJSON();
			SerializableFilesManager.savePlayer(player);
		}
		DisplayNames.save();
		IPBanL.save();
		PkRank.save();
		DTRank.save();
	}

	public static void cleanMemory(boolean force) {
		if (force) {
			ItemDefinitions.clearItemsDefinitions();
			NPCDefinitions.clearNPCDefinitions();
			ObjectDefinitions.clearObjectDefinitions();
			for (Region region : World.getRegions().values())
				region.removeMapFromMemory();
		}
		for (Index index : Cache.STORE.getIndexes())
			index.resetCachedFiles();
		CoresManager.fastExecutor.purge();
		System.gc();
	}

	public static void shutdown() {
		try {
			closeServices();
		} finally {
			System.exit(0);
		}
	}

	public static void closeServices() {
		ServerChannelHandler.shutdown();
		CoresManager.shutdown();
	}

	public static void restart() {
		closeServices();
		System.gc();
		try {
			Runtime.getRuntime().exec("java -server -Xms2048m -Xmx20000m -cp bin;/data/libs/netty-3.2.7.Final.jar;/data/libs/FileStore.jar Launcher false false true false");
			System.exit(0);
		} catch (Throwable e) {
			Logger.handle(e);
		}

	}

	private Server() {

	}

}
