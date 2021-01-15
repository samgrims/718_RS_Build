package com.rs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

import com.rs.Settings;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;

public final class NPCSpawns {
	public enum EntityDirection {
		NORTH(8192),
		SOUTH(0),
		EAST(12288),
		WEST(4096),
		NORTHEAST(10240),
		SOUTHEAST(14366),
		NORTHWEST(6144),
		SOUTHWEST(2048);

		private int value;

		public int getValue() {
			return value;
		}

		private EntityDirection(int value) {
			this.value = value;
		}
	}

	public int getRotationValue(String rotation) {
		int value = 6144;
		switch(rotation.toUpperCase()) {
			case "NORTH" :
				break;
			case "SOUTH" :
				break;
			case "WEST" :
				break;
			case "EAST" :
				break;
			case "NORTHEAST" :
				break;
			case "NORTHWEST" :
				break;
			case "SOUTHEAST" :
				break;
			case "SOUTHWEST" :
				break;
		}
		return value;
	}

	private static final Object lock = new Object();
	public static boolean addSpawn(String username, int id, WorldTile tile) throws Throwable {
		synchronized(lock) {
			File file = new File(Settings.SERVER_DIR + "data/npcs/spawns.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write("// "+NPCDefinitions.getNPCDefinitions(id).name+", "+NPCDefinitions.getNPCDefinitions(id).combatLevel+", added by: "+username);
			writer.newLine();
			writer.flush();
			writer.write(id+" - "+tile.getX()+" "+tile.getY()+" "+tile.getPlane());
			writer.newLine();
			writer.flush();
			World.spawnNPC(id, tile, -1, true);
			return true;
		}

	}

	public static boolean removeSpawn(NPC npc) throws Throwable {
		synchronized(lock) {
			List<String> page = new ArrayList<>();
			File file = new File(Settings.SERVER_DIR + "data/npcs/spawns.txt");
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line;
			boolean removed = false;
			int id =  npc.getId();
			WorldTile tile = npc.getRespawnTile();
			while((line = in.readLine()) != null)  {
				if(line.equals(id+" - "+tile.getX()+" "+tile.getY()+" "+tile.getPlane())) {
					page.remove(page.get(page.size()-1)); //description
					removed = true;
					continue;
				}
				page.add(line);
			}
			if(!removed)
				return false;
			file.delete();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(String l : page) {
				writer.write(l);
				writer.newLine();
				writer.flush();
			}
			npc.finish();
			return true;
		}
	}

	/**
	 * If you delete packedSpawns it will be regenerated
	 */
	public static final void init() {
		if (!new File(Settings.SERVER_DIR + "data/npcs/packedSpawns").exists())
			packNPCSpawns();
	}

	private static final void packNPCSpawns() {
		Logger.log("NPCSpawns", "Packing npc spawns...");
		if (!new File(Settings.SERVER_DIR + "data/npcs/packedSpawns").mkdir())
			throw new RuntimeException("Couldn't create packedSpawns directory.");
		try {
			BufferedReader in = new BufferedReader(new FileReader(Settings.SERVER_DIR + "data/npcs/unpackedSpawnsList.txt"));
			while (true) {
				String line = in.readLine();
				if (line == null)
					break;
				if (line.startsWith("//"))
					continue;

				String[] entireLine = line.split(" - ", 2);
				if (entireLine.length != 2)
					throw new RuntimeException("Invalid NPC Spawn line: " + line);

				int npcId = Integer.parseInt(entireLine[0]);

				String[] lineArguments = entireLine[1].split(" ", 6);
				if (!(lineArguments.length >= 4 && lineArguments.length <= 6))
					throw new RuntimeException("Invalid NPC Spawn line: " + line);

				int x = Integer.parseInt(lineArguments[0]);
				int y = Integer.parseInt(lineArguments[1]);
				int plane = Integer.parseInt(lineArguments[2]);
				String rotation = lineArguments[3].toUpperCase();
				WorldTile tile = new WorldTile(x, y, plane);
				int rotationValue = EntityDirection.valueOf(rotation).getValue();

				int mapAreaNameHash = -1;
				boolean canBeAttackFromOutOfArea = true;
				if (lineArguments.length == 6) {
					mapAreaNameHash = Utils.getNameHash(lineArguments[3]);
					canBeAttackFromOutOfArea = Boolean.parseBoolean(lineArguments[4]);
				}
				writeNPCSpawn(npcId, tile.getRegionId(), tile, rotationValue, mapAreaNameHash, canBeAttackFromOutOfArea);
			}
			in.close();
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	public static final void loadNPCSpawns(int regionId) {
		File file = new File(Settings.SERVER_DIR + "data/npcs/packedSpawns/" + regionId + ".ns");
		if (!file.exists())
			return;
		try {
			RandomAccessFile in = new RandomAccessFile(file, "r");
			FileChannel channel = in.getChannel();
			ByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
			while (buffer.hasRemaining()) {
				int npcId = buffer.getShort() & 0xffff;
				int plane = buffer.get() & 0xff;
				int x = buffer.getShort() & 0xffff;
				int y = buffer.getShort() & 0xffff;
				int rotation = buffer.getInt();
				boolean hashExtraInformation = buffer.get() == 1;
				int mapAreaNameHash = -1;
				boolean canBeAttackFromOutOfArea = true;
				if (hashExtraInformation) {
					mapAreaNameHash = buffer.getInt();
					canBeAttackFromOutOfArea = buffer.get() == 1;
				}
				World.spawnNPC(npcId, new WorldTile(x, y, plane), rotation, mapAreaNameHash, canBeAttackFromOutOfArea);
			}
			channel.close();
			in.close();
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	private static final void writeNPCSpawn(int npcId, int regionId, WorldTile tile, int rotationValue, int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(Settings.SERVER_DIR + "data/npcs/packedSpawns/" + regionId + ".ns", true));
			out.writeShort(npcId);
			out.writeByte(tile.getPlane());
			out.writeShort(tile.getX());
			out.writeShort(tile.getY());
			out.writeInt(rotationValue);
			out.writeBoolean(mapAreaNameHash != -1);
			if (mapAreaNameHash != -1) {
				out.writeInt(mapAreaNameHash);
				out.writeBoolean(canBeAttackFromOutOfArea);
			}
			out.flush();
			out.close();
		} catch (Throwable e) {
			Logger.handle(e);
		}
	}

	private NPCSpawns() {
	}
}
